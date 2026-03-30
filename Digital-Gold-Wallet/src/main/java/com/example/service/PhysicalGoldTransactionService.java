package com.example.service;

import com.example.dao.IPhysicalGoldTransactionRepository;
import com.example.dto.PhysicalGoldTransactionDTO;
import com.example.entity.PhysicalGoldTransaction;
import com.example.mapper.PhysicalGoldTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhysicalGoldTransactionService implements IPhysicalGoldTransactionService {

    @Autowired
    private IPhysicalGoldTransactionRepository physicalGoldTransactionRepository;

    // ─── Get physical transactions by branch ID ───────────────────────────────
    @Override
    public List<PhysicalGoldTransactionDTO> getPhysicalTransactionsByBranchId(Integer branchId) {

        // This method already exists in your repository — no changes needed there!
        // It means: "find all physical transactions where vendorBranch.branchId = ?"
        List<PhysicalGoldTransaction> transactions =
                physicalGoldTransactionRepository.findByVendorBranch_BranchId(branchId);

        List<PhysicalGoldTransactionDTO> resultList = new ArrayList<>();

        for (PhysicalGoldTransaction txn : transactions) {

            // Your PhysicalGoldTransactionMapper already exists and does this conversion!
            // It maps: transactionId, userName, branchName, quantity, deliveryAddress, requestDate
            PhysicalGoldTransactionDTO dto =
                    PhysicalGoldTransactionMapper.convertEntityToDTO(txn);

            resultList.add(dto);
        }

        return resultList;
    }
}