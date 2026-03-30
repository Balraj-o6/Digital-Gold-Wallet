package com.example.service;

import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;
import com.example.entity.Vendor;

import java.util.List;
import java.util.Optional;

public interface IVendorService {
    public List<PublicVendorDTO> getAllPublicVendors();
    public List<AdminVendorDTO> getAllAdminVendors();
    public AdminVendorDTO getVendorByName(String name);

}
