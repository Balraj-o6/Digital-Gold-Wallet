package com.example.demo;

import com.example.dao.IPhysicalGoldTransactionRepository;
import com.example.dao.IUserRepository;
import com.example.dao.IVirtualGoldHoldingsRepository;
import com.example.dto.UserDTO;
import com.example.dto.UserPortfolioDTO;
import com.example.entity.Address;
import com.example.entity.PhysicalGoldTransaction;
import com.example.entity.User;
import com.example.entity.Vendor;
import com.example.entity.VendorBranch;
import com.example.entity.VirtualGoldHoldings;
import com.example.exception.UserNotFoundException;
import com.example.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for UserService.
 *
 * This class tests the service layer in complete isolation — all repository
 * dependencies are replaced with Mockito mocks so no real database is required.
 *
 * @author Team Digital-Gold-Wallet
 * @version 1.0
 * 
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("UserService — Unit Tests")
@Tag("unit")
@SpringBootTest
class UserServiceTest1 {

	/** Mock for all user persistence operations. */
	@Mock
	private IUserRepository userRepository;

	/** Mock for fetching virtual gold holdings per user. */
	@Mock
	private IVirtualGoldHoldingsRepository virtualGoldHoldingsRepository;

	/** Mock for fetching physical gold transactions per user. */
	@Mock
	private IPhysicalGoldTransactionRepository physicalGoldTransactionRepository;

	/**
	 * The real UserService instance with mocks injected by Mockito. No Spring
	 * context is loaded; this keeps tests fast and focused.
	 */
	@InjectMocks
	private UserService userService;

	private static final int VALID_USER_ID = 1;
	private static final int INVALID_USER_ID = 999;
	private static final String VALID_EMAIL = "arjun@example.com";
	private static final String INVALID_EMAIL = "ghost@nowhere.com";
	private static final String USER_NAME = "Arjun Sharma";
	private static final BigDecimal USER_BALANCE = new BigDecimal("50000.00");
	private static final BigDecimal VIRTUAL_GOLD_QTY = new BigDecimal("10.50");
	private static final BigDecimal PHYSICAL_GOLD_QTY = new BigDecimal("5.25");

	/** Pre-computed expected total: {@value} = 10.50 + 5.25 */
	private static final BigDecimal EXPECTED_TOTAL_GOLD = VIRTUAL_GOLD_QTY.add(PHYSICAL_GOLD_QTY);

	/** Represents a user's saved address. */
	private Address mockAddress;

	/** Represents a gold vendor. */
	private Vendor mockVendor;

	/** Represents one branch of a vendor (holds physical gold stock). */
	private VendorBranch mockVendorBranch;

	/** Represents the authenticated user being tested. */
	private User mockUser;

	/** Represents one virtual gold holding owned by the user. */
	private VirtualGoldHoldings mockVirtualHolding;

	/** Represents one physical gold redemption transaction by the user. */
	private PhysicalGoldTransaction mockPhysicalTransaction;

	/**
	 * Builds a clean set of entity objects before every test method.
	 *
	 * Constructing fresh objects each time prevents one test from accidentally
	 * polluting the state seen by the next test.
	 */
	@BeforeEach
	void setUpTestFixtures() {
		mockAddress = buildAddress();
		mockVendor = buildVendor();
		mockVendorBranch = buildVendorBranch(mockVendor, mockAddress);
		mockUser = buildUser(mockAddress);
		mockVirtualHolding = buildVirtualHolding(mockUser, mockVendorBranch);
		mockPhysicalTransaction = buildPhysicalTransaction(mockUser, mockVendorBranch, mockAddress);
	}

	/**
	 * Groups all tests that target the getUserPortfolio method.
	 *
	 * Each nested test clearly describes the scenario being exercised, making
	 * failure reports easier to read in CI output.
	 */

