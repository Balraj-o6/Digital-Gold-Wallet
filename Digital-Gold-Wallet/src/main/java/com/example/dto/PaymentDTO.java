package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Anant
 */

@Data
public class PaymentDTO {
    private Integer paymentId;
    private String userEmail;
    private BigDecimal amount;
    private String method;
    private String type;
    private String status;
    private LocalDateTime timestamp;
}
