package com.example.springapp.exception;

public class UnAuthorizedException extends RuntimeException{

    public UnAuthorizedException(String exception){
        super(exception);
    }
}
