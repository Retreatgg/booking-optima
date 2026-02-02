package com.optima.test_task.services.impls;

import com.optima.test_task.dtos.CreatePaymentInput;
import com.optima.test_task.dtos.PaymentResponse;
import com.optima.test_task.enums.PaymentStatus;
import com.optima.test_task.enums.PaymentType;
import com.optima.test_task.exceptions.PaymentException;
import com.optima.test_task.mappers.PaymentMapper;
import com.optima.test_task.models.Booking;
import com.optima.test_task.models.Payment;
import com.optima.test_task.models.User;
import com.optima.test_task.repositories.PaymentRepository;
import com.optima.test_task.services.PaymentService;
import com.optima.test_task.services.PaymentStrategy;
import com.optima.test_task.services.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final List<PaymentStrategy> strategies;
    private final PaymentRepository paymentRepository;
    private final SecurityService securityService;
    private final PaymentMapper paymentMapper;

    @Override
    public void process(Booking booking, CreatePaymentInput paymentInput, PaymentType paymentType) {
        PaymentStrategy strategy = strategies.stream()
                .filter(s -> s.getProvider() == paymentInput.paymentProvider())
                .findFirst()
                .orElseThrow(() -> new PaymentException("Способ оплаты не поддерживается"));

        Payment payment = new Payment();
        payment.setBooking(booking);
        payment.setPaymentProvider(paymentInput.paymentProvider());
        payment.setPaymentType(paymentType);
        payment.setPaymentDetails(paymentInput.paymentDetails());
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDetails(paymentInput.paymentDetails());

        paymentRepository.save(payment);

        try {
            strategy.execute(booking, paymentInput);
            payment.setStatus(PaymentStatus.SUCCESS);
        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            throw new PaymentException("Ошибка при оплате: " + e.getMessage());
        }
    }

    @Override
    public List<PaymentResponse> myPayments() {
        User currentUser = securityService.getCurrentUser();
        List<Payment> payments = paymentRepository.findAllByBookingUser(currentUser);
        return paymentMapper.toResponseList(payments);
    }
}
