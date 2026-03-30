package com.example.service;

import com.example.dto.UserDTO;
import com.example.dto.UserPortfolioDTO;

public interface IUserService {
    
    UserDTO getUserByEmail(String email);
    UserPortfolioDTO getUserPortfolio(Integer userId);
}