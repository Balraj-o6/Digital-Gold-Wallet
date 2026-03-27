package com.example.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendorBranchDTO {
    private Integer branchId;
    @NotNull(message = "Vendor details are required")
    private Integer vendorId;
    private String vendorName;
    @NotNull(message = "Address details are required")
    private AddressDTO address;
    @PositiveOrZero(message = "Gold quantity cannot be negative")
    private BigDecimal quantity;
}
