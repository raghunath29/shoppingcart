package com.demo.shopping.cart.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShopingCardService {

	@Autowired
	private ShopingCartRepository shoppingRepo;

	public String createShopingCart(ShoppingCartRequest request, String interactionId) {
		ShoppingCart shopEntity = new ShoppingCart();
		ShopingCartResponse response = new ShopingCartResponse();
		shopEntity.setCountrycode(request.getCountrycode());
		shopEntity.setCurrency(request.getCurrency());
		String id = generateProfileId();
		shopEntity.setId(id);

		Set<Product> products = new HashSet<>();
		for (Product p : request.getProducts()) {
			Product pro = new Product();
			pro.setId(p.getId());
			pro.setCategory(p.getCategory());
			pro.setDescription(p.getDescription());
			pro.setPrice(p.getPrice());
			pro.setCreated(new Date());
			pro.setUpdated(new Date());
			products.add(pro);

		}
		shopEntity.setProducts(products);
		shopEntity.setUpdated(new Date());
		shopEntity.setCreated(new Date());
		//BeanUtils.copyProperties(shopEntity, response);
		try {
			shoppingRepo.save(shopEntity);
		} catch (Exception e) {
			log.error("Exception occured while saving  profile for single Junior CustomerId" + "for intereaction id"
					+ interactionId + e.getMessage());
			throw new TechnicalException(
					"Error in DB Operations while creating shoping cart" + "for intereaction id" + interactionId);
		}

		return "success";

	}

	public List<ShopingCartResponse> getALLCarts(String interactionId) {

		List<ShopingCartResponse> response = new ArrayList<ShopingCartResponse>();
		List<ShoppingCart> entities = new ArrayList<>();
		try {
			entities = shoppingRepo.findAll();
		} catch (Exception e) {
			log.error("Exception occured while getting all profiles" + "for intereaction id" + interactionId
					+ e.getMessage());
			throw new TechnicalException(
					"Exception occured while getting all profiles" + "for intereaction id" + interactionId);
		}
		for (ShoppingCart shop : entities) {
			ShopingCartResponse shopResponse = new ShopingCartResponse();
			BeanUtils.copyProperties(shop, shopResponse);
			response.add(shopResponse);

		}

		return response;

	}

	public ShopingCartResponse getCart(String interactionId, String cartId) {

		Optional<ShoppingCart> response = null;

		try {
			response = shoppingRepo.findById(cartId);
		} catch (Exception e) {
			log.error("Exception occured while getting shoping cart" + "for intereaction id" + interactionId
					+ e.getMessage());
			throw new TechnicalException(
					"Exception occured while getting shopping cart" + "for intereaction id" + interactionId);
		}
		if (response.isPresent()) {
			ShopingCartResponse shopResponse = new ShopingCartResponse();
			BeanUtils.copyProperties(response.get(), shopResponse);
			return shopResponse;

		} else {
			log.error("shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
			throw new BuisnessException(
					"shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
		}

	}

	public String createProduct(String interactionId, String cartId, ProductRequest request) {

		Optional<ShoppingCart> response = null;

		try {
			response = shoppingRepo.findById(cartId);
		} catch (Exception e) {
			log.error("Exception occured while getting shoping cart" + "for intereaction id" + interactionId
					+ e.getMessage());
			throw new TechnicalException(
					"Exception occured while getting shopping cart" + "for intereaction id" + interactionId);
		}
		if (response.isPresent()) {
			ShoppingCart cart = new ShoppingCart();
			cart = response.get();
			Set<Product> products = cart.getProducts();
			Product product = new Product();
			BeanUtils.copyProperties(request, product);
			product.setCreated(new Date());
			product.setUpdated(new Date());
			Boolean add = products.add(product);
			if (!add) {
				log.error("product already exists" + "  " + cartId + "for intereaction id" + interactionId);
				throw new ProductAlreadyExistsException(
						"product already exists" + "  " + cartId + "for intereaction id" + interactionId);
			}
			cart.setProducts(products);
			cart.setUpdated(new Date());
			try {
				shoppingRepo.save(cart);
			} catch (Exception e) {
				log.error("Exception occured while saving  profile for single Junior CustomerId" + "for intereaction id"
						+ interactionId + e.getMessage());
				throw new TechnicalException(
						"Error in DB Operations while creating shoping cart" + "for intereaction id" + interactionId);
			}

			ShopingCartResponse shopResponse = new ShopingCartResponse();
			//BeanUtils.copyProperties(cart, shopResponse);
			return "success";

		} else {
			log.error("shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
			throw new BuisnessException(
					"shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
		}

	}

	public String deleteProduct(String interactionId, String cartId, String productId) {

		Optional<ShoppingCart> response = null;

		try {
			response = shoppingRepo.findById(cartId);
		} catch (Exception e) {
			log.error("Exception occured while getting shoping cart" + "for intereaction id" + interactionId
					+ e.getMessage());
			throw new TechnicalException(
					"Exception occured while getting shopping cart" + "for intereaction id" + interactionId);
		}
		if (response.isPresent()) {
			ShoppingCart cart = new ShoppingCart();
			cart = response.get();
			Set<Product> products = cart.getProducts();
			products = products.stream().filter(i -> !i.getId().equalsIgnoreCase(productId))
					.collect(Collectors.toSet());
			cart.setProducts(products);
			cart.setUpdated(new Date());
			try {
				shoppingRepo.save(cart);
			} catch (Exception e) {
				log.error("Exception occured while deleting product" + "for intereaction id" + interactionId
						+ e.getMessage());
				throw new TechnicalException(
						"Error in DB Operations while deleting product" + "for intereaction id" + interactionId);
			}

			ShopingCartResponse shopResponse = new ShopingCartResponse();
			//BeanUtils.copyProperties(cart, shopResponse);
			return "success";

		} else {
			log.error("shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
			throw new BuisnessException(
					"shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
		}

	}

	public ProductResponse getProduct(String interactionId, String cartId, String productId) {

		Optional<ShoppingCart> response = null;
		ProductResponse proResponse = new ProductResponse();

		try {
			response = shoppingRepo.findById(cartId);
		} catch (Exception e) {
			log.error("Exception occured while getting shoping cart" + "for intereaction id" + interactionId
					+ e.getMessage());
			throw new TechnicalException(
					"Exception occured while getting shopping cart" + "for intereaction id" + interactionId);
		}
		if (response.isPresent()) {
			ShoppingCart cart = new ShoppingCart();
			cart = response.get();
			Set<Product> products = cart.getProducts();
			Product product = null;
			Optional<Product> productOp = products.stream().filter(i -> i.getId().equalsIgnoreCase(productId))
					.findFirst();
			if (productOp.isPresent()) {
				product = productOp.get();
				BeanUtils.copyProperties(product, proResponse);
				return proResponse;
			} else {
				log.error("product not found for id" + "  " + productId + "for intereaction id" + interactionId);
				throw new BuisnessException(
						"shoping cart not found for id" + "  " + productId + "for intereaction id" + interactionId);
			}

		} else {
			log.error("shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
			throw new BuisnessException(
					"shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
		}

	}
	
	public List<ProductResponse> getProductList(String interactionId, String cartId) {

		Optional<ShoppingCart> response = null;
		List<ProductResponse> proResponses = new ArrayList<>();

		try {
			response = shoppingRepo.findById(cartId);
		} catch (Exception e) {
			log.error("Exception occured while getting shoping cart" + "for intereaction id" + interactionId
					+ e.getMessage());
			throw new TechnicalException(
					"Exception occured while getting shopping cart" + "for intereaction id" + interactionId);
		}
		if (response.isPresent()) {
			ShoppingCart cart = new ShoppingCart();
			cart = response.get();
			Set<Product> products = cart.getProducts();
			for(Product p :products) {
				ProductResponse proResponse = new ProductResponse();
				BeanUtils.copyProperties(p, proResponse);
				proResponses.add(proResponse);
			}
			

		} else {
			log.error("shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
			throw new BuisnessException(
					"shoping cart not found for id" + "  " + cartId + "for intereaction id" + interactionId);
		}
		return proResponses;

	}


	public String generateProfileId() {
		Random random = new Random();
		DateFormat dateFormat = new SimpleDateFormat("yyMMdd");
		String AlphaNumericString = "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder s = new StringBuilder(6);
		int y;
		for (y = 0; y < 6; y++) {
			// generating a random number
			int index = (int) (AlphaNumericString.length() * Math.random());
			// add Character one by one in end of s
			s.append(AlphaNumericString.charAt(index));
		}
		Date date = new Date();
		String result = dateFormat.format(date) + s.toString();
		return result;

	}
}
