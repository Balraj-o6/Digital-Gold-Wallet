package com.example.service;

import com.example.dao.IVendorRepository;
import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;
import com.example.entity.Vendor;
import com.example.exception.VendorNotFoundException;
import com.example.mapper.VendorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendorService implements IVendorService {

	@Autowired
    private IVendorRepository repo;

    

    @Override
    public List<PublicVendorDTO> getAllPublicVendors() {
        List<Vendor> lVend=repo.findAll();
        List<PublicVendorDTO> lPubVend=new ArrayList<>();
        lVend.forEach((e)->lPubVend.add(VendorMapper.convertEntityToPublicDto(e)));
        return lPubVend;
    }

    @Override
    public List<AdminVendorDTO> getAllAdminVendors() {
        List<Vendor> lVend=repo.findAll();
        List<AdminVendorDTO> lAdmVend=new ArrayList<>();
        lVend.forEach((e)->lAdmVend.add(VendorMapper.convertEntityToAdminDto(e)));
        return lAdmVend;
    }

    @Override
    public AdminVendorDTO getVendorByName(String name) throws VendorNotFoundException {
        Optional<Vendor> op=repo.findByVendorName(name);
        if(op.isPresent()){
            return VendorMapper.convertEntityToAdminDto(op.get());
        }
        else throw new VendorNotFoundException("Vendor Not Found !!!");
    }


}
