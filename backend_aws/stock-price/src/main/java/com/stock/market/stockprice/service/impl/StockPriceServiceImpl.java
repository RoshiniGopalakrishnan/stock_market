package com.stock.market.stockprice.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.OptionalDouble;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.stock.market.stockprice.dto.PriceDto;
import com.stock.market.stockprice.dto.ViewStockPriceDetailsDto;
import com.stock.market.stockprice.entity.Price;
import com.stock.market.stockprice.repository.SortStockRepository;
import com.stock.market.stockprice.repository.StockPriceRepository;
import com.stock.market.stockprice.service.IStockPriceService;

import lombok.extern.log4j.Log4j2;

/**
 * @author Roshini
 *
 */
@Service
@Log4j2
public class StockPriceServiceImpl implements IStockPriceService {
	@Autowired
	private StockPriceRepository stockPriceRepository;

	@Autowired
	private SortStockRepository sortStockRepository;

	@Autowired
	private KafkaService kafkaService;

	@Transactional
	public void addStockPrice(PriceDto priceDto, String companyCode) throws ParseException {
		Price price = new Price();
		price.setCompanyCode(companyCode);
		price.setStckPrice(priceDto.getStckPrice());
		price.setCreationDate(new Date());
		log.info("StockPriceServiceImpl.addStockPrice, Price - {} ", price);
//		kafkaService.send(price);
		stockPriceRepository.save(price);
	}

	@Override
	@Transactional
	public ViewStockPriceDetailsDto viewStockDetails(String companyCode, Date startDate, Date endDate)
			throws ParseException {
		// TODO Auto-generated method stub

		Iterable<Price> allStocKPrice = sortStockRepository.findAll();
		log.info("StockPriceServiceImpl.viewStockDetails, allStockPrice - {}", allStocKPrice);

		List<Price> priceList = new ArrayList<Price>();

		for (Price pr : allStocKPrice) {
			log.info("startDate: " + startDate.before(pr.getCreationDate()));
			log.info("endDate: " + endDate.after(pr.getCreationDate()));

			if (pr.getCompanyCode().equals(companyCode) && startDate.before(pr.getCreationDate())
					&& endDate.after(pr.getCreationDate())) {
				priceList.add(pr);
			}
		}
		log.info("StockPriceServiceImpl.viewStockDetails, priceList - {}", priceList);

		List<Double> stockPriceList = new ArrayList<Double>();
		if (!priceList.isEmpty()) {
			for (Price price : priceList) {
				if (!ObjectUtils.isEmpty(price.getStckPrice())) {
					stockPriceList.add(price.getStckPrice());
				}
			}
		}
		Collections.sort(stockPriceList);
		ViewStockPriceDetailsDto viewStockPriceDetailsDto = null;
		if (!ObjectUtils.isEmpty(stockPriceList)) {
			double min = stockPriceList.get(0);
			double max = stockPriceList.get(stockPriceList.size() - 1);
			OptionalDouble average = stockPriceList.stream().mapToDouble(n -> n).average();
			viewStockPriceDetailsDto = ViewStockPriceDetailsDto.builder().average(average).min(min).max(max)
					.stockPriceList(stockPriceList).build();
		}
		log.info("StockPriceServiceImpl.viewStockDetails, final response - {}", viewStockPriceDetailsDto);
		return viewStockPriceDetailsDto;
	}
}
