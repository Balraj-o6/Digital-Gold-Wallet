package com.example.mapper;


import com.example.dto.PhysicalGoldTransactionDTO;
import com.example.entity.PhysicalGoldTransaction;

public class PhysicalGoldTransactionMapper {
    public static PhysicalGoldTransactionDTO convertEntityToDTO(PhysicalGoldTransaction pgTxn){
        if(pgTxn==null) return null;
        PhysicalGoldTransactionDTO pgTxnDTO=new PhysicalGoldTransactionDTO();
        pgTxnDTO.setTransactionId(pgTxn.getTransactionId());
        pgTxnDTO.setQuantity(pgTxn.getQuantity());
        pgTxnDTO.setBranchName(pgTxn.getVendorBranch().getVendor().getVendorName());
        pgTxnDTO.setUserName(pgTxn.getUser().getName());
        pgTxnDTO.setDeliveryAddress(AddressMapper.convertEntityToObject(pgTxn.getDeliveryAddress()));
        pgTxnDTO.setRequestDate(pgTxn.getCreatedAt());
        return pgTxnDTO;
    }
}
