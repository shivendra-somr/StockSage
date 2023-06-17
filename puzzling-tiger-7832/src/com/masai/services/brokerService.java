package com.masai.services;

import java.util.List;
import java.util.Map;

import com.masai.entities.Broker;
import com.masai.entities.Stock;
import com.masai.entities.Trader;
import com.masai.entities.Transaction;

public interface brokerService {
	
	public boolean login(String username,String password, Map<String, Broker> brokers);
	
	void addTraders(Map<String, Trader> traders);
    void manageTraderAccounts(Map<String, Trader> traders);
    void executeTrades(Map<Integer, Stock> stocks, Map<String, Trader> traders, List<Transaction> transactions);
    void viewTransactionHistoryForTraders(Map<String, Trader> traders, List<Transaction> transactions);

	public List<Broker> getActiveBrokers(Map<String, Broker> brokers);
	
}
