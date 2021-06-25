package com.demo.shopping.cart;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.demo.shopping.cart.Exception.BuisnessException;
import com.demo.shopping.cart.Exception.ProductAlreadyExistsException;
import com.demo.shopping.cart.Exception.TechnicalException;
import com.demo.shopping.cart.entity.Product;
import com.demo.shopping.cart.entity.ShoppingCart;
import com.demo.shopping.cart.repository.ShopingCartRepository;
import com.demo.shopping.cart.request.ProductRequest;
import com.demo.shopping.cart.request.ShoppingCartRequest;
import com.demo.shopping.cart.response.ProductResponse;
import com.demo.shopping.cart.response.ShopingCartResponse;
import com.demo.shopping.cart.service.ShopingCardService;

@ExtendWith(MockitoExtension.class)
public class ShopingCardServiceTest {
	@InjectMocks
	ShopingCardService service;

	@Mock
	ShopingCartRepository dao;

	@Test
	public void testgetALLCarts() {
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

		List<ShoppingCart> shop = Arrays.asList(response);

		when(dao.findAll()).thenReturn(shop);

		// test
		List<ShopingCartResponse> empList = service.getALLCarts("abc");

		assertEquals(1, empList.size());

		assertEquals("JO", empList.get(0).getCountrycode());
		assertEquals("JOD", empList.get(0).getCurrency());
		assertEquals("abcdef", empList.get(0).getId());
		assertEquals(1, empList.get(0).getProducts().size());
		verify(dao, times(1)).findAll();
	}

	public void testsaveCart() {
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

		ShoppingCart Shopresponse = new ShoppingCart();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		products.add(pro);
		response.setProducts(products);
		when(dao.save(Mockito.any(ShoppingCart.class))).thenReturn(Shopresponse);
		when(service.generateProfileId()).thenReturn("abcdef");

		// test
		ShoppingCart cart = null;
		String shop = service.createShopingCart(Mockito.any(ShoppingCartRequest.class), any());
		verify(dao, times(1)).save(Mockito.any(ShoppingCart.class));
		assertEquals("success", shop);
	}

	public void testsaveCartFail() {

		when(dao.save(Mockito.any(ShoppingCart.class))).thenThrow();

		// test
		ShoppingCart cart = null;
		Assertions.assertThrows(TechnicalException.class, () -> {
			service.createShopingCart(Mockito.any(ShoppingCartRequest.class), any());
		});

		verify(dao, times(1)).save(Mockito.any(ShoppingCart.class));
	}

	@Test
	public void testgetCart() {
		Optional<ShoppingCart> cart;
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
		cart = Optional.of(response);

		when(dao.findById(any())).thenReturn(cart);

		// test
		ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		assertEquals("JO", cartResponse.getCountrycode());
		assertEquals("JOD", cartResponse.getCurrency());
		assertEquals("abcdef", cartResponse.getId());
		assertEquals(1, cartResponse.getProducts().size());

		verify(dao, times(1)).findById(any());
	}

	@Test
	public void testgetCartFail() {
		Optional<ShoppingCart> cart;
		cart = Optional.empty();
		when(dao.findById(any())).thenReturn(cart);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		Assertions.assertThrows(BuisnessException.class, () -> {
			service.getCart("abc", "abcdef");
		});

		verify(dao, times(1)).findById(any());
	}

	@Test
	public void testcreateProductException() {
		Optional<ShoppingCart> cart;
		ShoppingCart response = new ShoppingCart();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		response.setId("abcdef");
		Product pro = new Product();
		pro.setCategory("loan");
		pro.setId("123");
		pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);
		cart = Optional.of(response);

