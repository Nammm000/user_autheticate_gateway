package com.javatechie.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class OrderResponseDTO {

    private String orderId;
    private String name;
    private int qty;
    private double price;
    private Date orderDate;
    private String status;
    private int estimateDeliveryWindow;

    public OrderResponseDTO(String orderId, String name, int qty, double price, Date orderDate, String status, int estimateDeliveryWindow) {
        this.orderId = orderId;
        this.name = name;
        this.qty = qty;
        this.price = price;
        this.orderDate = orderDate;
        this.status = status;
        this.estimateDeliveryWindow = estimateDeliveryWindow;
    }
}
