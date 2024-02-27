package com.example.Signup.page.DOA;

import com.example.Signup.page.Model.Project;
import com.example.Signup.page.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User,Integer> {
   User findByEmail(@Param("email") String email);
}