		when(dao.findById(any())).thenReturn(cart);
		ProductRequest request = new ProductRequest();
		request.setId("123");
		request.setCategory("loan");
		request.setDescription("loan amount");
		request.setPrice(new BigDecimal(10.0));
		// service.createProduct("abc", "abcdef", request);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		Assertions.assertThrows(ProductAlreadyExistsException.class, () -> {
			service.createProduct("abc", "abcdef", request);
		});

	}

	@Test
	public void testcreateProduct() {
		Optional<ShoppingCart> cart;
		ShoppingCart response = new ShoppingCart();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		response.setId("abcdef");
		Product pro = new Product();
		pro.setCategory("loan");
		pro.setId("123");
		pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);
		cart = Optional.of(response);

		when(dao.findById(any())).thenReturn(cart);
		ProductRequest request = new ProductRequest();
		request.setId("1234");
		request.setCategory("loan1");
		request.setDescription("loan amount2");
		request.setPrice(new BigDecimal(10.0));
		// service.createProduct("abc", "abcdef", request);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");

		String saveresponse = service.createProduct("abc", "abcdef", request);
		verify(dao, times(1)).save(Mockito.any(ShoppingCart.class));
		assertEquals("success", saveresponse);

	}

	@Test
	public void testcreateProductNull() {
		Optional<ShoppingCart> cart;
		cart = Optional.empty();
		when(dao.findById(any())).thenReturn(cart);

		ProductRequest request = new ProductRequest();
		request.setId("1234");
		request.setCategory("loan1");
		request.setDescription("loan amount2");
		request.setPrice(new BigDecimal(10.0));
		// service.createProduct("abc", "abcdef", request);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");

		Assertions.assertThrows(BuisnessException.class, () -> {
			service.createProduct("abc", "abcdef", request);

		});

	}

	@Test
	public void testdeleteProductFail() {
		Optional<ShoppingCart> cart;
		cart = Optional.empty();
		Exception e = new Exception();
		when(dao.findById(any())).thenThrow();

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		Assertions.assertThrows(TechnicalException.class, () -> {
			service.deleteProduct("abc", "abcdef", "abc");
		});

		verify(dao, times(1)).findById(any());
	}

	@Test
	public void testdeleteProductNotFound() {
		Optional<ShoppingCart> cart;
		cart = Optional.empty();
		when(dao.findById(any())).thenReturn(cart);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		Assertions.assertThrows(BuisnessException.class, () -> {
			service.deleteProduct("abc", "abcdef", "abc");
		});

		verify(dao, times(1)).findById(any());
	}

	@Test
	public void testdeleteProduct() {
		Optional<ShoppingCart> cart;

		ShoppingCart response = new ShoppingCart();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		response.setId("abcdef");
		Product pro = new Product();
		pro.setId("abc");
		pro.setCategory("loan");
		pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);
		cart = Optional.of(response);
		when(dao.findById(any())).thenReturn(cart);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		String deleteresponse = service.deleteProduct("abc", "abcdef", "abc");
		assertEquals("success", deleteresponse);

		verify(dao, times(1)).save(Mockito.any(ShoppingCart.class));

	}

	@Test
	public void testProductList() {
		Optional<ShoppingCart> cart;
		List<ProductResponse> proRsponse;

		ShoppingCart response = new ShoppingCart();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		response.setId("abcdef");
		Product pro = new Product();
		pro.setId("abc");
		pro.setCategory("loan");
		pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);
		cart = Optional.of(response);
		when(dao.findById(any())).thenReturn(cart);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		proRsponse = service.getProductList("abc", "abcdef");
		assertEquals(1, proRsponse.size());
		assertEquals("abc", proRsponse.get(0).getId());
		assertEquals("loan", proRsponse.get(0).getCategory());
		assertEquals("loan amount", proRsponse.get(0).getDescription());

	}

	@Test
	public void testProductListfail() {
		Optional<ShoppingCart> cart;

		cart = Optional.empty();
		when(dao.findById(any())).thenReturn(cart);
		Assertions.assertThrows(BuisnessException.class, () -> {
			service.getProductList("abc", "abcdef");
		});

	}

	@Test
	public void testgetProduct() {
		Optional<ShoppingCart> cart;
		ProductResponse proRsponse;

		ShoppingCart response = new ShoppingCart();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		response.setId("abcdef");
		Product pro = new Product();
		pro.setId("abc");
		pro.setCategory("loan");
		pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);
		cart = Optional.of(response);
		when(dao.findById(any())).thenReturn(cart);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");
		proRsponse = service.getProduct("abc", "abcdef", "abc");

		assertEquals("abc", proRsponse.getId());
		assertEquals("loan", proRsponse.getCategory());
		assertEquals("loan amount", proRsponse.getDescription());

	}

	@Test
	public void testgetProductfail() {
		Optional<ShoppingCart> cart;
		ProductResponse proRsponse;

		ShoppingCart response = new ShoppingCart();
		response.setCountrycode("JO");
		response.setCurrency("JOD");
		response.setId("abcdef");
		Product pro = new Product();
		pro.setId("abc");
		pro.setCategory("loan");
		pro.setCreated(new Date());
		pro.setDescription("loan amount");
		pro.setPrice(new BigDecimal(10.0));
		pro.setUpdated(new Date());
		Set<Product> products = new HashSet<>();
		products.add(pro);
		response.setProducts(products);
		cart = Optional.of(response);
		when(dao.findById(any())).thenReturn(cart);

		// test
		// ShopingCartResponse cartResponse = service.getCart("abc", "abcdef");

		Assertions.assertThrows(BuisnessException.class, () -> {
			service.getProduct("abc", "abcdef", "abcd");
		});

	}

	@Test
	public void testgetProductcardfail() {
		Optional<ShoppingCart> cart;

		cart = Optional.empty();
		when(dao.findById(any())).thenReturn(cart);

		Assertions.assertThrows(BuisnessException.class, () -> {
			service.getProduct("abc", "abcdef", "abcd");
		});

	}

}
