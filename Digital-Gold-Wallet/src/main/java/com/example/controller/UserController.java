package com.example.controller;

import com.example.dto.TopUserHoldingDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserPortfolioDTO;
import com.example.service.ITopUserService;
import com.example.service.IUserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

	private final ITopUserService topUserService;
	private final IUserService userService;

	public UserController(ITopUserService topService, IUserService userService) {
		this.topUserService = topService;
		this.userService = userService;
	}

	@GetMapping("/top-holdings")
	public ResponseEntity<List<TopUserHoldingDTO>> getTopUsersByHoldings(
			@RequestParam(defaultValue = "5") Integer limit) {

		List<TopUserHoldingDTO> topUsers = topUserService.getTopUsersByGoldHoldings(limit);
		return new ResponseEntity<>(topUsers, HttpStatus.OK);
	}

	// done by balraj.
	@GetMapping("/{id}/portfolio")
	public ResponseEntity<UserPortfolioDTO> getUserPortfolio(@PathVariable Integer id) {
		UserPortfolioDTO portfolio = userService.getUserPortfolio(id);
		return new ResponseEntity<>(portfolio, HttpStatus.OK);
	}

	@GetMapping("/email")
	public ResponseEntity<UserDTO> getUserByEmail(@RequestParam String email) {
		UserDTO user = userService.getUserByEmail(email);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}

}