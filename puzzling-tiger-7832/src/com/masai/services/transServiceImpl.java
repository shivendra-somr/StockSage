package com.masai.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.masai.entities.Trader;
import com.masai.entities.Transaction;
import com.masai.utilities.FileExists;

public class transServiceImpl implements transService {

	@Override
	public void executeTrade(List<Transaction> transactions, Transaction trade) {
		// TODO Auto-generated method stub
		transactions.add(trade);
		System.out.println("Trade executed successfully.");
	}

	@Override
	public void viewTransactionHistory(List<Transaction> transactions) {
		// TODO Auto-generated method stub
		transactions = FileExists.transactionFile();
		if (transactions.isEmpty()) {
            System.out.println("Transaction history is empty.");
        } else {
    		transactions = FileExists.transactionFile();
    		if (transactions.isEmpty()) {
    			System.out.println("Transaction history is empty.");
    			return;
    		}
        	System.out.println("Transaction History:");
    		System.out.println();
    		int count=1;
    		for (Transaction t : transactions) {
    			System.out.println("-------------Transaction No."+ count++ +"-------------------");
    			System.out.println("\tTraderName\t---->\t"+t.getTraderUsername());
    			System.out.println("\tStockSymbol\t---->\t"+t.getStockSymbol());
    			System.out.println("\tQuantity\t---->\t"+t.getQuantity());
    			System.out.println("\tPrice\t\t---->\tRs."+t.getPrice());
    			double total = t.getQuantity()*t.getPrice();
    			System.out.println("\tTotalPrice\t---->\tRs."+Math.round(total));
    			System.out.println("------------------------------------------------");
    		}
        }
	}
	
	public void filterTransactionsByTrader(List<Transaction> transactions, String traderUsername) {
        List<Transaction> filteredTransactions = new ArrayList<>();

        for (Transaction transaction : transactions) {
            if (transaction.getTraderUsername().equals(traderUsername)) {
                filteredTransactions.add(transaction);
            }
        }
        if (filteredTransactions.isEmpty()) {
			System.out.println("Transaction history is empty.");
			return;
		}
    	System.out.println("Transaction History:");
		System.out.println();
		int count=1;
		for (Transaction t : filteredTransactions) {
			System.out.println("-------------Transaction No."+ count++ +"-----------------");
			System.out.println("\tTraderName\t---->\t"+t.getTraderUsername());
			System.out.println("\tStockSymbol\t---->\t"+t.getStockSymbol());
			System.out.println("\tQuantity\t---->\t"+t.getQuantity());
			System.out.println("\tPrice\t\t---->\tRs."+t.getPrice());
			double total = t.getQuantity()*t.getPrice();
			System.out.println("\tTotalPrice\t---->\tRs."+Math.round(total));
			System.out.println("-------------------------------------");
		}
    }
}
