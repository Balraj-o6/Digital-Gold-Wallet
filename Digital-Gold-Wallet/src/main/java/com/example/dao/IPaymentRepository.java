package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Payment;

import java.util.List;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Integer> {
    List<Payment> findByUser_UserIdOrderByCreatedAtDesc(Integer userId);
    List<Payment> findByPaymentStatus(String status);
}
