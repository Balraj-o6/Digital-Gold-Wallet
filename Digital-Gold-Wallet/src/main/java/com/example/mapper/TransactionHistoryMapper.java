package com.example.mapper;

import com.example.dto.TransactionHistoryDTO;
import com.example.entity.TransactionHistory;

public class TransactionHistoryMapper {

    public static TransactionHistoryDTO convertEntityToDTO(TransactionHistory th) {
        if (th == null) return null;
        TransactionHistoryDTO dto = new TransactionHistoryDTO();
        dto.setTransactionId(th.getTransactionId());
        dto.setUserName(th.getUser() != null ? th.getUser().getName() : null);
        dto.setVendorName(
            th.getVendorBranch() != null && th.getVendorBranch().getVendor() != null
                ? th.getVendorBranch().getVendor().getVendorName()
                : null
        );
        dto.setType(th.getTransactionType() != null ? th.getTransactionType().name() : null);
        dto.setStatus(th.getTransactionStatus() != null ? th.getTransactionStatus().name() : null);
        dto.setQuantity(th.getQuantity());
        dto.setAmount(th.getAmount());
        dto.setDate(th.getCreatedAt());
        return dto;
    }
}