package com.example.orderservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseOrder {
    private String productId;
    private String qty;
    private Integer unitPrice;
    private Integer totalPrice;

    private Date createdAt;

    private String orderId;
}
