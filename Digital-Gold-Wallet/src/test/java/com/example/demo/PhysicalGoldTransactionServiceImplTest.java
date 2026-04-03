package com.example.demo;

import com.example.dto.PhysicalGoldTransactionDTO;
import com.example.entity.PhysicalGoldTransaction;
import com.example.dao.IPhysicalGoldTransactionRepository;
import com.example.service.PhysicalGoldTransactionService;
import com.example.mapper.PhysicalGoldTransactionMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhysicalGoldTransactionServiceImplTest {

    @Mock
    private IPhysicalGoldTransactionRepository repository;

    @InjectMocks
    private PhysicalGoldTransactionService service;

    @Test
    void testGetPhysicalTransactionsByCity_Success() {

        // Mock entity
        PhysicalGoldTransaction txn = new PhysicalGoldTransaction();
        List<PhysicalGoldTransaction> txnList = List.of(txn);

        // Mock DTO
        PhysicalGoldTransactionDTO dto = new PhysicalGoldTransactionDTO();

        when(repository.findByDeliveryAddress_City("Mumbai"))
                .thenReturn(txnList);

        // Mock static mapper
        try (MockedStatic<PhysicalGoldTransactionMapper> mapperMock =
                     mockStatic(PhysicalGoldTransactionMapper.class)) {

            mapperMock.when(() ->
                            PhysicalGoldTransactionMapper.convertEntityToDTO(txn))
                    .thenReturn(dto);

            List<PhysicalGoldTransactionDTO> result =
                    service.getPhysicalTransactionsByCity("Mumbai");

            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals(dto, result.get(0));

            verify(repository, times(1))
                    .findByDeliveryAddress_City("Mumbai");
        }
    }

    @Test
    void testGetPhysicalTransactionsByCity_EmptyList() {

        when(repository.findByDeliveryAddress_City("Delhi"))
                .thenReturn(Collections.emptyList());

        List<PhysicalGoldTransactionDTO> result =
                service.getPhysicalTransactionsByCity("Delhi");

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(repository, times(1))
                .findByDeliveryAddress_City("Delhi");
    }
}