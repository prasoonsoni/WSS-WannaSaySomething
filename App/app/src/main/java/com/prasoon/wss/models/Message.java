package com.prasoon.wss.models;

public class Message {
    String message, createdAt;

    public Message(String message, String createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
