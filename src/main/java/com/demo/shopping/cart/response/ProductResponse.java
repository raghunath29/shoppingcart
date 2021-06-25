package com.demo.shopping.cart.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
public class ProductResponse {
	private String id;
	private String description;
	private String category;
	private BigDecimal price;
	private Date created;
	private Date updated;
}
