package com.example.springapp.exception;

import com.example.springapp.message.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception){
        logger.error("Response Body is missing");
        return new ResponseEntity<>("Response Body is missing",HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> exception(ResourceNotFoundException exception){
        String details = exception.getLocalizedMessage();
        ResponseMessage error = new ResponseMessage("Resource Not Found",details);
        logger.error(details);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = UnAuthorizedException.class)
    public ResponseEntity<Object> exception(UnAuthorizedException exception){
        String details = exception.getLocalizedMessage();
        ResponseMessage error = new ResponseMessage("UnAuthorized Access",details);
        logger.error(details);
        return new ResponseEntity<>(error, HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
    }

    @ExceptionHandler(value = ValueExistsException.class)
    public ResponseEntity<Object> exception(ValueExistsException exception){
        String details = exception.getLocalizedMessage();
        ResponseMessage error = new ResponseMessage("Already Exists",details);
        logger.error(details);
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = DateBookedException.class)
    public ResponseEntity<Object> exception(DateBookedException exception){
        String details = exception.getLocalizedMessage();
        ResponseMessage error = new ResponseMessage("Date exists",details);
        logger.error(details);
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = MailNotSendException.class)
    public ResponseEntity<Object> exception(MailNotSendException exception){
        String details = exception.getLocalizedMessage();
        ResponseMessage error = new ResponseMessage("Mail couldn't be sent",details);
        logger.error(details);
        return new ResponseEntity<>(error, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception){
        logger.error("Method Not Supported");
        return  new ResponseEntity<>("Method Not Supported",HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = NoSuchElementException.class)
    public ResponseEntity<Object> handleNoSuchElementException(NoSuchElementException exception){
        logger.error("No SuchElement ");
        return  new ResponseEntity<>("NoSuchElement",HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException exception){
        logger.error("UsernameNot FoundException");
        return  new ResponseEntity<>("UsernameNotFoundException",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = NullPointerException.class)
    public ResponseEntity<Object> handleNullPointerException(NullPointerException exception){
        logger.error("Null value not accepted");
        return  new ResponseEntity<>("Null value not accepted",HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseMessage> handleMaxSizeException(MaxUploadSizeExceededException exception){
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage("File too large"));
    }



}
