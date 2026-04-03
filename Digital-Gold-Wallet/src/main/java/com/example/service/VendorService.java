package com.example.service;

import com.example.dao.IVendorRepository;
import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;
import com.example.entity.Vendor;
import com.example.exception.VendorNotFoundException;
import com.example.mapper.VendorMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Anant
 */

@Service
public class VendorService implements IVendorService {

	private IVendorRepository repo;

//	Constructor-injection
	public VendorService(IVendorRepository repo) {
		this.repo = repo;
	}


	@Override
	public AdminVendorDTO getVendorByName(String name) throws VendorNotFoundException {
		Optional<Vendor> op = repo.findByVendorName(name); // Fetch vendor by name
		if (op.isPresent()) {
			return VendorMapper.convertEntityToAdminDto(op.get());
		} else
			throw new VendorNotFoundException("Vendor Not Found !!!");
	}

	@Override
	public List<AdminVendorDTO> getVendorByTotalGoldQuantityGreaterThan(BigDecimal quantity) {
		List<Vendor> lVendDTO=repo.getVendorByTotalGoldQuantityGreaterThanEqual(quantity); // Fetch vendors with quantity >= given value
		List<AdminVendorDTO> lAdminVendDTO=new ArrayList<>();
		lVendDTO.forEach((e)->lAdminVendDTO.add(VendorMapper.convertEntityToAdminDto(e)));
		if(lAdminVendDTO.isEmpty()) throw new VendorNotFoundException("Vendor Not Found !!!");
		return lAdminVendDTO;
	}

}
