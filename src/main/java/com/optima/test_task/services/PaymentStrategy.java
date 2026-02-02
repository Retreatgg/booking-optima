package com.optima.test_task.services;

import com.optima.test_task.dtos.CreatePaymentInput;
import com.optima.test_task.enums.PaymentProvider;
import com.optima.test_task.models.Booking;

public interface PaymentStrategy {
    void execute(Booking booking, CreatePaymentInput paymentInput);
    PaymentProvider getProvider();
}
