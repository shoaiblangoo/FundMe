package com.example.Signup.page.Service;

import com.example.Signup.page.DTO.ProjectDetailsDTO;
import com.example.Signup.page.DTO.UserDTO;
import com.example.Signup.page.Model.Project;
import com.example.Signup.page.Model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    ResponseEntity<String> signUp(Map<String,String> requestMap);

    User findByEmail(String email);

    ResponseEntity<String> createProject(Integer userId, Map<String, String> projectDetails);
    List<Project> getUserProjects(Integer userId);

    UserDTO getUserDetails(Integer userId);

    Project getProjectById(Integer userId, String projectName);

    void updateProject(Project project);
    List<String> getFundedProjects(Integer userId);
    Map<Long, BigDecimal> getFundedAmountPerProject(Integer userId);
    UserDTO convertToDTO(User user);

    List<Project> getAllProjects();

    Project getDetailsByProjectId(Long projectId);
    ResponseEntity<ProjectDetailsDTO> getProjectDetails(Long projectId);
}