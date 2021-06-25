package com.demo.shopping.cart.request;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.demo.shopping.cart.entity.Product;
import com.demo.shopping.cart.response.ShopingCartResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCartRequest {
	private String countrycode;
	private String currency;
	private Set<Product> products;
	
}
