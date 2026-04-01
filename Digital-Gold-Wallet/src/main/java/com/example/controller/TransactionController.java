package com.example.controller;

import com.example.dto.TransactionHistoryDTO;

import com.example.dto.PhysicalGoldTransactionDTO;
import com.example.service.IPhysicalGoldTransactionService;
import com.example.service.ITransactionHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

	private final ITransactionHistoryService transactionHistoryService;
	private final IPhysicalGoldTransactionService physicalGoldTransactionService;

	public TransactionController(ITransactionHistoryService historyService,
			IPhysicalGoldTransactionService physicalService) {
		this.transactionHistoryService = historyService;
		this.physicalGoldTransactionService = physicalService;
	}

	@GetMapping("/branch/{branchId}")
	public ResponseEntity<List<TransactionHistoryDTO>> getTransactionsByBranchId(@PathVariable Integer branchId) {

		List<TransactionHistoryDTO> transactions = transactionHistoryService.getTransactionsByBranchId(branchId);

		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@GetMapping("/physical/branch/{branchId}")
	public ResponseEntity<List<PhysicalGoldTransactionDTO>> getPhysicalTransactionsByBranchId(
			@PathVariable Integer branchId) {

		List<PhysicalGoldTransactionDTO> transactions = physicalGoldTransactionService
				.getPhysicalTransactionsByBranchId(branchId);

		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@GetMapping("/user/{userId}")
	public ResponseEntity<List<TransactionHistoryDTO>> getTransactionHistoryByUserId(@PathVariable Integer userId) {

		List<TransactionHistoryDTO> transactions = transactionHistoryService.getTransactionHistoryByUserId(userId);

		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}

	@GetMapping("/type")
	public ResponseEntity<List<TransactionHistoryDTO>> getTransactionsByType(@RequestParam String type) {
		List<TransactionHistoryDTO> transactions = transactionHistoryService.getTransactionsByType(type);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
	
	@GetMapping("/status")
	public ResponseEntity<List<TransactionHistoryDTO>> getTransactionsByStatus(@RequestParam String status) {

		List<TransactionHistoryDTO> transactions = transactionHistoryService.getTransactionsByStatus(status);
		return new ResponseEntity<>(transactions, HttpStatus.OK);

	}

}
