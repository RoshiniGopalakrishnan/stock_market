package com.stock.market.company.entity;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Roshini Entity for userDetail
 *
 */
@DynamoDBTable(tableName = "userDetail")
@Getter
@Setter
public class UserDetail {


	@Id
	@DynamoDBHashKey(attributeName = "id")
	@DynamoDBAutoGeneratedKey
	private String id;
	@DynamoDBAttribute(attributeName = "username")

	private String username;

	@DynamoDBAttribute(attributeName = "password")
	private String password;
}
