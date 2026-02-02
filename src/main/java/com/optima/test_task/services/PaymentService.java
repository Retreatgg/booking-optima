package com.optima.test_task.services;

import com.optima.test_task.dtos.CreatePaymentInput;
import com.optima.test_task.dtos.PaymentResponse;
import com.optima.test_task.enums.PaymentType;
import com.optima.test_task.models.Booking;

import java.util.List;

public interface PaymentService {

    void process(Booking booking, CreatePaymentInput paymentInput, PaymentType paymentType);

    List<PaymentResponse> myPayments();
}
