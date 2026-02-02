package com.optima.test_task.mappers;

import com.optima.test_task.dtos.BookingResponse;
import com.optima.test_task.models.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {

    BookingResponse toResponse(Booking booking);
    List<BookingResponse> toResponseList(List<Booking> bookings);

    default OffsetDateTime map(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.atOffset(ZoneOffset.UTC);
    }

}
