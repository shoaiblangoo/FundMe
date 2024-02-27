package com.example.Signup.page.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
public interface FundingService {

    ResponseEntity<String> fundProject(Integer userId, Long projectId, BigDecimal amount);
    BigDecimal getTotalFundedAmount(Integer userId);

}