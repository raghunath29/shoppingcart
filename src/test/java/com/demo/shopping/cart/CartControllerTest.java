package com.demo.shopping.cart;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.shopping.cart.controller.CartController;
import com.demo.shopping.cart.entity.Product;
import com.demo.shopping.cart.request.ShoppingCartRequest;
import com.demo.shopping.cart.response.ShopingCartResponse;
import com.demo.shopping.cart.service.ShopingCardService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CartController.class)
public class CartControllerTest {

	@MockBean
	ShopingCardService shopingService;

	@Autowired
	MockMvc mockMvc;

	@Test
	public void testgetAllCarts() throws Exception {

		ShopingCartResponse response = new ShopingCartResponse();
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

		List<ShopingCartResponse> shop = Arrays.asList(response);
		System.out.println("size" + shop.size());

		Mockito.when(shopingService.getALLCarts(any())).thenReturn(shop);

		mockMvc.perform(get("/api/carts/all")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].products[0].category", Matchers.is("loan")))
				// .andExpect(jsonPath("$[0].products[0].created", Matchers.is(new Date())))
				.andExpect(jsonPath("$[0].products[0].description", Matchers.is("loan amount")))
				// .andExpect(jsonPath("$[0].products[0].price", Matchers.is(new
				// BigDecimal(10.0))))
				// .andExpect(jsonPath("$[0].products[0].updated", Matchers.is(new Date())))
				.andExpect(jsonPath("$[0].countrycode", Matchers.is("JO")))
				.andExpect(jsonPath("$[0].currency", Matchers.is("JOD")))
				.andExpect(jsonPath("$[0].id", Matchers.is("abcdef")))

				.andExpect(jsonPath("$[0].products", Matchers.hasSize(1)));
	}

	@Test
	public void testgetCart() throws Exception {

		ShopingCartResponse response = new ShopingCartResponse();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		response.setId("abcdef");
		Product pro = new Product();
		pro.setCategory("loan");
		// pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		// pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);

		Mockito.when(shopingService.getCart(any(), any())).thenReturn(response);

		mockMvc.perform(get("/api/carts/cart-id")).andExpect(status().isOk())

				.andExpect(jsonPath("$.products[0].category", Matchers.is("loan")))
				// .andExpect(jsonPath("$.products[0].created", Matchers.is(new Date())))
				.andExpect(jsonPath("$.products[0].description", Matchers.is("loan amount")))
				// .andExpect(jsonPath("$.products[0].price", Matchers.is(new
				// BigDecimal(10.0))))
				// .andExpect(jsonPath("$.products[0].updated", Matchers.is(new Date())))
				.andExpect(jsonPath("$.countrycode", Matchers.is("JO")))
				.andExpect(jsonPath("$.currency", Matchers.is("JOD")))
				.andExpect(jsonPath("$.id", Matchers.is("abcdef")))

				.andExpect(jsonPath("$.products", Matchers.hasSize(1)));
	}

	@Test
	public void testcreateShopingCart() throws Exception {

		

		Mockito.when(shopingService.createShopingCart(Mockito.any(ShoppingCartRequest.class), any()))
				.thenReturn("SUCCESS");

		ShoppingCartRequest request = new ShoppingCartRequest();
		mockMvc.perform(post("/api/carts").content(asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

				
	}

	@Test
	public void testemptycreateShopingCart() throws Exception {

		ShoppingCartRequest request = null;
		mockMvc.perform(post("/api/carts")).andExpect(status().is4xxClientError());

	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
