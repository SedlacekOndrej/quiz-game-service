package com.sedlacek.quiz.models;

public class ErrorMessage {
    private String message;
    public static boolean isError;

    public ErrorMessage(String message) {
        this.message = message;
    }

    public ErrorMessage() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
