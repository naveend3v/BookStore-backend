package com.naveend3v.bookshop.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    public static ResponseEntity<Object> generateResp(Object message, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<Object>(map, status);
    }
}
