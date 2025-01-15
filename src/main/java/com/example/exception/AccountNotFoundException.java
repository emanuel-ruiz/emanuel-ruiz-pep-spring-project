
/**
 * Code Written by: Emanuel Ruiz
 */
package com.example.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String message){
        super(message);
    }
}
