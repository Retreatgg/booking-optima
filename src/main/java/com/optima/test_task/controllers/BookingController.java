package com.optima.test_task.controllers;

import com.optima.test_task.dtos.BookingResponse;
import com.optima.test_task.dtos.CreateBookingInput;
import com.optima.test_task.dtos.CreatePaymentInput;
import com.optima.test_task.services.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @MutationMapping
    @PreAuthorize("hasAuthority('USER')")
    public BookingResponse createBooking(@Argument CreateBookingInput input) {
        return bookingService.create(input);
    }

    @QueryMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<BookingResponse> myBookings() {
        return bookingService.getMyBookings();
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('USER')")
    public BookingResponse cancelBooking(@Argument Long id) {
        return bookingService.cancel(id);
    }

    @MutationMapping
    @PreAuthorize("hasAuthority('USER')")
    public BookingResponse payForBooking(@Argument Long bookingId, @Argument CreatePaymentInput input) {
        return bookingService.payForBooking(bookingId, input);
    }

}
