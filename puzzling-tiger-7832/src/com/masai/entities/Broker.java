package com.masai.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Broker implements Serializable{
	private String username;
    private String password;
    private boolean verified;
    private List<Transaction> transactions;

    public Broker(String username, String password) {
        this.username = username;
        this.password = password;
        this.verified = false;
        this.transactions = new ArrayList();
    }

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	@Override
	public String toString() {
		return "Broker [username=" + username + ", password=" + password + ", verified=" + verified + "]";
	}
    
}
