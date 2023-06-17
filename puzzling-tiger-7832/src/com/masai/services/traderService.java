package com.masai.services;

import java.util.List;
import java.util.Map;

import com.masai.entities.Trader;


public interface traderService {
	boolean login(String userName, String pass, Map<String, Trader> traders);

	void deleteAccount();

	void viewPortfolioHistory();

	void sellStocks();

	void buyStocks();

	void viewMarketTrendsAndStockPrices();

	List<Trader> getActiveTraders(Map<String, Trader> traders);
}
