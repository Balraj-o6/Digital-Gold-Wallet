package com.example.controller;

import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;
import com.example.service.IVendorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendors")
public class VendorController {
	
	@Autowired
    private IVendorService service;

    @GetMapping("/public")
    public ResponseEntity<List<PublicVendorDTO>>  getAllPublicVendors(){
        List<PublicVendorDTO> publicVendors= service.getAllPublicVendors();
        return new ResponseEntity<>(publicVendors, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<AdminVendorDTO>> getAllAdminVendors(){
        List<AdminVendorDTO> adminVendors=service.getAllAdminVendors();
        return new ResponseEntity<>(adminVendors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminVendorDTO> getVendorById(@PathVariable Integer id){
        AdminVendorDTO avDto=service.getVendorById(id);
        return new ResponseEntity<>(avDto, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<AdminVendorDTO> createVendor(@Valid @RequestBody AdminVendorDTO avDto){
        AdminVendorDTO savedVendor=service.saveVendor(avDto);
        return new ResponseEntity<>(savedVendor, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable Integer id){
        String output=service.deleteVendor(id);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

}
