package com.masai.services;

import java.util.List;

import com.masai.entities.Transaction;

public interface transService {
	void executeTrade(List<Transaction> transactions, Transaction trade);
	void viewTransactionHistory(List<Transaction> transactions);
}
