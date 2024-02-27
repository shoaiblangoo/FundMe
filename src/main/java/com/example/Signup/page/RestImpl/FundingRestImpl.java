package com.example.Signup.page.RestImpl;

import com.example.Signup.page.DTO.FundingRequest;
import com.example.Signup.page.Rest.FundingRest;
import com.example.Signup.page.Service.FundingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
@CrossOrigin


@RestController
public class FundingRestImpl implements FundingRest {

    @Autowired
    private FundingService fundingService;

    @Override
    public ResponseEntity<String> fundProject(@RequestBody FundingRequest request) {
        return fundingService.fundProject(request.getUserId(), request.getProjectId(), request.getAmount());
    }

    @Override
    public BigDecimal getTotalFundedAmount(@PathVariable Integer userId) {
        return fundingService.getTotalFundedAmount(userId);
    }



}