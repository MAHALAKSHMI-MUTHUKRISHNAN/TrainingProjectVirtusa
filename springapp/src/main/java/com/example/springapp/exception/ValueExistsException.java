package com.example.springapp.exception;

public class ValueExistsException extends RuntimeException{

    public ValueExistsException(String exception){
        super(exception);
    }
}
