package com.naveend3v.bookshop.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;

@RestControllerAdvice
public class CustomExceptionHandler {

    public ProblemDetail errorDetail = null;

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex){

        if(ex instanceof BadCredentialsException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(401),ex.getMessage());
            errorDetail.setProperty("Reason","Authentication failure");
        }

        if(ex instanceof AccessDeniedException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("Reason","Not Authorized");
        }

        if(ex instanceof SignatureException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("Reason","Jwt Signature not valid");
        }

        if(ex instanceof ExpiredJwtException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
            errorDetail.setProperty("Reason","Jwt tok already expired!");
        }

        if(ex instanceof CustomException){
            errorDetail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(403),ex.getMessage());
        }
        return errorDetail;
    }



}
