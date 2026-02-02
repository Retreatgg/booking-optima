package com.optima.test_task.mappers;

import com.optima.test_task.dtos.PaymentResponse;
import com.optima.test_task.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {

    @Mapping(target = "paymentStatus", source = "status")
    PaymentResponse toResponse(Payment payment);
    List<PaymentResponse> toResponseList(List<Payment> payments);

    default OffsetDateTime map(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atOffset(ZoneOffset.UTC);
    }
}
