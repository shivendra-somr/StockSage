package com.masai.entities;

import java.io.Serializable;

public class Trader implements Serializable {
	private String name;
	private String address;
	private String contactNumber;
	private String username;
	private String password;
	private boolean isVerified;
	private double funds;

	public Trader(String name, String address, String contactNumber, String username, String password) {
		super();
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.username = username;
		this.password = password;
		this.isVerified = false;
		this.funds = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
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

	@Override
	public String toString() {
		return "Trader [name=" + name + ", address=" + address + ", contactNumber=" + contactNumber + ", username="
				+ username + ", password=" + password + "]";
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean b) {
		this.isVerified = b;
	}

	public double getFunds() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setFunds(double funds) {
		this.funds = funds;
	}
}
