package com.example.mapper;

import com.example.dto.PaymentDTO;
import com.example.entity.Payment;

/**
 * @author Anant
 */

public class PaymentMapper {
    public static PaymentDTO convertEntityToObject(Payment payment){
        if(payment==null) return null;
        PaymentDTO paymentDTO=new PaymentDTO();
        paymentDTO.setPaymentId(payment.getPaymentId());
        paymentDTO.setUserEmail(payment.getUser().getEmail());
        paymentDTO.setAmount(payment.getAmount());
        paymentDTO.setMethod(payment.getPaymentMethod().name());
        paymentDTO.setType(payment.getTransactionType().name());
        paymentDTO.setStatus(payment.getPaymentStatus().name());
        paymentDTO.setTimestamp(payment.getCreatedAt());
        return paymentDTO;
    }
}
