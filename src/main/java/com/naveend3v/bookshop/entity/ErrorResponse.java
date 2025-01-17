package com.naveend3v.bookshop.entity;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse {
    private String errorMessage;
    private int status;
    private long timestamp;

    public ErrorResponse(String errorMessage, int status, long timestamp) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
