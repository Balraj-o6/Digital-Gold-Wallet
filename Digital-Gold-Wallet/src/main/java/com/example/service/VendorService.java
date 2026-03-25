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

    private final IVendorRepository repo;

    @Autowired
    public VendorService(IVendorRepository repo) {
        this.repo = repo;
    }

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
    public AdminVendorDTO getVendorById(Integer id) throws VendorNotFoundException {
        Optional<Vendor> op=repo.findById(id);
        if(op.isPresent()){
            return VendorMapper.convertEntityToAdminDto(op.get());
        }
        else throw new VendorNotFoundException("Vendor Not Found !!!");
    }

    @Override
    public AdminVendorDTO saveVendor(AdminVendorDTO avDto) {
        Vendor savedVendor=repo.saveAndFlush(VendorMapper.convertAdminDtoToEntity(avDto));
        return VendorMapper.convertEntityToAdminDto(savedVendor);
    }

    @Override
    public String deleteVendor(Integer id) {
        getVendorById(id);
        repo.deleteById(id);
        return "Vendor Deleted Successfully !!!";
    }
}
