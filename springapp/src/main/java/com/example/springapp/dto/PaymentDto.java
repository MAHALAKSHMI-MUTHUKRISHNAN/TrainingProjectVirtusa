package com.example.springapp.dto;

import lombok.Data;

@Data
public class PaymentDto {

    private long bookId;

    private String initialPay;
    private String finalPay;
    private String charges;
}
