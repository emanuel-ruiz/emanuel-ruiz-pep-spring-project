package com.example.exception;

public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException(String message){
        super(message);
    }
}
