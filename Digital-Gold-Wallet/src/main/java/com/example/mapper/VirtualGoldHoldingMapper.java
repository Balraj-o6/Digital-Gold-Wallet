package com.example.mapper;

import com.example.dto.VirtualGoldHoldingDTO;
import com.example.entity.VirtualGoldHoldings;

public class VirtualGoldHoldingMapper {
    public static VirtualGoldHoldingDTO convertEntityToObject(VirtualGoldHoldings vgHolds){
        if(vgHolds==null) return null;
        VirtualGoldHoldingDTO vgHoldDTO=new VirtualGoldHoldingDTO();
        vgHoldDTO.setHoldingId(vgHolds.getHoldingId());
        vgHoldDTO.setQuantity(vgHolds.getQuantity());
        vgHoldDTO.setBranchId(vgHolds.getVendorBranch().getBranchId());
        vgHoldDTO.setUserId(vgHolds.getUser().getUserId());
        if(vgHolds.getVendorBranch() != null && vgHolds.getVendorBranch().getVendor()!=null){
            vgHoldDTO.setVendorName(vgHolds.getVendorBranch().getVendor().getVendorName());
        }
        return vgHoldDTO;
    }
}
