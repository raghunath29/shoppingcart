package com.demo.shopping.cart.request;

import java.math.BigDecimal;
import java.util.Set;

import com.demo.shopping.cart.entity.Product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductRequest {
	private String id;
	private String description;
	private String category;
	private BigDecimal price;
}
