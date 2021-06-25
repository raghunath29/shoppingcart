package com.demo.shopping.cart;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.shopping.cart.controller.CartController;
import com.demo.shopping.cart.controller.ProductController;
import com.demo.shopping.cart.entity.Product;
import com.demo.shopping.cart.request.ProductRequest;
import com.demo.shopping.cart.request.ShoppingCartRequest;
import com.demo.shopping.cart.response.ProductResponse;
import com.demo.shopping.cart.response.ShopingCartResponse;
import com.demo.shopping.cart.service.ShopingCardService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	 @MockBean
	 ShopingCardService shopingService;
	 
	 @Autowired
	 MockMvc mockMvc;
	 
	 
	 @Test
	    public void testcreateShopingCartProduct() throws Exception {
		 
	    	
	       
	        
	 
	        Mockito.when(shopingService.createProduct(any(),any(),Mockito.any(ProductRequest .class))).thenReturn("SUCCESS");
	 
	        ProductRequest request = new ProductRequest();
	        mockMvc.perform(put("/api/carts/cart-id/products") 
	        		.content(asJsonString(request))
	                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	      
	                .andExpect(status().isOk()) ;
	    }
	 
	 public void testdeleteShopingCartProduct() throws Exception {
		
	    	Mockito.when(shopingService.deleteProduct(any(),any(),any())).thenReturn("SUCCESS");
	 
	        ProductRequest request = new ProductRequest();
	        mockMvc.perform(delete("/api/carts/cart-id/products/product-id"))
	      
	                .andExpect(status().isOk());
	                
	               
	    }
	 
	  @Test
	    public void testgetAllProducts() throws Exception {
	    	
	    	
	    	ProductResponse pro = new ProductResponse();
	    	pro.setCategory("loan");
	    	pro.setCreated(new Date());
	    	pro.setDescription("loan amount");
	    	pro.setPrice(new BigDecimal(10.0));
	    	pro.setUpdated(new Date());
	    	List<ProductResponse> products = new ArrayList<>();
	    	products.add(pro);
	    	
	    	Mockito.when(shopingService.getProductList(any(),any())).thenReturn(products);
	 
	        
	        mockMvc.perform(get("/api/carts/cart-id/products"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$", Matchers.hasSize(1)))
	                .andExpect(jsonPath("$[0].category", Matchers.is("loan")))
	                .andExpect(jsonPath("$[0].description", Matchers.is("loan amount")));
	                
	              
	                
	               
	    }
	  
	  @Test
	    public void testgetProduct() throws Exception {
	    	
	    	
	    	ProductResponse pro = new ProductResponse();
	    	pro.setCategory("loan");
	    	pro.setCreated(new Date());
	    	pro.setDescription("loan amount");
	    	pro.setPrice(new BigDecimal(10.0));
	    	pro.setUpdated(new Date());
	    	
	    	
	    	Mockito.when(shopingService.getProduct(any(),any(),any())).thenReturn(pro);
	 
	        
	        mockMvc.perform(get("/api/carts/cart-id/products/product-id"))
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.category", Matchers.is("loan")))
	                .andExpect(jsonPath("$.description", Matchers.is("loan amount")));
	                 }
	    
	    
	 
	 
	 
	  public static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	  
	  
		 
	 }

