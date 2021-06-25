package com.demo.shopping.cart.controller;

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
import com.demo.shopping.cart.request.ShoppingCartRequest;
import com.demo.shopping.cart.response.ShopingCartResponse;
import com.demo.shopping.cart.service.ShopingCardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Api(value = "Card Ms")
@RestController
@Validated
@Slf4j
@RequestMapping(value = "/")
public class CartController {
	@Autowired
	private ShopingCardService shopingService;
	
	@ApiOperation(value = "", notes = "/Create cart", tags = "cart")
	@ApiResponses(value = { @ApiResponse(code = 200, response = String.class, message = "Success"),
			@ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
			@ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
			@ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
			@ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error") })
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(path = "/api/carts", method = { RequestMethod.POST }, produces = {APPLICATION_JSON_VALUE}, consumes = APPLICATION_JSON_VALUE)
	public String createShopingCart(
			@ApiParam(name = "lang", required = false, example = "en", value = "ISO lang code") @RequestHeader(required = false, name = "lang") String lang,
			@ApiParam(name = "x-fapi-interaction-id", required = false, example = "4457567568", value = "An RFC4122 UID used as a correlation id / Request Id")  @RequestHeader(required = false, name = "x-fapi-interaction-id") String interactionId,
            @RequestBody ShoppingCartRequest request) {
		ShopingCartResponse response = null;

		log.info("Recieved lang,interaction id for creating shopping cart" + interactionId);
		
		if (request != null) {
		    return	shopingService.createShopingCart(request, interactionId);
		} else {

			log.error("request is  Empty for RequestId={}, CustomerId={} ", interactionId,
					 "for creating cart");
			return "failure";

		}
	}
	
	@ApiOperation(value = "", notes = "/get all  carts", tags = "cart")
	@ApiResponses(value = { @ApiResponse(code = 200, response = ShopingCartResponse.class, message = "Success"),
			@ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
			@ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
			@ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
			@ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error") })
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(path = "/api/carts/all", method = { RequestMethod.GET }, produces = {APPLICATION_JSON_VALUE})
	public List<ShopingCartResponse> getAllCarts(
			@ApiParam(name = "lang", required = false, example = "en", value = "ISO lang code") @RequestHeader(required = false, name = "lang") String lang,
			@ApiParam(name = "x-fapi-interaction-id", required = false, example = "4457567568", value = "An RFC4122 UID used as a correlation id / Request Id")  @RequestHeader(required = false, name = "x-fapi-interaction-id") String interactionId) {
		

		log.info("Recieved lang,interaction id for getting all shopping cart" + interactionId);
	    return	shopingService.getALLCarts(interactionId);
		

		}
	
	@ApiOperation(value = "", notes = "/get shoping  cart", tags = "cart")
	@ApiResponses(value = { @ApiResponse(code = 200, response = ShopingCartResponse.class, message = "Success"),
			@ApiResponse(code = 400, response = ErrorResponseDTO.class, message = "Bad Request"),
			@ApiResponse(code = 401, response = ErrorResponseDTO.class, message = "Unauthorized"),
			@ApiResponse(code = 403, response = ErrorResponseDTO.class, message = "Forbidden"),
			@ApiResponse(code = 500, response = ErrorResponseDTO.class, message = "Internal Server Error") })
	@ResponseStatus(code = HttpStatus.OK)
	@RequestMapping(path = "/api/carts/{cart-id}", method = { RequestMethod.GET }, produces = {APPLICATION_JSON_VALUE})
	public ShopingCartResponse getShoppingCart(
			@ApiParam(name = "lang", required = false, example = "en", value = "ISO lang code") @RequestHeader(required = false, name = "lang") String lang,
            @ApiParam(name = "cart-id", value = "cart id", type = "String", required = true)
            @PathVariable(name = "cart-id")String cartId,
			@ApiParam(name = "x-fapi-interaction-id", required = false, example = "4457567568", value = "An RFC4122 UID used as a correlation id / Request Id")  @RequestHeader(required = false, name = "x-fapi-interaction-id") String interactionId) {
		

		log.info("Recieved lang,interaction id for getting all shopping cart" + interactionId);
	    return	shopingService.getCart(interactionId, cartId);
		

		}
	
	

}
