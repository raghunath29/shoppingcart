package com.demo.shopping.cart;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.demo.shopping.cart.controller.CartController;
import com.demo.shopping.cart.controller.ProductController;
import com.demo.shopping.cart.entity.Product;
import com.demo.shopping.cart.entity.ShoppingCart;
import com.demo.shopping.cart.request.ProductRequest;
import com.demo.shopping.cart.request.ShoppingCartRequest;
import com.demo.shopping.cart.response.ShopingCartResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
public class IntegrationTests {
	@Autowired
	CartController cartController;
	
	@Autowired
	ProductController productController;
	
	@PersistenceContext
	EntityManager entityManager;
	
	

	@Test
    public void testCreateReadDeleteCart() {
		
		
		ShoppingCartRequest response = new ShoppingCartRequest();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		
		Product pro = new Product();
		pro.setCategory("loan");
		pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);
		
		String status = cartController.createShopingCart("en", "abc", response);
		 List<ShopingCartResponse> carts = cartController.getAllCarts("en" ,"abc");
		 Assertions.assertThat(carts.get(carts.size()-1).getCountrycode().equalsIgnoreCase("JO")); Assertions.assertThat(carts.get(carts.size()-1).getCountrycode().equalsIgnoreCase("JO"));
		 Assertions.assertThat(carts.get(carts.size()-1).getCurrency().equalsIgnoreCase("JOD"));
		
		
		 ShopingCartResponse cart = cartController.getShoppingCart("en", carts.get(0).getId(), "abc");
		 Assertions.assertThat(cart.getCountrycode().equalsIgnoreCase("JO")); Assertions.assertThat(carts.get(carts.size()-1).getCountrycode().equalsIgnoreCase("JO"));
		 Assertions.assertThat(cart.getCurrency().equalsIgnoreCase("JOD"));
    }
	
	
}
