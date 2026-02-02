package com.optima.test_task.repositories;

import com.optima.test_task.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("""
        SELECT COUNT(b) > 0
        FROM Booking b
        WHERE b.resource.id = :resourceId
        AND b.status != 'CANCELLED'
        AND b.startTime < :endTime
        AND b.endTime > :startTime
    """)
    boolean hasOverlappingBookings(
            @Param("resourceId") Long resourceId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    List<Booking> findByUserId(Long userId);
}
