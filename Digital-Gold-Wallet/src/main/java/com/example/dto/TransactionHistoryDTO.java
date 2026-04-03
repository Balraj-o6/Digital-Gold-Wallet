package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Anant
 */

@Data
public class TransactionHistoryDTO {

    private Integer transactionId;
    private String userName;
    private String vendorName;
    private String type;
    private String status;
    private BigDecimal quantity;
    private BigDecimal amount;
    private LocalDateTime date;
}

