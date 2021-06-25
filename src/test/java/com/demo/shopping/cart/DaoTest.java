package com.demo.shopping.cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.demo.shopping.cart.entity.Product;
import com.demo.shopping.cart.entity.ShoppingCart;
import com.demo.shopping.cart.repository.ShopingCartRepository;
import com.google.common.base.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoTest {
	 @Autowired
	 ShopingCartRepository shopRepository;
	 
	  @Test
	  public void testCreateReadDelete() {
		  ShoppingCart response = new ShoppingCart();
			response.setCountrycode("JO");
			response.setCurrency("JOD");
			response.setId("abcdef");
			Product pro = new Product();
			pro.setCategory("loan");
			pro.setCreated(new Date());
			pro.setDescription("loan amount");
			pro.setPrice(new BigDecimal(10.0));
			pro.setUpdated(new Date());
			Set<Product> products = new HashSet<>();
			products.add(pro);
			response.setProducts(products);
			
			

	 
	   
	 
	    Iterable<ShoppingCart> carts = shopRepository.findAll();
	    Assertions.assertThat(carts).extracting(ShoppingCart::getCountrycode).containsOnly("JO");
	    Assertions.assertThat(carts).extracting(ShoppingCart::getCurrency).containsOnly("JOD");
	   // Assertions.assertThat(carts).extracting(ShoppingCart::getId).containsOnly("abcdef");
	    for (ShoppingCart element : shopRepository.findAll()) {
	    	shopRepository.delete(element);
		}
	     
	    Assertions.assertThat(shopRepository.findAll()).isEmpty();
	  }
}
