package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.enums.PaymentMethod;
import com.example.enums.PaymentStatus;
import com.example.enums.TransactionType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="payment_id")
    private Integer paymentId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, precision = 18, scale=2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    @Column(name="created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

}
