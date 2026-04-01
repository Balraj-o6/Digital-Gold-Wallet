package com.example.service;

import com.example.dto.TopUserHoldingDTO;
import java.util.List;

// Interface
// It declares what the service must do, but not HOW it does it
public interface ITopUserService {

	List<TopUserHoldingDTO> getTopUsersByGoldHoldings(Integer limit);
}