package com.example.dto;

import lombok.Data;
import java.math.BigDecimal;

/**
 * @author Anant
 */

@Data
public class TopUserHoldingDTO {

    private Integer userId;
    private String userName;
    private String userEmail;
    private BigDecimal virtualGoldQuantity;
    private BigDecimal physicalGoldQuantity;
    private BigDecimal totalGoldQuantity;
}
