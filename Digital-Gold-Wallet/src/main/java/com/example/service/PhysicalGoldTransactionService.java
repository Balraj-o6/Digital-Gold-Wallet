package com.example.service;

import com.example.dao.IPhysicalGoldTransactionRepository;
import com.example.dto.PhysicalGoldTransactionDTO;
import com.example.entity.PhysicalGoldTransaction;
import com.example.exception.TransactionNotFoundException;
import com.example.mapper.PhysicalGoldTransactionMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhysicalGoldTransactionService implements IPhysicalGoldTransactionService {

	private final IPhysicalGoldTransactionRepository physicalGoldTransactionRepository;

	public PhysicalGoldTransactionService(IPhysicalGoldTransactionRepository repo) {
		this.physicalGoldTransactionRepository = repo;
	}

	@Override
	public List<PhysicalGoldTransactionDTO> getPhysicalTransactionsByBranchId(Integer branchId) {

		List<PhysicalGoldTransaction> transactions = physicalGoldTransactionRepository
				.findByVendorBranch_BranchId(branchId);

		if (transactions.isEmpty()) {
			throw new TransactionNotFoundException("No transaction history found for branch Id: " + branchId);
		}

		List<PhysicalGoldTransactionDTO> resultList = new ArrayList<>();

		for (PhysicalGoldTransaction txn : transactions) {

			PhysicalGoldTransactionDTO dto = PhysicalGoldTransactionMapper.convertEntityToDTO(txn);

			resultList.add(dto);
		}

		return resultList;
	}


	@Override
	public List<PhysicalGoldTransactionDTO> getPhysicalTransactionsByCity(String city) {

		// How this works step by step:
		// 1. physical_gold_transactions table has delivery_address_id
		// 2. That links to addresses table which has city column
		// 3. findByDeliveryAddress_City() already exists in your repository
		//    It means: find all rows where deliveryAddress.city = ?
		List<PhysicalGoldTransaction> transactions =
				physicalGoldTransactionRepository.findByDeliveryAddress_City(city);

		List<PhysicalGoldTransactionDTO> resultList = new ArrayList<>();

		for (PhysicalGoldTransaction txn : transactions) {

			// PhysicalGoldTransactionMapper already handles everything:
			// → transactionId, userName, branchName, quantity,
			//   deliveryAddress (street, city, state, postalCode, country), requestDate
			PhysicalGoldTransactionDTO dto =
					PhysicalGoldTransactionMapper.convertEntityToDTO(txn);

			resultList.add(dto);
		}

		return resultList;
	}
}