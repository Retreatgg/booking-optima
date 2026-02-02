package com.optima.test_task.controllers;

import com.optima.test_task.dtos.PaymentResponse;
import com.optima.test_task.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @QueryMapping
    @PreAuthorize("hasAuthority('USER')")
    public List<PaymentResponse> myPayments() {
        return paymentService.myPayments();
    }
}
