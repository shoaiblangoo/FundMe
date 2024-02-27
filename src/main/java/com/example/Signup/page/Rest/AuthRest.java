package com.example.Signup.page.Rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/user")
public interface AuthRest {
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody(required = true) Map<String, String> request);
}