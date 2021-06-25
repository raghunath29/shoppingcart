package com.demo.shopping.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.Generated;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@EnableSwagger2
@SpringBootApplication


(scanBasePackages = { "com.demo.shopping.cart", "com.demo.shopping.cart.controller", "com.demo.shopping.cart.service" })
public class BootstrapApplication {
	@Generated
	public static void main(String[] args) {
		SpringApplication.run(BootstrapApplication.class, args);
	}

}
