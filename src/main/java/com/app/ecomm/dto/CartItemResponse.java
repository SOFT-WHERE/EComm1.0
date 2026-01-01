package com.app.ecomm.dto;

import com.app.ecomm.model.Product;
import com.app.ecomm.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartItemResponse {

    private Long id;
//    private User user;
//    private Product product;
    private Integer quantity;
    private BigDecimal price;
}
