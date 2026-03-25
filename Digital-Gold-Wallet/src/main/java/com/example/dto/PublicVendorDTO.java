package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PublicVendorDTO {
    private String vendorName;
    private String description;
    private String websiteUrl;
    private BigDecimal currentGoldPrice;
}
