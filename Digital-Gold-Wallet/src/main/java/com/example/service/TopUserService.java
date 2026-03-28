package com.example.service;

import com.example.dao.IPhysicalGoldTransactionRepository;
import com.example.dao.IUserRepository;
import com.example.dao.IVirtualGoldHoldingsRepository;
import com.example.dto.TopUserHoldingDTO;
import com.example.entity.PhysicalGoldTransaction;
import com.example.entity.User;
import com.example.entity.VirtualGoldHoldings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// @Service tells Spring "this class contains business logic"
@Service
public class TopUserService implements ITopUserService {


    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IVirtualGoldHoldingsRepository virtualGoldHoldingsRepository;

    @Autowired
    private IPhysicalGoldTransactionRepository physicalGoldTransactionRepository;

    @Override
    public List<TopUserHoldingDTO> getTopUsersByGoldHoldings(Integer limit) {


        List<User> allUsers = userRepository.findAll();


        List<TopUserHoldingDTO> resultList = new ArrayList<>();

        // Step 3: Loop through every user and calculate their gold totals
        for (User user : allUsers) {


            List<VirtualGoldHoldings> virtualList =
                    virtualGoldHoldingsRepository.findByUser_UserId(user.getUserId());

            BigDecimal virtualTotal = BigDecimal.ZERO; // start at 0
            for (VirtualGoldHoldings holding : virtualList) {
                virtualTotal = virtualTotal.add(holding.getQuantity());
            }
            List<PhysicalGoldTransaction> physicalList =
                    physicalGoldTransactionRepository.findByUser_UserId(user.getUserId());

            BigDecimal physicalTotal = BigDecimal.ZERO; // start at 0
            for (PhysicalGoldTransaction txn : physicalList) {
                physicalTotal = physicalTotal.add(txn.getQuantity());
            }


            BigDecimal combinedTotal = virtualTotal.add(physicalTotal);

            TopUserHoldingDTO dto = new TopUserHoldingDTO();
            dto.setUserId(user.getUserId());
            dto.setUserName(user.getName());
            dto.setUserEmail(user.getEmail());
            dto.setVirtualGoldQuantity(virtualTotal);
            dto.setPhysicalGoldQuantity(physicalTotal);
            dto.setTotalGoldQuantity(combinedTotal);

            resultList.add(dto);
        }


        resultList.sort(Comparator.comparing(TopUserHoldingDTO::getTotalGoldQuantity).reversed());


        int actualLimit = Math.min(limit, resultList.size());
        return resultList.subList(0, actualLimit);
    }
}