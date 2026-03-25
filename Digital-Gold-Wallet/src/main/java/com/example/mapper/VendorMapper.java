package com.example.mapper;

import com.example.dto.AdminVendorDTO;
import com.example.dto.PublicVendorDTO;
import com.example.entity.Vendor;
import org.springframework.stereotype.Component;


public class VendorMapper {
    public static PublicVendorDTO convertEntityToPublicDto (Vendor vendor){
        if(vendor==null) return null;
        PublicVendorDTO pvDto=new PublicVendorDTO();
        pvDto.setVendorName(vendor.getVendorName());
        pvDto.setDescription(vendor.getDescription());
        pvDto.setWebsiteUrl(vendor.getWebsiteUrl());
        pvDto.setCurrentGoldPrice(vendor.getCurrentGoldPrice());
        return pvDto;
    }
    public static AdminVendorDTO convertEntityToAdminDto (Vendor vendor){
        if(vendor==null) return null;
        AdminVendorDTO avDto=new AdminVendorDTO();
        avDto.setVendorId(vendor.getVendorId());
        avDto.setVendorName(vendor.getVendorName());
        avDto.setContactEmail(vendor.getContactEmail());
        avDto.setContactPhone(vendor.getContactPhone());
        avDto.setTotalGoldQuantity(vendor.getTotalGoldQuantity());
        avDto.setCurrentGoldPrice(vendor.getCurrentGoldPrice());
        avDto.setCreatedAt(vendor.getCreatedAt());
        return avDto;
    }
    public static Vendor convertAdminDtoToEntity(AdminVendorDTO avDto){
        if(avDto==null) return null;
        Vendor vendor=new Vendor();
        vendor.setVendorId(avDto.getVendorId());
        vendor.setVendorName(avDto.getVendorName());
        vendor.setContactEmail(avDto.getContactEmail());
        vendor.setContactPhone(avDto.getContactPhone());
        vendor.setTotalGoldQuantity(avDto.getTotalGoldQuantity());
        vendor.setCurrentGoldPrice(avDto.getCurrentGoldPrice());
        return vendor;
    }
}
