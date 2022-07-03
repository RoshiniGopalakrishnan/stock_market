package com.stock.market.company.repository;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.stock.market.company.config.DynamoConfig;
import com.stock.market.company.entity.CompanyDetails;
import com.stock.market.company.entity.Price;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class MapperDynamo {

	@Autowired
	DynamoConfig config;

	@Value("${amazon.dynamodb.endpoint}")
	String endpoint;
	@Value("${amazon.aws.accesskey}")
	String accesskey;
	@Value("${amazon.aws.secretkey}")
	String secretkey;
	@Value("${amazon.aws.region}")
	String region;

	public List<Price> findByCompanyCode(String companyCode) {
		DynamoDBMapper mapper = new DynamoDBMapper(config.amazonDynamoDB());
		Price price = new Price();
		price.setCompanyCode(companyCode);
		DynamoDBQueryExpression<Price> query = new DynamoDBQueryExpression<Price>().withHashKeyValues(price);
		List<Price> scanResult = mapper.query(Price.class, query);
		return scanResult;

	}

	public void deleteByCompanyCodeStockPrice(Price price) {
		DynamoDBMapper mapper = new DynamoDBMapper(config.amazonDynamoDB());
//		HashMap<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
//		eav.put(":id", new AttributeValue().withS(price.getId()));
//		
//		eav.put(":creationDate", new AttributeValue().withS(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(price.getCreationDate())));
//		DynamoDBQueryExpression<Price> queryExpression = new DynamoDBQueryExpression<Price>()
//		            .withKeyConditionExpression("id = :id and creationDate=:creationDate")
//		            .withExpressionAttributeValues(eav);
		
//		List<Price> ddbResults = mapper.query(Price.class, queryExpression);
//		log.info("CompanyDetailsController.getAllCompanyDetailList, priceList - {}", ddbResults);
//		mapper.batchDelete(ddbResults);
		
		Price pr= new Price();
		pr.setId(price.getId());
		pr.setCreationDate(price.getCreationDate());
		DynamoDBQueryExpression<Price> query = new DynamoDBQueryExpression<Price>().withHashKeyValues(pr);
		List<Price> scanResult = mapper.query(Price.class, query);
		log.info("CompanyDetailsController.getAllCompanyDetailList, priceList - {}", scanResult);
		mapper.batchWrite(Collections.EMPTY_LIST, scanResult, new DynamoDBMapperConfig.TableNameOverride("stockPrice").config());
		mapper.batchDelete(scanResult);
		
		

	}

	public void deleteCompanyCodeCompanyDetails(String companyCode) {
		System.out.println("company");
		DynamoDBMapper mapper = new DynamoDBMapper(config.amazonDynamoDB());

		CompanyDetails companyDetails = new CompanyDetails();
		companyDetails.setCompanyCode(companyCode);
		DynamoDBQueryExpression<CompanyDetails> queryCompanyDetails = new DynamoDBQueryExpression<CompanyDetails>()
				.withHashKeyValues(companyDetails);
		mapper.batchDelete(mapper.query(CompanyDetails.class, queryCompanyDetails));
	}
}
