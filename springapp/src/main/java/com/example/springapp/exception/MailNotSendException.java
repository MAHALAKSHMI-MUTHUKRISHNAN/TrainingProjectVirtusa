package com.example.springapp.exception;

public class MailNotSendException extends RuntimeException{

    public MailNotSendException(String exception){
        super(exception);
    }
}
