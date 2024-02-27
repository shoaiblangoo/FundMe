package com.example.Signup.page.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "project")
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "business_plan", length = 3000)
    private String businessPlan;

    @Column(name = "total_funding_amount")
    private BigDecimal totalFundingAmount;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Funding> fundings = new ArrayList<>();

    @Column(name = "funding_received")
    private BigDecimal fundingReceived = BigDecimal.ZERO;
}