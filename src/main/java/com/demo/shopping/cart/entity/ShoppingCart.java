package com.demo.shopping.cart.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
// shopping cart entity
@Entity
@Table(name = "cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShoppingCart {
	@Id
	private String id;
	private String countrycode;
	private String currency;
	
	@ElementCollection
	
	private Set<Product> products;
	@Temporal(TemporalType.TIME)
	private Date created;
	@Temporal(TemporalType.TIME)
	private Date updated;
	

}
