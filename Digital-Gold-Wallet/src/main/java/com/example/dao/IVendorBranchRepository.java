package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.VendorBranch;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface IVendorBranchRepository extends JpaRepository<VendorBranch, Integer> {
	List<VendorBranch> findByVendor_VendorId(Integer vendorId);

	List<VendorBranch> findByQuantityLessThan(BigDecimal threshold);
}
