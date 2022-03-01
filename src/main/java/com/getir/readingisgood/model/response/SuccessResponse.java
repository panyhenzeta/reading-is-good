package com.getir.readingisgood.model.response;

import java.util.Date;

public class SuccessResponse<T> {
    private int statusCode = 200;
    private T content;
    private String timestamp;

    public SuccessResponse(T content) {
        this.content = content;
        this.timestamp = new Date().toString();
    }

    public SuccessResponse(T content, Date timestamp) {
        this.content = content;
        this.timestamp = timestamp.toString();
    }

    public SuccessResponse(int statusCode, T content, Date timestamp) {
        this.statusCode = statusCode;
        this.content = content;
        this.timestamp = timestamp.toString();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
