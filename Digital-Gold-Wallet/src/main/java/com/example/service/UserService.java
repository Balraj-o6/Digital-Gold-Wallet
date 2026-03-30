package com.example.service;

import com.example.dao.IPhysicalGoldTransactionRepository;
import com.example.dao.IUserRepository;
import com.example.dao.IVirtualGoldHoldingsRepository;
import com.example.dto.PhysicalGoldTransactionDTO;
import com.example.dto.UserDTO;
import com.example.dto.UserPortfolioDTO;
import com.example.dto.VirtualGoldHoldingDTO;
import com.example.entity.PhysicalGoldTransaction;
import com.example.entity.User;
import com.example.entity.VirtualGoldHoldings;
import com.example.exception.UserNotFoundException;
import com.example.mapper.AddressMapper;
import com.example.mapper.PhysicalGoldTransactionMapper;
import com.example.mapper.UserMapper;
import com.example.mapper.VirtualGoldHoldingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IVirtualGoldHoldingsRepository virtualGoldHoldingsRepository;

    @Autowired
    private IPhysicalGoldTransactionRepository physicalGoldTransactionRepository;

   

    @Override
    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
        return UserMapper.convertEntityToDTO(user);
    }

    @Override
    public UserPortfolioDTO getUserPortfolio(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));

        List<VirtualGoldHoldings> virtualList =
                virtualGoldHoldingsRepository.findByUser_UserId(userId);

        List<PhysicalGoldTransaction> physicalList =
                physicalGoldTransactionRepository.findByUser_UserId(userId);

        List<VirtualGoldHoldingDTO> virtualDTOs = virtualList.stream()
                .map(VirtualGoldHoldingMapper::convertEntityToObject)
                .collect(Collectors.toList());

        List<PhysicalGoldTransactionDTO> physicalDTOs = physicalList.stream()
                .map(PhysicalGoldTransactionMapper::convertEntityToDTO)
                .collect(Collectors.toList());

        BigDecimal totalVirtual = virtualList.stream()
                .map(VirtualGoldHoldings::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalPhysical = physicalList.stream()
                .map(PhysicalGoldTransaction::getQuantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        UserPortfolioDTO portfolio = new UserPortfolioDTO();
        portfolio.setUserId(user.getUserId());
        portfolio.setUserName(user.getName());
        portfolio.setUserEmail(user.getEmail());
        portfolio.setBalance(user.getBalance());
        portfolio.setAddress(AddressMapper.convertEntityToObject(user.getAddress()));
        portfolio.setVirtualHoldings(virtualDTOs);
        portfolio.setPhysicalTransactions(physicalDTOs);
        portfolio.setTotalVirtualGold(totalVirtual);
        portfolio.setTotalPhysicalGold(totalPhysical);
        portfolio.setTotalGold(totalVirtual.add(totalPhysical));

        return portfolio;
    }
}