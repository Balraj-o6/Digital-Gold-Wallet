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

		List<PhysicalGoldTransactionDTO> resultList = new ArrayList<>();

		for (PhysicalGoldTransaction txn : transactions) {

			PhysicalGoldTransactionDTO dto = PhysicalGoldTransactionMapper.convertEntityToDTO(txn);

			resultList.add(dto);
		}

		return resultList;
	}
}