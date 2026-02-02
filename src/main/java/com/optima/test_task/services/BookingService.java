package com.optima.test_task.services;

import com.optima.test_task.dtos.BookingResponse;
import com.optima.test_task.dtos.CreateBookingInput;
import com.optima.test_task.dtos.CreatePaymentInput;

import java.util.List;

public interface BookingService {
    BookingResponse create(CreateBookingInput input);
    List<BookingResponse> getMyBookings();
    BookingResponse cancel(Long id);
    BookingResponse payForBooking(Long bookingId, CreatePaymentInput input);
}
