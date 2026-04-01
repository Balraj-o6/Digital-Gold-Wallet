package com.example.service;

import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;

import java.math.BigDecimal;
import java.util.List;

public interface IVendorService {
	public AdminVendorDTO getVendorByName(String name);
	public List<AdminVendorDTO> getVendorByTotalGoldQuantityGreaterThan(BigDecimal quantity);

}
