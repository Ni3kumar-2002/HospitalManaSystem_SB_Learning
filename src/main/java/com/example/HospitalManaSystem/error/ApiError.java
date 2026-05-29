package com.example.HospitalManaSystem.error;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;

    public ApiError(){
        this.timeStamp = LocalDateTime.now();
    }

    public ApiError(String Error, HttpStatus statusCode){
        this();
        this.error = Error;
        this.statusCode = statusCode;
    }
}
