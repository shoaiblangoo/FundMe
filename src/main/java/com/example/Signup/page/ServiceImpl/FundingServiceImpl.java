package com.example.Signup.page.ServiceImpl;


import com.example.Signup.page.Constants.ProjConstants;
import com.example.Signup.page.DOA.FundingDAO;
import com.example.Signup.page.DOA.ProjectDAO;
import com.example.Signup.page.DOA.UserDAO;
import com.example.Signup.page.Model.Funding;
import com.example.Signup.page.Model.Project;
import com.example.Signup.page.Model.User;
import com.example.Signup.page.Service.FundingService;
import com.example.Signup.page.Utils.ProjUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FundingServiceImpl implements FundingService {

    @Autowired
    private FundingDAO fundingDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    public ResponseEntity<String> fundProject(Integer userId, Long projectId, BigDecimal amount) {
        try {
            User user = userDAO.findById(userId).orElse(null);
            Project project = projectDAO.findById(projectId).orElse(null);

            if (user != null && project != null) {
                Funding funding = new Funding();
                funding.setUser(user);
                funding.setProject(project);
                funding.setAmount(amount);

                fundingDAO.save(funding);

                project.setFundingReceived(project.getFundingReceived().add(amount));
                projectDAO.save(project);

                return ProjUtils.getResponseEntity("Funding successful", HttpStatus.OK);
            } else {
                return ProjUtils.getResponseEntity("User or project not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ProjUtils.getResponseEntity(ProjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public BigDecimal getTotalFundedAmount(Integer userId) {
        User user = userDAO.findById(userId).orElse(null);
        if (user != null) {
            return fundingDAO.findByUser(user).stream()
                    .map(Funding::getAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return null;
        }
    }


}