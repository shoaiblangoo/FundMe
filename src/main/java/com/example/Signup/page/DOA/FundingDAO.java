package com.example.Signup.page.DOA;

import com.example.Signup.page.Model.Funding;
import com.example.Signup.page.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FundingDAO extends JpaRepository<Funding, Long> {
    List<Funding> findByUser(User user);
}