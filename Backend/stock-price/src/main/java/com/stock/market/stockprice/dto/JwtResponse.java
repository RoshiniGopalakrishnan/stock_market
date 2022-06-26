package com.stock.market.stockprice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Roshini
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {

	private  String token;
	private String username;
}
