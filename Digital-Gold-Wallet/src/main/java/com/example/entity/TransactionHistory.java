package com.example.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "transaction_history")
public class TransactionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="transaction_id")
    private Integer transactionId;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private VendorBranch vendorBranch;

    @Column(name="transaction_type")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name="transaction_status")
    private TransactionStatus transactionStatus;

    @Column(nullable = false, precision = 10, scale=2)
    private BigDecimal quantity;
    @Column(nullable=false, precision = 18, scale=2)
    private BigDecimal amount;

    @Column(name="created_at", nullable=false, updatable = false)
    private LocalDateTime createdAt;

}
