package com.optima.test_task.services.impls;

import com.optima.test_task.dtos.CreatePaymentInput;
import com.optima.test_task.enums.PaymentProvider;
import com.optima.test_task.exceptions.PaymentException;
import com.optima.test_task.models.Booking;
import com.optima.test_task.services.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardPaymentStrategy implements PaymentStrategy {

    @Override
    public void execute(Booking booking, CreatePaymentInput paymentInput) {
        log.info("Выбран способ оплаты картой");
        throw new PaymentException("Оплата по карте в данный момент не доступно");
    }

    @Override
    public PaymentProvider getProvider() {
        return PaymentProvider.CARD;
    }
}
