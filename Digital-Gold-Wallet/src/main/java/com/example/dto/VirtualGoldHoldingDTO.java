package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VirtualGoldHoldingDTO {
    private Integer holdingId;
    @NotNull(message="User ID is required")
    private Integer userId;
    @NotNull(message="Branch Id is required")
    private Integer branchId;
    private String vendorName;
    @Positive(message = "Quantity must be greater than zero")
    private BigDecimal quantity;

}
