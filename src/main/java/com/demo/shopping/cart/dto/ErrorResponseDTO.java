package com.demo.shopping.cart.dto;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import com.demo.shopping.cart.entity.Product;
import com.demo.shopping.cart.entity.ShoppingCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorResponseDTO {
private String message;
private int code;
}