	@Test
	@DisplayName("Should return a fully populated UserPortfolioDTO when the user exists")
	void getUserPortfolio_ShouldReturnPortfolioDTO_WhenUserExists() {

		when(userRepository.findById(VALID_USER_ID)).thenReturn(Optional.of(mockUser));

		when(virtualGoldHoldingsRepository.findByUser_UserId(VALID_USER_ID)).thenReturn(List.of(mockVirtualHolding));

		when(physicalGoldTransactionRepository.findByUser_UserId(VALID_USER_ID))
				.thenReturn(List.of(mockPhysicalTransaction));

		UserPortfolioDTO result = userService.getUserPortfolio(VALID_USER_ID);

		assertNotNull(result, "Returned portfolio DTO must not be null");

		assertEquals(VALID_USER_ID, result.getUserId(), "User ID should match the requested ID");
		assertEquals(USER_NAME, result.getUserName(), "User name should be mapped from the entity");
		assertEquals(VALID_EMAIL, result.getUserEmail(), "User email should be mapped from the entity");
		assertEquals(USER_BALANCE, result.getBalance(), "Account balance should be carried over unchanged");

		assertNotNull(result.getAddress(), "Address DTO must be present in the portfolio");
		assertEquals("Mumbai", result.getAddress().getCity(),
				"City should be mapped correctly from the Address entity");
		assertEquals("400001", result.getAddress().getPostalCode(),
				"Postal code should be mapped correctly from the Address entity");

		assertEquals(1, result.getVirtualHoldings().size(),
				"Portfolio should contain exactly one virtual gold holding");
		assertEquals(1, result.getPhysicalTransactions().size(),
				"Portfolio should contain exactly one physical transaction");

		assertEquals(VIRTUAL_GOLD_QTY, result.getTotalVirtualGold(),
				"Total virtual gold should equal the sum of all virtual holdings");
		assertEquals(PHYSICAL_GOLD_QTY, result.getTotalPhysicalGold(),
				"Total physical gold should equal the sum of all physical transactions");
		assertEquals(EXPECTED_TOTAL_GOLD, result.getTotalGold(),
				"Grand total must be virtualTotal + physicalTotal (10.50 + 5.25 = 15.75)");

		verify(userRepository, times(1)).findById(VALID_USER_ID);
		verify(virtualGoldHoldingsRepository, times(1)).findByUser_UserId(VALID_USER_ID);
		verify(physicalGoldTransactionRepository, times(1)).findByUser_UserId(VALID_USER_ID);
	}

	@Test
	@DisplayName("Should throw UserNotFoundException when the userId does not exist")
	void getUserPortfolio_ShouldThrowUserNotFoundException_WhenUserDoesNotExist() {

		when(userRepository.findById(INVALID_USER_ID)).thenReturn(Optional.empty());

		UserNotFoundException thrownException = assertThrows(UserNotFoundException.class,
				() -> userService.getUserPortfolio(INVALID_USER_ID),
				"A UserNotFoundException must be raised for an unknown user ID");

		assertTrue(thrownException.getMessage().contains(String.valueOf(INVALID_USER_ID)),
				"Exception message must include the unrecognised user ID so callers "
						+ "can diagnose the problem without reading source code");

		verify(userRepository, times(1)).findById(INVALID_USER_ID);
		verify(virtualGoldHoldingsRepository, never()).findByUser_UserId(anyInt());
		verify(physicalGoldTransactionRepository, never()).findByUser_UserId(anyInt());
	}

	@Test
	@DisplayName("Should return a correctly mapped UserDTO when the email exists")
	void getUserByEmail_ShouldReturnUserDTO_WhenEmailExists() {

		when(userRepository.findByEmail(VALID_EMAIL)).thenReturn(Optional.of(mockUser));

		UserDTO result = userService.getUserByEmail(VALID_EMAIL);

		assertNotNull(result, "Returned UserDTO must not be null");

		assertEquals(VALID_USER_ID, result.getUserId(), "User ID should be correctly mapped from the entity");
		assertEquals(USER_NAME, result.getName(), "Name should be correctly mapped from the entity");
		assertEquals(VALID_EMAIL, result.getEmail(), "Email should match what was passed to the query");
		assertEquals(USER_BALANCE, result.getBalance(), "Balance should be carried over from the entity unchanged");

		assertNotNull(result.getAddress(), "UserDTO must carry a mapped AddressDTO");
		assertEquals("123 Gold Street", result.getAddress().getStreet(),
				"Street should be mapped from the Address entity");
		assertEquals("Maharashtra", result.getAddress().getState(), "State should be mapped from the Address entity");
		assertEquals("India", result.getAddress().getCountry(), "Country should be mapped from the Address entity");
		assertEquals("400001", result.getAddress().getPostalCode(),
				"Postal code should be mapped from the Address entity");

		verify(userRepository, times(1)).findByEmail(VALID_EMAIL);
	}

