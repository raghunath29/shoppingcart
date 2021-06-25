package com.demo.shopping.cart.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
// product entity
import lombok.Setter;

@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Embeddable 
public class Product {
	
	private String id;
	private String description;
	private String category;
	private BigDecimal price;
	@Temporal(TemporalType.TIME)
	private Date created;
	@Temporal(TemporalType.TIME)
	private Date updated;
	
	 @Override
	  public boolean equals(Object obj) {
	    if(obj instanceof Product){
	    	Product temp = (Product)obj;
	       return this.id.equals(temp.getId());
	    }
	    return true;
	  }

	  @Override
	  public int hashCode() {
	    int hash = 22;
	    hash = 32 * hash;
	    return hash;
	  }

	


}
