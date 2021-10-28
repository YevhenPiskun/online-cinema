package com.example.cinema.onlinecinema.utils;

public class ErrorMessage {

    private int statusCode;
    private String statusName;
    private String message;

    public ErrorMessage(int statusCode, String status, String message) {
        this.statusCode = statusCode;
        this.statusName = status;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatus() {
        return statusName;
    }

    public void setStatus(String status) {
        this.statusName = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}