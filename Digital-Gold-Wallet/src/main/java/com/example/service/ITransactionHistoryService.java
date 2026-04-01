package com.example.service;

import com.example.dto.TransactionHistoryDTO;
import com.example.exception.TransactionNotFoundException;

import java.util.List;

public interface ITransactionHistoryService {

	// Get all transactions filtered by status — "Success" or "Failed"
	List<TransactionHistoryDTO> getTransactionsByStatus(String status) throws TransactionNotFoundException;

	List<TransactionHistoryDTO> getTransactionsByBranchId(Integer branchId) throws TransactionNotFoundException;

	List<TransactionHistoryDTO> getTransactionHistoryByUserId(Integer userId);

	List<TransactionHistoryDTO> getTransactionsByType(String type) throws TransactionNotFoundException;
}
