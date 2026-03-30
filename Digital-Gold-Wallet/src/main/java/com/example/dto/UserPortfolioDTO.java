package com.example.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class UserPortfolioDTO {
    private Integer userId;
    private String userName;
    private String userEmail;
    private BigDecimal balance;
    private AddressDTO address;
    private List<VirtualGoldHoldingDTO> virtualHoldings;
    private List<PhysicalGoldTransactionDTO> physicalTransactions;
    private BigDecimal totalVirtualGold;
    private BigDecimal totalPhysicalGold;
    private BigDecimal totalGold;
}