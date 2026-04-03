package com.example.demo;

import com.example.dto.TransactionHistoryDTO;
import com.example.entity.*;
import com.example.enums.TransactionStatus;
import com.example.enums.TransactionType;
import com.example.exception.TransactionNotFoundException;
import com.example.dao.ITransactionHistoryRepository;

import com.example.service.TransactionHistoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionHistoryServiceImplTest {

    @Mock
    private ITransactionHistoryRepository repository;

    @InjectMocks
    private TransactionHistoryService service;

    // ✅ SUCCESS CASE
    @Test
    void testGetTransactionsByBranchId_Success() {

        TransactionHistory txn = new TransactionHistory();
        txn.setTransactionId(1);

        User user = new User();
        user.setName("Sankalp");
        txn.setUser(user);

        Vendor vendor = new Vendor();
        vendor.setVendorName("Gold Shop");

        VendorBranch branch = new VendorBranch();
        branch.setVendor(vendor);
        txn.setVendorBranch(branch);

        txn.setTransactionType(TransactionType.BUY);
        txn.setTransactionStatus(TransactionStatus.SUCCESS);

        txn.setQuantity(BigDecimal.valueOf(10.0));
        txn.setAmount(BigDecimal.valueOf(50000.0));
        txn.setCreatedAt(LocalDateTime.now());

        when(repository.findByVendorBranch_BranchId(1))
                .thenReturn(List.of(txn));

        List<TransactionHistoryDTO> result =
                service.getTransactionsByBranchId(1);

        assertNotNull(result);
        assertEquals(1, result.size());

        TransactionHistoryDTO dto = result.get(0);

        assertEquals("Sankalp", dto.getUserName());
        assertEquals("Gold Shop", dto.getVendorName());
        assertEquals("BUY", dto.getType());
        assertEquals("SUCCESS", dto.getStatus());

        verify(repository, times(1))
                .findByVendorBranch_BranchId(1);
    }

    // ❌ NO DATA CASE
    @Test
    void testGetTransactionsByBranchId_NoData() {

        when(repository.findByVendorBranch_BranchId(2))
                .thenReturn(Collections.emptyList());

        assertThrows(TransactionNotFoundException.class, () ->
                service.getTransactionsByBranchId(2)
        );

        verify(repository, times(1))
                .findByVendorBranch_BranchId(2);
    }

    // ⚠️ NULL SAFETY CASE
    @Test
    void testGetTransactionsByBranchId_NullFields() {

        TransactionHistory txn = new TransactionHistory();
        txn.setTransactionId(1);

        txn.setUser(null);
        txn.setVendorBranch(null);
        txn.setTransactionType(null);
        txn.setTransactionStatus(null);

        txn.setQuantity(BigDecimal.valueOf(5.0));
        txn.setAmount(BigDecimal.valueOf(10000.0));
        txn.setCreatedAt(LocalDateTime.now());

        when(repository.findByVendorBranch_BranchId(3))
                .thenReturn(List.of(txn));

        List<TransactionHistoryDTO> result =
                service.getTransactionsByBranchId(3);

        TransactionHistoryDTO dto = result.get(0);

        assertEquals("N/A", dto.getUserName());
        assertEquals("N/A", dto.getVendorName());
        assertEquals("N/A", dto.getType());
        assertEquals("N/A", dto.getStatus());
    }
}