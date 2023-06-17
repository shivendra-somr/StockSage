package com.masai.services;

import java.util.List;

import com.masai.entities.Transaction;

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
		if (transactions.isEmpty()) {
            System.out.println("Transaction history is empty.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
	}

}