	@Test
	@DisplayName("Should throw UserNotFoundException when the email does not exist")
	void getUserByEmail_ShouldThrowUserNotFoundException_WhenEmailDoesNotExist() {

		when(userRepository.findByEmail(INVALID_EMAIL)).thenReturn(Optional.empty());

		UserNotFoundException thrownException = assertThrows(UserNotFoundException.class,
				() -> userService.getUserByEmail(INVALID_EMAIL),
				"A UserNotFoundException must be raised for an unregistered email");

		assertTrue(thrownException.getMessage().contains(INVALID_EMAIL),
				"Exception message must include the unrecognised email address so "
						+ "callers can identify exactly which lookup failed");

		verify(userRepository, times(1)).findByEmail(INVALID_EMAIL);
	}

	/**
	 * Builds a fully populated Address entity for use in tests.
	 *
	 * return a non-null Address with all fields set
	 */
	private Address buildAddress() {
		Address address = new Address();
		address.setAddressId(1);
		address.setStreet("123 Gold Street");
		address.setCity("Mumbai");
		address.setState("Maharashtra");
		address.setPostalCode("400001");
		address.setCountry("India");
		return address;
	}

	/**
	 * Builds a Vendor entity representing a gold vendor.
	 *
	 * return a non-null Vendor with ID, name, and pricing set
	 */
	private Vendor buildVendor() {
		Vendor vendor = new Vendor();
		vendor.setVendorId(10);
		vendor.setVendorName("GoldCraft India");
		vendor.setCurrentGoldPrice(new BigDecimal("13460.00"));
		vendor.setTotalGoldQuantity(new BigDecimal("1000.00"));
		return vendor;
	}

	/**
	 * Builds a VendorBranch entity linked to the given vendor and address.
	 *
	 * param vendor the parent vendor param address the branch's physical location
	 * return a non-null VendorBranch
	 */
	private VendorBranch buildVendorBranch(Vendor vendor, Address address) {
		VendorBranch branch = new VendorBranch();
		branch.setBranchId(100);
		branch.setVendor(vendor);
		branch.setAddress(address);
		branch.setQuantity(new BigDecimal("500.00"));
		return branch;
	}

	/**
	 * Builds a User entity using the shared test constants #VALID_USER_ID,
	 * #USER_NAME, #VALID_EMAIL, and #USER_BALANCE.
	 *
	 * param address the user's registered address return a non-null User
	 */
	private User buildUser(Address address) {
		User user = new User();
		user.setUserId(VALID_USER_ID);
		user.setName(USER_NAME);
		user.setEmail(VALID_EMAIL);
		user.setBalance(USER_BALANCE);
		user.setAddress(address);
		user.setCreatedAt(LocalDateTime.of(2024, 1, 15, 10, 0));
		return user;
	}

	/**
	 * Builds a VirtualGoldHoldings entity owned by the given user and associated
	 * with the given branch.
	 *
	 * param user the owning user param branch the vendor branch where gold is held
	 * virtually return a non-null VirtualGoldHoldings with quantity
	 * #VIRTUAL_GOLD_QTY
	 */
	private VirtualGoldHoldings buildVirtualHolding(User user, VendorBranch branch) {
		VirtualGoldHoldings holding = new VirtualGoldHoldings();
		holding.setHoldingId(201);
		holding.setUser(user);
		holding.setVendorBranch(branch);
		holding.setQuantity(VIRTUAL_GOLD_QTY);
		return holding;
	}

	/**
	 * Builds a PhysicalGoldTransaction entity representing a redemption request
	 * placed by the given user, fulfilled from the given branch.
	 *
	 * param user the user who placed the redemption request param branch the branch
	 * fulfilling the request param deliveryAddress the address to which physical
	 * gold is dispatched return a non-null PhysicalGoldTransaction with quantity
	 * #PHYSICAL_GOLD_QTY
	 */
	private PhysicalGoldTransaction buildPhysicalTransaction(User user, VendorBranch branch, Address deliveryAddress) {

		PhysicalGoldTransaction transaction = new PhysicalGoldTransaction();
		transaction.setTransactionId(301);
		transaction.setUser(user);
		transaction.setVendorBranch(branch);
		transaction.setQuantity(PHYSICAL_GOLD_QTY);
		transaction.setDeliveryAddress(deliveryAddress);
		transaction.setCreatedAt(LocalDateTime.of(2024, 2, 20, 14, 30));
		return transaction;
	}
}