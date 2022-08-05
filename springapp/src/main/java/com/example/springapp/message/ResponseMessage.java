package com.example.springapp.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "error")
public class ResponseMessage {
    private String message;
    private String details;

    public ResponseMessage(String message){
        this.message=message;
    }
}
