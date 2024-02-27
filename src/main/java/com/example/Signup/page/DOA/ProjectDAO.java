package com.example.Signup.page.DOA;

import com.example.Signup.page.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProjectDAO extends JpaRepository<Project,Long> {
    @Query("SELECT p.fundingReceived FROM Project p WHERE p.projectId = :projectId")
    BigDecimal getFundingReceivedForProject(@Param("projectId") Long projectId);
}