package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Anant
 */

@Data
public class PhysicalGoldTransactionDTO {
    private Integer transactionId;
    private String userName;
    private String branchName;
    private BigDecimal quantity;
    private AddressDTO deliveryAddress;
    private LocalDateTime requestDate;
}
