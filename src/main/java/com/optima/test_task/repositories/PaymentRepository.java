package com.optima.test_task.repositories;

import com.optima.test_task.models.Payment;
import com.optima.test_task.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByBookingUser(User user);
}
