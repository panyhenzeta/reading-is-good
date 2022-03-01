package com.getir.readingisgood.model.response;

import java.time.Instant;

public class ErrorResponse {
    private int statusCode;
    private String timestamp;
    private String message;
    private String description;

    public ErrorResponse(int statusCode, Instant timestamp, String message, String description) {
        this.statusCode = statusCode;
        this.timestamp = timestamp.toString();
        this.message = message;
        this.description = description;
    }
    public int getStatusCode() {
        return statusCode;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public String getMessage() {
        return message;
    }
    public String getDescription() {
        return description;
    }
}
