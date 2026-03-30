package com.example.service;

import com.example.dto.TransactionHistoryDTO;

import java.util.List;


    public interface ITransactionHistoryService {
    	
    	
        // Get all transactions filtered by status — "Success" or "Failed"
        List<TransactionHistoryDTO> getTransactionsByStatus(String status);
        List<TransactionHistoryDTO> getTransactionsByBranchId(Integer branchId);
    }

