package com.example.service;

import com.example.dto.PhysicalGoldTransactionDTO;
import java.util.List;

public interface IPhysicalGoldTransactionService {

	// Get all physical gold transactions by branch ID
	List<PhysicalGoldTransactionDTO> getPhysicalTransactionsByBranchId(Integer branchId);
}