package com.example.dao;

import com.example.enums.TransactionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.TransactionHistory;

import java.util.List;

@Repository
public interface ITransactionHistoryRepository extends JpaRepository<TransactionHistory, Integer> {
    List<TransactionHistory> findByUser_UserIdOrderByCreatedAtDesc(Integer userId);
    List<TransactionHistory> findByVendorBranch_BranchId(Integer branchId);

    //added for get transaction by status
    List<TransactionHistory> findByTransactionStatus(TransactionStatus transactionStatus);
}
