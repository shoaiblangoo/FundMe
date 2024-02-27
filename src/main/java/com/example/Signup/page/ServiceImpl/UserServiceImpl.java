package com.example.Signup.page.ServiceImpl;

import com.example.Signup.page.Constants.ProjConstants;
import com.example.Signup.page.DOA.FundingDAO;
import com.example.Signup.page.DOA.ProjectDAO;
import com.example.Signup.page.DOA.UserDAO;
import com.example.Signup.page.DTO.ProjectDetailsDTO;
import com.example.Signup.page.DTO.UserDTO;
import com.example.Signup.page.Model.Funding;
import com.example.Signup.page.Model.Project;
import com.example.Signup.page.Model.User;
import com.example.Signup.page.Service.UserService;
import com.example.Signup.page.Utils.ProjUtils;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private FundingDAO fundingDAO;

    @Autowired
    private ProjectDAO projectDAO;
    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            if (validateSignUpMap(requestMap)) {

                User user = userDAO.findByEmail(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDAO.save(getUserFromMap(requestMap));
                    return ProjUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);

                } else {
                    return ProjUtils.getResponseEntity("Email Already Exists", HttpStatus.BAD_REQUEST);
                }


            } else {
                return ProjUtils.getResponseEntity(ProjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return ProjUtils.getResponseEntity(ProjConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public ResponseEntity<String> createProject(Integer userId, Map<String, String> projectDetails) {
        try {
            User user = userDAO.findById(userId).orElse(null);
            if (user != null) {
                Project project = new Project();
                project.setUser(user);
                project.setProjectName(projectDetails.get("projectName"));
                project.setDescription(projectDetails.get("description"));
                project.setBusinessPlan(projectDetails.get("businessPlan"));
                project.setTotalFundingAmount(new BigDecimal(projectDetails.get("totalFundingAmount")));
                project.setFundingReceived(BigDecimal.ZERO);

                user.getProjects().add(project);
                userDAO.save(user);
                return ProjUtils.getResponseEntity("Project created successfully", HttpStatus.OK);
            } else {
                return ProjUtils.getResponseEntity("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ProjUtils.getResponseEntity(ProjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    @Transactional
    public List<Project> getUserProjects(Integer userId) {
        List<Project> projects = new ArrayList<>();
        Optional<User> optionalUser = userDAO.findById(userId);
        optionalUser.ifPresent(user -> projects.addAll(user.getProjects()));
        return projects;
    }

    @Override
    @Transactional
    public UserDTO getUserDetails(Integer userId) {
        User user = userDAO.findById(userId).orElse(null);


        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setName(user.getName());
            userDTO.setContactNumber(user.getContactNumber());
            userDTO.setEmail(user.getEmail());

            return userDTO;
        } else {
            return null;
        }
    }

    @Override
    public List<String> getFundedProjects(Integer userId) {
        User user = userDAO.findById(userId).orElse(null);
        if (user != null) {
            List<String> fundedProjectNames = new ArrayList<>();
            for (Funding funding : fundingDAO.findByUser(user)) {
                fundedProjectNames.add(funding.getProject().getProjectName());
            }
            return fundedProjectNames;
        } else {
            return null;
        }
    }

    @Override
    public Map<Long, BigDecimal> getFundedAmountPerProject(Integer userId) {
        User user = userDAO.findById(userId).orElse(null);
        if (user != null) {
            Map<Long, BigDecimal> fundedAmountPerProject = new HashMap<>();
            for (Funding funding : fundingDAO.findByUser(user)) {
                fundedAmountPerProject.merge(
                        funding.getProject().getProjectId(),
                        funding.getAmount(),
                        BigDecimal::add
                );
            }
            return fundedAmountPerProject;
        } else {
            return null;
        }
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){
        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") && requestMap.containsKey("password")){
            return true;
        }
        return false;
    }

    private User getUserFromMap(Map<String,String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;

    }

    @Override
    public Project getProjectById(Integer userId, String projectName) {
        User user = userDAO.findById(userId).orElse(null);
        if (user != null) {
            Optional<Project> optionalProject = user.getProjects().stream()
                    .filter(project -> project.getProjectName().equals(projectName))
                    .findFirst();
            return optionalProject.orElse(null);
        }
        return null;
    }

    @Override
    public Project getDetailsByProjectId(Long projectId) {
        return projectDAO.findById(projectId).orElse(null);
    }

    @Override
    public ResponseEntity<ProjectDetailsDTO> getProjectDetails(Long projectId) {
        Project project = getDetailsByProjectId(projectId);

        if (project != null) {
            Project refreshedProject = projectDAO.findById(project.getProjectId()).orElse(null);
            if (refreshedProject != null) {
                ProjectDetailsDTO projectDetailsDTO = convertToProjectDetailsDTO(refreshedProject);
                return new ResponseEntity<>(projectDetailsDTO, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    private ProjectDetailsDTO convertToProjectDetailsDTO(Project project) {
        ProjectDetailsDTO projectDetailsDTO = new ProjectDetailsDTO();
        projectDetailsDTO.setProjectName(project.getProjectName());
        projectDetailsDTO.setDescription(project.getDescription());
        projectDetailsDTO.setBusinessPlan(project.getBusinessPlan());
        projectDetailsDTO.setTotalFundingAmount(project.getTotalFundingAmount());
        projectDetailsDTO.setFundingReceived(project.getFundingReceived());

        User user = project.getUser();
        if (user != null) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            projectDetailsDTO.setUser(userDTO);
        }
        return projectDetailsDTO;
    }
    @Override
    public void updateProject(Project project) {
        projectDAO.save(project);
    }

    @Override
    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setContactNumber(user.getContactNumber());
        userDTO.setEmail(user.getEmail());
        return userDTO;
    }

    @Override
    public List<Project> getAllProjects() {
        return projectDAO.findAll();
    }
}