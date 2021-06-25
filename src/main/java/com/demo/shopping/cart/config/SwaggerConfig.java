package com.demo.shopping.cart.config;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;

import io.swagger.models.Swagger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.function.Predicate;

@Configuration
public class SwaggerConfig {

	@Bean
	public Docket apis() {

		return new Docket(DocumentationType.SWAGGER_2)

				.select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any()).build()
				.apiInfo(new ApiInfo("API Documentation for shopping cart ", "shoping cart API", "1.0.0", "",
						new Contact("SHopping cart", "shoping card", "CMTF@arabbank.com.jo"),
						"shoping cart license 1.0", " ", Collections.emptyList()));

	}

	

}
