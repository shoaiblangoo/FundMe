package com.example.Signup.page.RestImpl;

import com.example.Signup.page.Constants.ProjConstants;
import com.example.Signup.page.DTO.ProjectDetailsDTO;
import com.example.Signup.page.DTO.UserDTO;
import com.example.Signup.page.Model.Project;
import com.example.Signup.page.Rest.UserRest;
import com.example.Signup.page.Service.UserService;
import com.example.Signup.page.Utils.ProjUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return ProjUtils.getResponseEntity(ProjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> createProject(@PathVariable Integer userId, @RequestBody Map<String, String> projectDetails) {
        ResponseEntity<String> response = userService.createProject(userId, projectDetails);
        if (response.getStatusCode() == HttpStatus.OK) {
            Project project = userService.getProjectById(userId, projectDetails.get("projectName"));
            if (project != null) {
                project.setFundingReceived(BigDecimal.ZERO);
                userService.updateProject(project);
            }
        }

        return response;

    }

    @Override
    public ResponseEntity<List<Project>> getUserProjects(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getUserProjects(userId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDTO> getUserDetails(@PathVariable Integer userId) {
        UserDTO userDTO = userService.getUserDetails(userId);

        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BigDecimal> getProjectFunding(@PathVariable Integer userId, @PathVariable String projectName) {
        Project project = userService.getProjectById(userId, projectName);

        if (project != null) {
            BigDecimal fundingReceived = project.getFundingReceived();
            return new ResponseEntity<>(fundingReceived, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<String> getFundedProjects(@PathVariable Integer userId) {
        return userService.getFundedProjects(userId);
    }

    @Override
    public Map<Long, BigDecimal> getFundedAmountPerProject(@PathVariable Integer userId) {
        return userService.getFundedAmountPerProject(userId);
    }

    @Override
    public ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = userService.getAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProjectDetailsDTO> getProjectDetails(@PathVariable Long projectId) {
        return userService.getProjectDetails(projectId);
    }
}