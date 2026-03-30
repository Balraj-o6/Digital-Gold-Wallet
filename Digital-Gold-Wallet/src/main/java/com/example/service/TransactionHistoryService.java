package com.example.service;


import com.example.dao.ITransactionHistoryRepository;
import com.example.dto.TransactionHistoryDTO;
import com.example.entity.TransactionHistory;
import com.example.enums.TransactionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryService implements ITransactionHistoryService {

    @Autowired
    private ITransactionHistoryRepository transactionHistoryRepository;

    @Override
    public List<TransactionHistoryDTO> getTransactionsByStatus(String status) {

        TransactionStatus transactionStatus = TransactionStatus.valueOf(status);


        List<TransactionHistory> transactions =
                transactionHistoryRepository.findByTransactionStatus(transactionStatus);


        List<TransactionHistoryDTO> resultList = new ArrayList<>();

        for (TransactionHistory txn : transactions) {

            TransactionHistoryDTO dto = new TransactionHistoryDTO();

            dto.setTransactionId(txn.getTransactionId());

            // Safe null check
            dto.setUserName(txn.getUser() != null ? txn.getUser().getName() : "N/A");

            // Goes into VendorBranch → Vendor → VendorName
            if (txn.getVendorBranch() != null && txn.getVendorBranch().getVendor() != null) {
                dto.setVendorName(txn.getVendorBranch().getVendor().getVendorName());
            } else {
                dto.setVendorName("N/A");
            }

            dto.setType(txn.getTransactionType() != null
                    ? txn.getTransactionType().name() : "N/A");

            dto.setStatus(txn.getTransactionStatus() != null
                    ? txn.getTransactionStatus().name() : "N/A");

            dto.setQuantity(txn.getQuantity());
            dto.setAmount(txn.getAmount());
            dto.setDate(txn.getCreatedAt());

            resultList.add(dto);
        }

        return resultList;
    }

    @Override
    public List<TransactionHistoryDTO> getTransactionsByBranchId(Integer branchId) {

        // Step 1: Go to the database and find all transactions for this branch
        List<TransactionHistory> transactions =
                transactionHistoryRepository.findByVendorBranch_BranchId(branchId);
        // Note: findByVendorBranch_BranchId already exists in your repository!
        // It means: "find where vendorBranch.branchId = ?"

        // Step 2: Convert each database record into a DTO (Data Transfer Object)
        // A DTO is just a clean object we send back to the user
        List<TransactionHistoryDTO> resultList = new ArrayList<>();

        for (TransactionHistory txn : transactions) {

            TransactionHistoryDTO dto = new TransactionHistoryDTO();

            dto.setTransactionId(txn.getTransactionId());

            // Safe null check — what if user is deleted?
            dto.setUserName(txn.getUser() != null ? txn.getUser().getName() : "N/A");

            // Goes: txn → vendorBranch → vendor → vendorName
            if (txn.getVendorBranch() != null && txn.getVendorBranch().getVendor() != null) {
                dto.setVendorName(txn.getVendorBranch().getVendor().getVendorName());
            } else {
                dto.setVendorName("N/A");
            }

            dto.setType(txn.getTransactionType() != null
                    ? txn.getTransactionType().name() : "N/A");

            dto.setStatus(txn.getTransactionStatus() != null
                    ? txn.getTransactionStatus().name() : "N/A");

            dto.setQuantity(txn.getQuantity());
            dto.setAmount(txn.getAmount());
            dto.setDate(txn.getCreatedAt());

            resultList.add(dto);
        }

        return resultList;
    }
	
}
