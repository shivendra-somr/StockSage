package com.masai.services;

import java.util.Map;

import com.masai.entities.Stock;

public interface stockService {
	void addStock(Map<Integer, Stock> stocks, Stock stock);
    void removeStock(Map<Integer, Stock> stocks, int stockId);
    void updateStockPrice(Map<Integer, Stock> stocks, int stockId, double newPrice);
    void viewAllStocks(Map<Integer, Stock> stocks);
    Stock searchStock(Map<Integer, Stock> stocks, int stockId);
}
