package com.optima.test_task.services.impls;

import com.optima.test_task.dtos.BookingResponse;
import com.optima.test_task.dtos.CreateBookingInput;
import com.optima.test_task.dtos.CreatePaymentInput;
import com.optima.test_task.enums.BookingStatus;
import com.optima.test_task.enums.PaymentType;
import com.optima.test_task.exceptions.*;
import com.optima.test_task.mappers.BookingMapper;
import com.optima.test_task.models.Booking;
import com.optima.test_task.models.Resource;
import com.optima.test_task.models.User;
import com.optima.test_task.repositories.BookingRepository;
import com.optima.test_task.services.BookingService;
import com.optima.test_task.services.PaymentService;
import com.optima.test_task.services.ResourceService;
import com.optima.test_task.services.SecurityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final ResourceService resourceService;
    private final PaymentService paymentProcessor;
    private final SecurityService securityService;

    @Override
    @Transactional
    public BookingResponse create(CreateBookingInput input) {
        User currentUser = securityService.getCurrentUser();
        Resource resource = resourceService.findModelById(input.resourceId());

        log.info("Пользователь [ID: {}] инициировал создание бронирования для ресурса [ID: {}]",
                currentUser.getId(), input.resourceId());

        checkAvailability(input, resource);

        Booking booking = toBooking(input, resource);

        booking.setUser(currentUser);

        bookingRepository.save(booking);
        log.debug("Предварительная запись бронирования сохранена в БД [ID: {}, Status: PENDING]", booking.getId());

        if (input.paymentType() == PaymentType.INSTANT) {
            log.info("Запуск процесса моментальной оплаты для бронирования [ID: {}]", booking.getId());
            paymentProcessor.process(booking, input.paymentInput(), input.paymentType());
            booking.setStatus(BookingStatus.CONFIRMED);
            log.info("Бронирование [ID: {}] успешно подтверждено после оплаты", booking.getId());
        } else {
            log.info("Создано отложенное бронирование [ID: {}], ожидается оплата", booking.getId());
        }

        return bookingMapper.toResponse(booking);
    }

    @Override
    public List<BookingResponse> getMyBookings() {
        User currentUser = securityService.getCurrentUser();
        log.info("Запрос истории бронирований пользователем [ID: {}]", currentUser.getId());
        List<Booking> myBookings = bookingRepository.findByUserId(currentUser.getId());
        log.debug("Найдено {} записей для пользователя [ID: {}]", myBookings.size(), currentUser.getId());
        return bookingMapper.toResponseList(myBookings);
    }

    @Override
    @Transactional
    public BookingResponse cancel(Long id) {
        User currentUser = securityService.getCurrentUser();
        log.info("Пользователь [ID: {}] пытается отменить бронирование [ID: {}]", currentUser.getId(), id);
        Booking booking = findModelById(id);
        if(!booking.getUser().getId().equals(currentUser.getId())) {
            log.warn("Отказ в отмене: пользователь [ID: {}] не является владельцем брони [ID: {}]",
                    currentUser.getId(), id);
            throw new UserUnAuthorizedException("Вы не можете отменить чужую бронь!");
        }
        if(booking.getStatus().equals(BookingStatus.CONFIRMED)) {
            log.warn("Отказ в отмене: бронирование [ID: {}] уже оплачено и подтверждено", id);
            throw new ResourceBookedException("Подтвержденный бронь нельзя отменить!");
        }
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
        log.info("Бронирование [ID: {}] успешно отменено пользователем [ID: {}]", id, currentUser.getId());
        return bookingMapper.toResponse(booking);
    }

    @Override
    @Transactional
    public BookingResponse payForBooking(Long bookingId, CreatePaymentInput input) {
        User currentUser = securityService.getCurrentUser();
        Booking booking = findModelById(bookingId);
        log.info("Пользователь [ID: {}] инициировал оплату отложенного бронирования [ID: {}]",
                currentUser.getId(), bookingId);
        if(booking.getStatus().equals(BookingStatus.CONFIRMED)) {
            log.warn("Повторная попытка оплаты бронирования [ID: {}]", bookingId);
            throw new PaymentException("За данный бронь уже оплачено");
        }
        paymentProcessor.process(booking, input, PaymentType.DELAYED);
        booking.setStatus(BookingStatus.CONFIRMED);
        log.info("Отложенная оплата принята. Бронирование [ID: {}] переведено в статус CONFIRMED", bookingId);
        return bookingMapper.toResponse(booking);
    }

    private Booking toBooking(CreateBookingInput input, Resource resource) {
        return Booking.builder()
                .resource(resource)
                .endTime(input.endTime().toLocalDateTime())
                .startTime(input.startTime().toLocalDateTime())
                .status(BookingStatus.PENDING)
                .build();
    }

    private void checkAvailability(CreateBookingInput input, Resource resource) {
        LocalDateTime now = LocalDateTime.now();

        if (input.startTime().toLocalDateTime().isBefore(now)) {
            throw new ResourceTimeException("Нельзя забронировать ресурс в прошлом");
        }
        if (input.endTime().isBefore(input.startTime())) {
            throw new ResourceTimeException("Время окончания не может быть раньше времени начала");
        }

        if (!resource.getIsActive()) {
            throw new ResourceInActiveException("Ресурс сейчас неактивен и недоступен для бронирования");
        }

        boolean isOverlapped = bookingRepository.hasOverlappingBookings(
                resource.getId(),
                input.startTime().toLocalDateTime(),
                input.endTime().toLocalDateTime()
        );

        if (isOverlapped) {
            log.warn("Конфликт бронирования: ресурс [ID: {}] занят на период {} - {}",
                    resource.getId(), input.startTime(), input.endTime());
            throw new ResourceBookedException("Ресурс уже забронирован на указанное время");
        }
    }

    private Booking findModelById(Long id) {

        return bookingRepository.findById(id).orElseThrow(() -> {
            log.error("Бронирование с ID {} не найдено", id);
            throw new BookingNotFoundException("Бронь по ID: " + id + " не найдена");
        });
    }
}
