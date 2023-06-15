package com.masai.entities;

import java.io.Serializable;
//import java.util.HashMap;
//import java.util.Map;
import java.util.Objects;

public class Stock implements Serializable{
	private int stockId;
	private String stockName;
	private double stockPrice;

	public Stock() {
		super();
	}

	public Stock(int stockId, String stockName, double stockPrice) {
		super();
		this.stockId = stockId;
		this.stockName = stockName;
		this.stockPrice = stockPrice;
	}

	public int getStockId() {
		return stockId;
	}

	public void setStockId(int stockId) {
		this.stockId = stockId;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public double getStockPrice() {
		return stockPrice;
	}

	public void setStockPrice(double stockPrice) {
		this.stockPrice = stockPrice;
	}

//	public static Map<Integer, Stock> getStocks() {
//        Map<Integer, Stock> stocks = new HashMap<>();
//        
//        stocks.put(1, new Stock(1, "Reliance", 2520.40));
//        stocks.put(2, new Stock(2, "TCS", 3249.95));
//        stocks.put(3, new Stock(3, "HDFC", 1600.60));
//        stocks.put(4, new Stock(4, "ICICI", 935.75));
//        stocks.put(5, new Stock(5, "HUL", 2688.35));
//
//        return stocks;
//    }
	@Override
	public String toString() {
		return "Stock [stockId=" + stockId + ", stockName=" + stockName + ", stockPrice=" + stockPrice + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(stockId, stockName, stockPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return stockId == other.stockId && Objects.equals(stockName, other.stockName)
				&& Double.doubleToLongBits(stockPrice) == Double.doubleToLongBits(other.stockPrice);
	}
	
	
}
