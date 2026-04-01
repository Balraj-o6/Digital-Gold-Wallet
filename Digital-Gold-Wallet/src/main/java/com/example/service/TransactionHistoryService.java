package com.example.service;

import com.example.dao.ITransactionHistoryRepository;
import com.example.dto.TransactionHistoryDTO;
import com.example.entity.TransactionHistory;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import com.example.exception.TransactionNotFoundException;
import com.example.exception.UserNotFoundException;
import com.example.mapper.TransactionHistoryMapper;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryService implements ITransactionHistoryService {

	private ITransactionHistoryRepository transactionHistoryRepository;

	public TransactionHistoryService(ITransactionHistoryRepository historyRepo) {
		this.transactionHistoryRepository = historyRepo;
	}

	@Override
	public List<TransactionHistoryDTO> getTransactionsByStatus(String status) throws TransactionNotFoundException {

		TransactionStatus transactionStatus = TransactionStatus.valueOf(status);

		List<TransactionHistory> transactions = transactionHistoryRepository.findByTransactionStatus(transactionStatus);

		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transaction history found for transaction status: " + status);
		}

		List<TransactionHistoryDTO> resultList = new ArrayList<>();

		for (TransactionHistory txn : transactions) {

			TransactionHistoryDTO dto = new TransactionHistoryDTO();

			dto.setTransactionId(txn.getTransactionId());

			dto.setUserName(txn.getUser() != null ? txn.getUser().getName() : "N/A");

			if (txn.getVendorBranch() != null && txn.getVendorBranch().getVendor() != null) {
				dto.setVendorName(txn.getVendorBranch().getVendor().getVendorName());
			} else {
				dto.setVendorName("N/A");
			}

			dto.setType(txn.getTransactionType() != null ? txn.getTransactionType().name() : "N/A");

			dto.setStatus(txn.getTransactionStatus() != null ? txn.getTransactionStatus().name() : "N/A");

			dto.setQuantity(txn.getQuantity());
			dto.setAmount(txn.getAmount());
			dto.setDate(txn.getCreatedAt());

			resultList.add(dto);
		}

		return resultList;
	}

	@Override
	public List<TransactionHistoryDTO> getTransactionsByBranchId(Integer branchId) throws TransactionNotFoundException {

		List<TransactionHistory> transactions = transactionHistoryRepository.findByVendorBranch_BranchId(branchId);

		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transaction history found for branch Id: " + branchId);
		}

		List<TransactionHistoryDTO> resultList = new ArrayList<>();

		for (TransactionHistory txn : transactions) {

			TransactionHistoryDTO dto = new TransactionHistoryDTO();

			dto.setTransactionId(txn.getTransactionId());

			dto.setUserName(txn.getUser() != null ? txn.getUser().getName() : "N/A");

			if (txn.getVendorBranch() != null && txn.getVendorBranch().getVendor() != null) {
				dto.setVendorName(txn.getVendorBranch().getVendor().getVendorName());
			} else {
				dto.setVendorName("N/A");
			}

			dto.setType(txn.getTransactionType() != null ? txn.getTransactionType().name() : "N/A");

			dto.setStatus(txn.getTransactionStatus() != null ? txn.getTransactionStatus().name() : "N/A");

			dto.setQuantity(txn.getQuantity());
			dto.setAmount(txn.getAmount());
			dto.setDate(txn.getCreatedAt());

			resultList.add(dto);
		}

		return resultList;
	}

	@Override
	public List<TransactionHistoryDTO> getTransactionHistoryByUserId(Integer userId) {

		List<TransactionHistory> transactions = transactionHistoryRepository
				.findByUser_UserIdOrderByCreatedAtDesc(userId);

		if (transactions.isEmpty()) {
			throw new UserNotFoundException("No transaction history found for user ID: " + userId);
		}

		return transactions.stream().map(TransactionHistoryMapper::convertEntityToDTO).collect(Collectors.toList());

	}

	@Override
	public List<TransactionHistoryDTO> getTransactionsByType(String type) throws TransactionNotFoundException {
		TransactionType transactionType = TransactionType.fromValue(type);
		List<TransactionHistory> transactions = transactionHistoryRepository.findByTransactionType(transactionType);
		return transactions.stream().map(TransactionHistoryMapper::convertEntityToDTO).collect(Collectors.toList());
	}

}
