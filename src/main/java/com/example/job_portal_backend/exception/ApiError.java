package com.example.job_portal_backend.exception;

import java.util.Date;

public class ApiError {
    private String message;
    private Date timestamp;

    public ApiError(String message) {
        this.message = message;
        this.timestamp = new Date();
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
