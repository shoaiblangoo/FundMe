package com.example.Signup.page.RestImpl;

import com.example.Signup.page.Constants.ProjConstants;
import com.example.Signup.page.DTO.UserDTO;
import com.example.Signup.page.Model.User;
import com.example.Signup.page.Rest.AuthRest;
import com.example.Signup.page.Service.UserService;
import com.example.Signup.page.Utils.ProjUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.HashMap;
import java.util.Map;
@CrossOrigin
@RestController
public class AuthRestImpl implements AuthRest {

    @Autowired
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ResponseEntity <?> login(@RequestBody(required = true) Map<String, String> requestMap) {
        try {
            if (requestMap.containsKey("email") && requestMap.containsKey("password")) {
                User user = userService.findByEmail(requestMap.get("email"));

                if (user != null && user.getPassword().equals(requestMap.get("password"))) {
                    String accessToken = generateAccessToken(user);
                    UserDTO userDTO = userService.convertToDTO(user);
                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Login successful");
                    response.put("accessToken", accessToken);
                    response.put("user", userDTO);
                    response.put("userId", user.getId());
                    return ResponseEntity.ok(response);
                    //return ProjUtils.getResponseEntity("Login successful", HttpStatus.OK);
                } else {
                    return ProjUtils.getResponseEntity("Invalid email or password", HttpStatus.UNAUTHORIZED);
                }
            } else {
                return ProjUtils.getResponseEntity(ProjConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ProjUtils.getResponseEntity(ProjConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    private String generateAccessToken(User user) {
        byte[] keyBytes = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();

        return Jwts.builder()
                .setSubject(user.getEmail())
                .signWith(Keys.hmacShaKeyFor(keyBytes), SignatureAlgorithm.HS256)
                .compact();
    }
}