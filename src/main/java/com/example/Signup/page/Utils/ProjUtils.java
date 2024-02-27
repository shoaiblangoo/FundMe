package com.example.Signup.page.Utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ProjUtils {

    private ProjUtils(){

    }

    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){
        return new ResponseEntity<String>("{\"message\":\""+responseMessage +"\"}",httpStatus);
    }
}