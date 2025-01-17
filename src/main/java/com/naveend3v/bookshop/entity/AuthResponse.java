package com.naveend3v.bookshop.entity;

public class AuthResponse {
    private String JwtToken;
    private int status;
    private long timestamp;

    // Constructor
    public AuthResponse(String message, int status, long timestamp) {
        this.JwtToken = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getJwtToken() {
        return JwtToken;
    }

    public void setJwtToken(String jwtToken) {
        JwtToken = jwtToken;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
