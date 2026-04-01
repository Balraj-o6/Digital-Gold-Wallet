package com.example.service;

import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;

import java.util.List;

public interface IVendorService {
	public List<PublicVendorDTO> getAllPublicVendors();

	public List<AdminVendorDTO> getAllAdminVendors();

	public AdminVendorDTO getVendorByName(String name);

}
