package com.example.mapper;

import com.example.dto.VendorBranchDTO;
import com.example.entity.VendorBranch;

public class VendorBranchMapper {
    public static VendorBranchDTO convertEntityToObject (VendorBranch vBranch){
        if(vBranch==null) return null;
        VendorBranchDTO vbDTO = new VendorBranchDTO();
        vbDTO.setBranchId(vBranch.getBranchId());
        vbDTO.setQuantity(vBranch.getQuantity());
        if(vBranch.getVendor()!=null){
            vbDTO.setVendorId(vBranch.getVendor().getVendorId());
            vbDTO.setVendorName(vBranch.getVendor().getVendorName());
        }
        vbDTO.setAddress(AddressMapper.convertEntityToObject(vBranch.getAddress()));
        return vbDTO;
    }


}
