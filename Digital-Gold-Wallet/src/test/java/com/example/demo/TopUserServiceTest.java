package com.example.demo;

import com.example.dao.IPhysicalGoldTransactionRepository;
import com.example.dao.IUserRepository;
import com.example.dao.IVirtualGoldHoldingsRepository;
import com.example.dto.TopUserHoldingDTO;
import com.example.entity.PhysicalGoldTransaction;
import com.example.entity.User;
import com.example.entity.VirtualGoldHoldings;
import com.example.exception.UserNotFoundException;
import com.example.service.TopUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// @ExtendWith — tells JUnit to use Mockito
// No Spring app starts, tests run fast
@ExtendWith(MockitoExtension.class)
@DisplayName("Top User Service Tests")
public class TopUserServiceTest {

    // @Mock — creates FAKE repositories
    // No real DB is used — Mockito pretends to be the DB
    @Mock
    private IUserRepository userRepository;

    @Mock
    private IVirtualGoldHoldingsRepository virtualGoldHoldingsRepository;

    @Mock
    private IPhysicalGoldTransactionRepository physicalGoldTransactionRepository;

    // @InjectMocks — creates REAL TopUserService
    // and puts the above fake repositories inside it
    @InjectMocks
    private TopUserService topUserService;


    @Test
    @DisplayName("Positive Test: Users are correctly ranked by total gold")
    public void testGetTopUsers_ReturnsSortedList() {

        // ── Create two fake users ──
        User user1 = new User();
        user1.setUserId(1);
        user1.setName("Aman Gupta");
        user1.setEmail("aman.gupta@example.in");

        User user2 = new User();
        user2.setUserId(2);
        user2.setName("Bhuvan Sharma");
        user2.setEmail("bhuvan.sharma@example.in");

        // ── Create fake virtual holdings ──

        // Aman has 5.25g virtual gold
        VirtualGoldHoldings holding1 = new VirtualGoldHoldings();
        holding1.setQuantity(new BigDecimal("5.25"));
        holding1.setUser(user1);

        // Bhuvan has 20.00g virtual gold → should rank HIGHER
        VirtualGoldHoldings holding2 = new VirtualGoldHoldings();
        holding2.setQuantity(new BigDecimal("20.00"));
        holding2.setUser(user2);

        // ── Create fake physical transactions ──

        // Aman has 2.00g physical gold
        // Aman total = 5.25 + 2.00 = 7.25g
        PhysicalGoldTransaction txn1 = new PhysicalGoldTransaction();
        txn1.setQuantity(new BigDecimal("2.00"));
        txn1.setUser(user1);

        // Bhuvan has NO physical gold
        // Bhuvan total = 20.00 + 0.00 = 20.00g

        // ── Tell Mockito what to return ──

        // findAll() → returns our 2 fake users (list is NOT empty)
        when(userRepository.findAll())
                .thenReturn(Arrays.asList(user1, user2));

        // Virtual holdings for user 1 → 5.25g
        when(virtualGoldHoldingsRepository.findByUser_UserId(1))
                .thenReturn(Arrays.asList(holding1));

        // Virtual holdings for user 2 → 20.00g
        when(virtualGoldHoldingsRepository.findByUser_UserId(2))
                .thenReturn(Arrays.asList(holding2));

        // Physical transactions for user 1 → 2.00g
        when(physicalGoldTransactionRepository.findByUser_UserId(1))
                .thenReturn(Arrays.asList(txn1));

        // Physical transactions for user 2 → nothing
        when(physicalGoldTransactionRepository.findByUser_UserId(2))
                .thenReturn(Collections.emptyList());

        // ── Call the actual method ──
        List<TopUserHoldingDTO> result =
                topUserService.getTopUsersByGoldHoldings(5);

        // ── Assertions ──

        // Result should not be null
        assertNotNull(result,
                "Result list should not be null");

        // Should have 2 users
        assertEquals(2, result.size(),
                "Should return 2 users");

        // Bhuvan (20g) should be FIRST — highest gold
        assertEquals("Bhuvan Sharma",
                result.get(0).getUserName(),
                "Bhuvan should be ranked 1st with 20g");

        // Bhuvan total = 20.00g
        assertEquals(new BigDecimal("20.00"),
                result.get(0).getTotalGoldQuantity(),
                "Bhuvan total should be 20.00g");

        // Aman should be SECOND
        assertEquals("Aman Gupta",
                result.get(1).getUserName(),
                "Aman should be ranked 2nd");

        // Aman total = 5.25 + 2.00 = 7.25g
        assertEquals(new BigDecimal("7.25"),
                result.get(1).getTotalGoldQuantity(),
                "Aman total should be 7.25g");

        // Verify repos were called
        verify(userRepository, times(1)).findAll();
        verify(virtualGoldHoldingsRepository, times(1))
                .findByUser_UserId(1);
        verify(virtualGoldHoldingsRepository, times(1))
                .findByUser_UserId(2);
    }



    @Test
    @DisplayName("Negative Test: Throws UserNotFoundException when no users in DB")
    public void testGetTopUsers_NoUsersInDatabase_ThrowsUserNotFoundException() {

        // ── Tell Mockito to return EMPTY list ──
        // This simulates a completely empty users table in DB
        when(userRepository.findAll())
                .thenReturn(Collections.emptyList());

        // ── assertThrows checks that the method DOES throw this exception ──
        // Your TopUserService.java line 39 does:
        //   if (allUsers.isEmpty()) {
        //       throw new UserNotFoundException("No users found. ");
        //   }
        // So this test EXPECTS that exact exception to be thrown
        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> topUserService.getTopUsersByGoldHoldings(5),
                "UserNotFoundException must be thrown when DB has no users"
        );

        // ── Check the exception message is not empty ──
        assertNotNull(exception.getMessage(),
                "Exception message should not be null");

        // ── Check message contains expected text ──
        assertTrue(exception.getMessage().contains("No users found"),
                "Exception message should say 'No users found'");

        // ── Verify findAll was called once ──
        verify(userRepository, times(1)).findAll();

        // ── Verify virtual/physical repos were NEVER called ──
        // Because service throws exception BEFORE the for loop
        verify(virtualGoldHoldingsRepository, never())
                .findByUser_UserId(any());
        verify(physicalGoldTransactionRepository, never())
                .findByUser_UserId(any());
    }
}



