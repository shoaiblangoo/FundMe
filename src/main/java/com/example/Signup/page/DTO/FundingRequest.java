package com.example.Signup.page.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class FundingRequest {
    private Integer userId;
    private Long projectId;
    private BigDecimal amount;
}