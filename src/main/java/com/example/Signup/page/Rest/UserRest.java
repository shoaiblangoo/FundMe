package com.example.Signup.page.Rest;

import com.example.Signup.page.DTO.ProjectDetailsDTO;
import com.example.Signup.page.DTO.UserDTO;
import com.example.Signup.page.Model.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RequestMapping ("/user")
public interface UserRest {
    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody(required = true) Map<String,String> request);

    @PostMapping("/createProject/{userId}")
    ResponseEntity<String> createProject(@PathVariable Integer userId, @RequestBody Map<String, String> projectDetails);

    @GetMapping("/getUserProjects/{userId}")
    ResponseEntity<List<Project>> getUserProjects(@PathVariable Integer userId);
    @GetMapping("/getProjectDetails/{projectId}")
    ResponseEntity<ProjectDetailsDTO> getProjectDetails(@PathVariable Long projectId);

    @GetMapping("/getUserDetails/{userId}")
    ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer userId);

    @GetMapping("/getProjectFunding/{userId}/{projectName}")
    ResponseEntity<BigDecimal> getProjectFunding(@PathVariable Integer userId, @PathVariable String projectName);

    @GetMapping("/fundedProjects/{userId}")
    List<String> getFundedProjects(@PathVariable Integer userId);

    @GetMapping("/fundedAmountPerProject/{userId}")
    Map<Long, BigDecimal> getFundedAmountPerProject(@PathVariable Integer userId);

    @GetMapping("/getAllProjects")
    ResponseEntity<List<Project>> getAllProjects();
}