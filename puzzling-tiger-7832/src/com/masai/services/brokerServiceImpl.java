package com.masai.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.masai.entities.Broker;
import com.masai.entities.Stock;
import com.masai.entities.Trader;
import com.masai.entities.Transaction;
import com.masai.utilities.FileExists;

public class brokerServiceImpl implements brokerService {

	public boolean login(String userName, String pass, Map<String, Broker> brokers) {
		// TODO Auto-generated method stub\
		if (brokers.containsKey(userName)) {
			Broker broker = brokers.get(userName);
			if (broker.getPassword().equals(pass)) {
				System.out.println("Login successful. Welcome, " + broker.getUsername() + "!");

				return true;
			}
		}
		return false;
	}

	public void addTraders(Map<String, Trader> traders) {
		// TODO Auto-generated method stub
		
	}

	public void manageTraderAccounts(Map<String, Trader> traders) {
		// TODO Auto-generated method stub
		
	}

	public void executeTrades(Map<Integer, Stock> stocks, Map<String, Trader> traders, List<Transaction> transactions) {
		// TODO Auto-generated method stub
		
	}

	public void viewTransactionHistoryForTraders(Map<String, Trader> traders, List<Transaction> transactions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Broker> getActiveBrokers(Map<String, Broker> brokers) {
		// TODO Auto-generated method stub
		brokers = FileExists.brokerFile();
		List<Broker> activeBrokers = new ArrayList<>();

        for (Broker broker : brokers.values()) {
            if (broker.isVerified()) {
                activeBrokers.add(broker);
            }
        }

        return activeBrokers;
	}
	
}
