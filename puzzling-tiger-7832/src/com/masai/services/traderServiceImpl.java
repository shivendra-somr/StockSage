package com.masai.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.masai.entities.Stock;
import com.masai.entities.Trader;

public class traderServiceImpl implements traderService{

	public boolean login(String userName, String pass, Map<String, Trader> traders) {
		// TODO Auto-generated method stub\
		if (traders.containsKey(userName)) {
			Trader trader = traders.get(userName);
			if (trader.getPassword().equals(pass)) {
				System.out.println("Login successful. Welcome, " + trader.getUsername() + "!");

				return true;
			}
		}
		return false;
	}

	public void deleteAccount() {
		// TODO Auto-generated method stub

	}

	public void viewPortfolioHistory() {
		// TODO Auto-generated method stub

	}

	public void sellStocks() {
		// TODO Auto-generated method stub

	}

	public void buyStocks() {
		// TODO Auto-generated method stub

	}

	public void viewMarketTrendsAndStockPrices() {
		System.out.println("----Market-Trends-and-Stock-Prices----");

		Stock marketTrends = Stock.getStocks().get(1);
		System.out.print("Market Trends: ");
		System.out.println(marketTrends.getStockName() + "----" + "Rs. " + marketTrends.getStockPrice());

		Map<Integer, Stock> stockPrices = Stock.getStocks();
		System.out.println("Stock Prices:");
		for (Map.Entry<Integer, Stock> me : stockPrices.entrySet()) {
			Integer stockId = me.getKey();
			Stock st = me.getValue();
			System.out.println(stockId + "--|--" + st.getStockName() + "--|--" + "Rs. " + st.getStockPrice());
		}
	}

	@Override
	public List<Trader> getActiveTraders(Map<String, Trader> traders) {
		// TODO Auto-generated method stub
		List<Trader> activeTraders = new ArrayList<>();

        for (Trader trader : traders.values()) {
            if (trader.isVerified()) {
                activeTraders.add(trader);
            }
        }

        return activeTraders;
	}

	

}
