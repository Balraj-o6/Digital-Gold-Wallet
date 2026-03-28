package com.example.controller;

import com.example.dto.TopUserHoldingDTO;
import com.example.service.ITopUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ITopUserService topUserService;


    @GetMapping("/top-holdings")
    public ResponseEntity<List<TopUserHoldingDTO>> getTopUsersByHoldings(
            @RequestParam(defaultValue = "5") Integer limit) {

        List<TopUserHoldingDTO> topUsers = topUserService.getTopUsersByGoldHoldings(limit);
        return new ResponseEntity<>(topUsers, HttpStatus.OK);
    }
}