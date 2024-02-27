package com.example.Signup.page.Rest;

import com.example.Signup.page.DTO.FundingRequest;
import com.example.Signup.page.Model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/funding")
public interface FundingRest {

    @PostMapping("/fundProject")
    ResponseEntity<String> fundProject(@RequestBody FundingRequest request);

    @GetMapping("/totalFundedAmount/{userId}")
    BigDecimal getTotalFundedAmount(@PathVariable Integer userId);

}