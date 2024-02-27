package com.example.Signup.page.DTO;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class ProjectDetailsDTO {
    private String projectName;
    private String description;
    private String businessPlan;
    private BigDecimal totalFundingAmount;
    private BigDecimal fundingReceived;
    private UserDTO user;
}
