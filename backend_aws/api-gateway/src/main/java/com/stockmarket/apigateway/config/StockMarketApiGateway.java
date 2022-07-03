package com.stockmarket.apigateway.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
/**
 * @author Roshini This method is used to configure the gateway for all microservice
 *
 */
@Configuration
public class StockMarketApiGateway {
	
	@Value("${company.url}")
	String companyUrl;	
	
	@Value("${stock.url}")
	String stockUrl;
	
	@Bean
	public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(r -> r.path("/api/v1.0/market/company/**").uri(companyUrl))
				.route(r -> r.path("/api/v1.0/market/authentication/**").uri(companyUrl))
				.route(r -> r.path("/api/v1.0/market/stock/**").uri(stockUrl))
				.build();
	} 
	  @Bean
	    public CorsWebFilter corsWebFilter() {

	        final CorsConfiguration corsConfig = new CorsConfiguration();
	        corsConfig.setAllowedOrigins(Collections.singletonList("*"));
	        corsConfig.setMaxAge(3600L);
	        corsConfig.setAllowedMethods(Arrays.asList("GET", "POST","PUT","DELETE"));
	        corsConfig.addAllowedHeader("*");

	        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", corsConfig);

	        return new CorsWebFilter(source);
	    }  
}
