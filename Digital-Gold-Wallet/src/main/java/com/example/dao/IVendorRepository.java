package com.example.dao;

import com.example.dto.AdminVendorDTO;
import com.example.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IVendorRepository extends JpaRepository <Vendor, Integer> {
//    Optional<AdminVendorDTO> findByVendorName (String vendorName);
//    Optional<AdminVendorDTO> findByContactEmail(String contactEmail);
}
