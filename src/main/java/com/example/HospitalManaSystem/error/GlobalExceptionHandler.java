package com.example.HospitalManaSystem.error;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


//This GlobalExceptionHandler only handle exception that occurs in Spring MVC
//then ques arries
//How to handle Exception which occurs in Spring servlet.
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiError> handleUsernameNotFoundException(UsernameNotFoundException ex){
        ApiError apiError = new ApiError("Username not found with username: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(apiError, apiError.getStatusCode());
    }

    //This will not work because this exception comes in Servlet and
    //GlobalExceptionHandler doesn't handle servlet exception
    //it will execute when we use HandlerExceptionResolve in our DofilterInternal method by try-catch block
//    catch (Exception ex) {
//
//        resolver.resolveException(   //this method invoke GlobalExceptionHandler
//                request,
//                response,
//                null,
//                ex
//        );
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            AuthenticationException ex) {

        ApiError apiError = new ApiError(
                "Authentication failed: " + ex.getMessage(),
                HttpStatus.UNAUTHORIZED
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJwtException(
            JwtException ex) {

        ApiError apiError = new ApiError(
                "Invalid JWT token: " + ex.getMessage(),
                HttpStatus.UNAUTHORIZED
        );

        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(
            AccessDeniedException ex) {

        ApiError apiError = new ApiError(
                "Access denied! Insufficient permissions",
                HttpStatus.FORBIDDEN
        );

        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex) {

        ApiError apiError = new ApiError(
                "An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
