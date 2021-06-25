package com.demo.shopping.cart.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.demo.shopping.cart.dto.ErrorResponseDTO;
import com.demo.shopping.cart.request.ProductRequest;
import com.demo.shopping.cart.request.ShoppingCartRequest;
import com.demo.shopping.cart.response.ProductResponse;
import com.demo.shopping.cart.response.ShopingCartResponse;
import com.demo.shopping.cart.service.ShopingCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Product Ms")
@RestController
@Validated
@Slf4j
@RequestMapping(value = "/")
public class ProductController {
	@Autowired
	private ShopingCardService shopingService;
	
	@ApiOperation(value = "", notes = "/add product in cart", tags = "product")
	@ApiResponses(value = { @ApiResponse(code = 200, response = String.class, message = "Success"),
			@ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
			@ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
			@ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
			@ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error") })
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(path = "/api/carts/{cart-id}/products", method = { RequestMethod.PUT }, produces = {APPLICATION_JSON_VALUE}, consumes = APPLICATION_JSON_VALUE)
	public String createShopingCartProduct(
			@ApiParam(name = "lang", required = false, example = "en", value = "ISO lang code") @RequestHeader(required = false, name = "lang") String lang,
			@ApiParam(name = "x-fapi-interaction-id", required = false, example = "4457567568", value = "An RFC4122 UID used as a correlation id / Request Id")  @RequestHeader(required = false, name = "x-fapi-interaction-id") String interactionId,
			@PathVariable(name = "cart-id")String cartId,
            @RequestBody ProductRequest request) {
		ShopingCartResponse response = null;

		log.info("Recieved lang,interaction id for adding product in shopping cart" + interactionId);
		
		if (request != null) {
		    return	shopingService.createProduct(interactionId, cartId, request);
		} else {

			log.error("request is  Empty for RequestId={}, CustomerId={} ", interactionId,
					 "for creating product");
			return "failure";

		}
	}
	
	@ApiOperation(value = "", notes = "/delete product in cart", tags = "product")
	@ApiResponses(value = { @ApiResponse(code = 200, response = String.class, message = "Success"),
			@ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
			@ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
			@ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
			@ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error") })
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(path = "/api/carts/{cart-id}/products/{product-id}", method = { RequestMethod.DELETE }, produces = {APPLICATION_JSON_VALUE})
	public String deleteShopingCartProduct(
			@ApiParam(name = "lang", required = false, example = "en", value = "ISO lang code") @RequestHeader(required = false, name = "lang") String lang,
			@ApiParam(name = "x-fapi-interaction-id", required = false, example = "4457567568", value = "An RFC4122 UID used as a correlation id / Request Id")  @RequestHeader(required = false, name = "x-fapi-interaction-id") String interactionId,
			@PathVariable(name = "cart-id")String cartId,
			@PathVariable(name = "product-id")String productId) {
		ShopingCartResponse response = null;

		log.info("Recieved lang,interaction id for a deleting product in shopping cart" + interactionId);
	    return	shopingService.deleteProduct(interactionId, cartId, productId);
		

		}
	
	@ApiOperation(value = "", notes = "/get product in cart", tags = "product")
	@ApiResponses(value = { @ApiResponse(code = 200, response = ProductResponse.class, message = "Success"),
			@ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
			@ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
			@ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
			@ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error") })
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(path = "/api/carts/{cart-id}/products/{product-id}", method = { RequestMethod.GET }, produces = {APPLICATION_JSON_VALUE})
	public ProductResponse getShopingCartProduct(
			@ApiParam(name = "lang", required = false, example = "en", value = "ISO lang code") @RequestHeader(required = false, name = "lang") String lang,
			@ApiParam(name = "x-fapi-interaction-id", required = false, example = "4457567568", value = "An RFC4122 UID used as a correlation id / Request Id")  @RequestHeader(required = false, name = "x-fapi-interaction-id") String interactionId,
			@PathVariable(name = "cart-id")String cartId,
			@PathVariable(name = "product-id")String productId) {
		ShopingCartResponse response = null;

		log.info("Recieved lang,interaction id for a getting product in shopping cart" + interactionId);
	    return	shopingService.getProduct(interactionId, cartId, productId);
		

		}
	
	@ApiOperation(value = "", notes = "/get All products in cart", tags = "product")
	@ApiResponses(value = { @ApiResponse(code = 200, response = List.class, message = "Success"),
			@ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
			@ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
			@ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
			@ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error") })
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(path = "/api/carts/{cart-id}/products", method = { RequestMethod.GET }, produces = {APPLICATION_JSON_VALUE})
	public List<ProductResponse> getShopingCartAllProducts(
			@ApiParam(name = "lang", required = false, example = "en", value = "ISO lang code") @RequestHeader(required = false, name = "lang") String lang,
			@ApiParam(name = "x-fapi-interaction-id", required = false, example = "4457567568", value = "An RFC4122 UID used as a correlation id / Request Id")  @RequestHeader(required = false, name = "x-fapi-interaction-id") String interactionId,
			@PathVariable(name = "cart-id")String cartId
			) {
		

		log.info("Recieved lang,interaction id for a getting product in shopping cart" + interactionId);
	    return	shopingService.getProductList(interactionId, cartId);
		

		}
	}


