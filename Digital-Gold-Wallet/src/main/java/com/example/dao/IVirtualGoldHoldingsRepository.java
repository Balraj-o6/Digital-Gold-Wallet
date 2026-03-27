package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.VirtualGoldHoldings;

import java.util.List;

@Repository
public interface IVirtualGoldHoldingsRepository extends JpaRepository<VirtualGoldHoldings, Integer> {
    List<VirtualGoldHoldings> findByUser_UserId(Integer userId);
    List<VirtualGoldHoldings> findByUser_UserIdAndVendorBranch_BranchId(Integer userId, Integer branchId);
}
