package com.stock.market.company.config;

import java.util.Date;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class DataTypeConvertedClass implements DynamoDBTypeConverter<String, Date>{

	private ObjectMapper objectMapper= new ObjectMapper();
	@Override
	public String convert(Date object) {
		try {
			return objectMapper.writeValueAsString(object);
		}catch(JsonProcessingException e){
			throw new RuntimeException();
		}
	}

	@Override
	public Date unconvert(String object) {
		try {
			System.out.println(object);
			return objectMapper.readValue(object, Date.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException();
		}
	}

}
