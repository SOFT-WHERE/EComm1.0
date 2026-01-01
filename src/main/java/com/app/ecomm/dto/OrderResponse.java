package com.app.ecomm.dto;

import com.app.ecomm.model.OrderStatus;
import com.app.ecomm.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private BigDecimal amount;
    private OrderStatus status;
    private List<OrderItemDto> items;
    private LocalDateTime createdAt;



}
