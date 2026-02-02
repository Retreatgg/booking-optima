package com.optima.test_task.services.impls;

import com.optima.test_task.dtos.CreatePaymentInput;
import com.optima.test_task.enums.PaymentProvider;
import com.optima.test_task.models.Booking;
import com.optima.test_task.services.PaymentStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayPalPaymentStrategy implements PaymentStrategy {

    @Override
    public void execute(Booking booking, CreatePaymentInput paymentInput) {
        log.info("Выбран способ оплаты через PayPal");
        // реализация оплаты через paypal
    }

    @Override
    public PaymentProvider getProvider() {
        return PaymentProvider.PAYPAL;
    }
}
