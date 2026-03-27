package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.PhysicalGoldTransaction;

import java.util.List;

@Repository
public interface IPhysicalGoldTransactionRepository extends JpaRepository<PhysicalGoldTransaction, Integer> {

    List<PhysicalGoldTransaction> findByUser_UserId(Integer userId);

    List<PhysicalGoldTransaction> findByVendorBranch_BranchId(Integer branchId);

    List<PhysicalGoldTransaction> findByDeliveryAddress_City(String city);
}
