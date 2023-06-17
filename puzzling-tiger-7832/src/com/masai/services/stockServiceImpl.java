package com.masai.services;

import java.util.Map;

import com.masai.entities.Stock;

public class stockServiceImpl implements stockService {

	@Override
	public void addStock(Map<Integer, Stock> stocks, Stock stock) {
		// TODO Auto-generated method stub
		stocks.put(stock.getStockId(), stock);
        System.out.println("Stock added successfully.");
	}

	@Override
	public void removeStock(Map<Integer, Stock> stocks, int stockId) {
		// TODO Auto-generated method stub
		if (stocks.containsKey(stockId)) {
            stocks.remove(stockId);
            System.out.println("Stock removed successfully.");
        } else {
            System.out.println("Stock not found.");
        }
	}

	@Override
	public void updateStockPrice(Map<Integer, Stock> stocks, int stockId, double newPrice) {
		// TODO Auto-generated method stub
		if (stocks.containsKey(stockId)) {
            Stock stock = stocks.get(stockId);
            stock.setStockPrice(newPrice);
            System.out.println("Stock price updated successfully.");
        } else {
            System.out.println("Stock not found.");
        }
	}

	@Override
	public void viewAllStocks(Map<Integer, Stock> stocks) {
		// TODO Auto-generated method stub
		for (Stock stock : stocks.values()) {
            System.out.println(stock);
        }
	}

	@Override
	public Stock searchStock(Map<Integer, Stock> stocks, int stockId) {
		// TODO Auto-generated method stub
		if (stocks.containsKey(stockId)) {
            return stocks.get(stockId);
        } else {
            System.out.println("Stock not found.");
            return null;
        }
	}

}
