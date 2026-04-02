package com.example.controller;

import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;
import com.example.service.IVendorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {

	private final IVendorService service;

	public VendorController(IVendorService vendorService) {
		this.service = vendorService;
	}


	@GetMapping("/{name}")
	public ResponseEntity<AdminVendorDTO> getVendorByName(@PathVariable String name) {
		AdminVendorDTO avDto = service.getVendorByName(name);
		return new ResponseEntity<>(avDto, HttpStatus.OK);
	}

	@GetMapping("/quantity/{quantity}")
	public ResponseEntity<List<AdminVendorDTO>> getVendorByTotalGoldQuantityMoreThan(@PathVariable BigDecimal quantity){
		List<AdminVendorDTO> lAdminDTO=service.getVendorByTotalGoldQuantityGreaterThan(quantity);
		return new ResponseEntity<>(lAdminDTO, HttpStatus.OK);
	}

}
