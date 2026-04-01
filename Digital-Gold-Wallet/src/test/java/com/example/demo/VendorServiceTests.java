package com.example.demo;

import com.example.dao.IVendorRepository;
import com.example.dto.AdminVendorDTO;
import com.example.entity.Vendor;
import com.example.exception.VendorNotFoundException;
import com.example.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Vendor Service Tests")
public class VendorServiceTests {
    @Mock
    private IVendorRepository repo;

    @InjectMocks
    private VendorService vendorService;

    private Vendor vendor1;
    private Vendor vendor2;
    
    @BeforeEach
    void setUp(){
        vendor1=new Vendor();
        vendor1.setVendorId(1);
        vendor1.setVendorName("GoldMart");
        vendor1.setContactEmail("alice@goldmart.com");
        vendor1.setContactPhone("9876543210");
        vendor1.setTotalGoldQuantity(new BigDecimal("500.00"));
        vendor1.setCurrentGoldPrice(new BigDecimal("13460.00"));
        vendor1.setCreatedAt(LocalDateTime.of(2024, 1, 15, 10, 0));

        vendor2 = new Vendor();
        vendor2.setVendorId(2);
        vendor2.setVendorName("PureGold Co.");
        vendor2.setContactEmail("bob@puregold.com");
        vendor2.setContactPhone("8765432109");
        vendor2.setTotalGoldQuantity(new BigDecimal("750.00"));
        vendor2.setCurrentGoldPrice(new BigDecimal("13500.00"));
        vendor2.setCreatedAt(LocalDateTime.of(2024, 2, 20, 9, 30));
    }

    // For getVendorByName()

    @Nested
    @DisplayName("getVendorByName()")
    class GetVendorByName{
        @Test
        @DisplayName("Returns correct AdminVendorDTO when Vendor exists")
        void shouldReturnAdminVendorDTOWhenVendorExists(){
            when(repo.findByVendorName("GoldMart")).thenReturn(Optional.of(vendor1));
            AdminVendorDTO result=vendorService.getVendorByName("GoldMart");
            assertThat(result).isNotNull();
            assertAll(
                    ()->assertThat(result.getVendorId()).isEqualTo(1),
                    ()->assertThat(result.getVendorName()).isEqualTo("GoldMart"),
                    () -> assertThat(result.getContactEmail()).isEqualTo("alice@goldmart.com"),
                    () -> assertThat(result.getContactPhone()).isEqualTo("9876543210"),
                    () -> assertThat(result.getTotalGoldQuantity()).isEqualByComparingTo("500.00"),
                    () -> assertThat(result.getCurrentGoldPrice()).isEqualByComparingTo("13460.00")
            );
            verify(repo, times(1)).findByVendorName("GoldMart");
        }

        @Test
        @DisplayName("Throw VendorNotFoundException when vendor name does not exist")
        void shouldThrowVendorNotFoundException(){
            when(repo.findByVendorName("Unknown")).thenReturn(Optional.empty());
            assertThatThrownBy(()->vendorService.getVendorByName("Unknown"))
                    .isInstanceOf(VendorNotFoundException.class)
                    .hasMessage("Vendor not found !!!");
        }

        @Test
        @DisplayName("Throw VendorNotFoundException for wrong case name")
        void shouldThrowForWrongCaseName(){
            when(repo.findByVendorName("goldmart")).thenReturn(Optional.empty());
            assertThatThrownBy(()->vendorService.getVendorByName("goldmart"))
                    .isInstanceOf(VendorNotFoundException.class)
                    .hasMessage("Vendor Not Found !!!");
        }

        @Test
        @DisplayName("VendorNotFoundException for empty string name")
        void shouldThrowForEmptyName(){
            when(repo.findByVendorName("")).thenReturn(Optional.empty());
            assertThatThrownBy(()->vendorService.getVendorByName(""))
                    .isInstanceOf(VendorNotFoundException.class)
                    .hasMessage("Vendor Not Found !!!");
        }
    }


    // For getVendorByTotalGoldQuantityGreaterThan()

    @Nested
    @DisplayName("getVendorByTotalGoldQuantityGreaterThan()")
    class GetVendorByTotalGoldQuantityGreaterThan{
        @Test
        @DisplayName("Returns list of AdminVendorDTOs for vendors above the given quantity")
        void shouldReturnVendorAboveGivenQuantity(){
            when(repo.getVendorByTotalGoldQuantityGreaterThanEqual(new BigDecimal("400.00"))).thenReturn(Arrays.asList(vendor1, vendor2));
            List<AdminVendorDTO> result =vendorService.getVendorByTotalGoldQuantityGreaterThan(new BigDecimal("400.00"));
            assertThat(result).hasSize(2);
            assertThat(result.get(0).getVendorName()).isEqualTo("GoldMart");
            assertThat(result.get(1).getVendorName()).isEqualTo("PureGold Co.");
            verify(repo, times(1)).getVendorByTotalGoldQuantityGreaterThanEqual(new BigDecimal("400.00"));
        }

        @Test
        @DisplayName("Returns empty list when no vendors meet the quantity threshold")
        void shouldReturnEmptyListWhenNoVendorsQualify() {
            when(repo.getVendorByTotalGoldQuantityGreaterThanEqual(new BigDecimal("9999.00"))).thenReturn(Collections.emptyList());
            List<AdminVendorDTO> result = vendorService.getVendorByTotalGoldQuantityGreaterThan(new BigDecimal("9999.00"));
            assertThat(result).isNotNull().isEmpty();
        }

        @Test
        @DisplayName("Propagates RuntimeException thrown by repository")
        void shouldPropagateRepositoryException() {
            when(repo.getVendorByTotalGoldQuantityGreaterThanEqual(any(BigDecimal.class))).thenThrow(new RuntimeException("DB connection timeout"));
            assertThatThrownBy(() -> vendorService.getVendorByTotalGoldQuantityGreaterThan(new BigDecimal("100.00")))
                    .isInstanceOf(RuntimeException.class)
                    .hasMessageContaining("DB connection timeout");
        }

        @Test
        @DisplayName("Returns empty list when quantity is very large (no vendor qualifies)")
        void shouldReturnEmptyListForExcessivelyLargeQuantity() {
            when(repo.getVendorByTotalGoldQuantityGreaterThanEqual(new BigDecimal("999999.00"))).thenReturn(Collections.emptyList());
            List<AdminVendorDTO> result = vendorService.getVendorByTotalGoldQuantityGreaterThan(new BigDecimal("999999.00"));
            assertThat(result).isEmpty();
        }
    }



}
