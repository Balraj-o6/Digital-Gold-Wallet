package com.example.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class AdminVendorDTO {
    private Integer vendorId;
    @NotBlank(message = "Vendor Name is required !")
    @Size(min=2, max=100, message="Vendor name must be between 2 to 100 characters")
    private String vendorName;
    @Email(message = "Enter a valid email address")
    @NotBlank(message = "Contact Email is required !")
    private String contactEmail;
    private String contactPhone;
    @PositiveOrZero(message = "Total Gold Quantity cannot be negative")
    private BigDecimal totalGoldQuantity;
    @DecimalMin(value="0.01", message = "Current gold price must be greater than zero")
    private BigDecimal currentGoldPrice;
    private LocalDateTime createdAt;
}
