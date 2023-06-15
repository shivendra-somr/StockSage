package com.masai.entities;

public class Trader {
	private String name;
	private String address;
	private String contactNumber;
	private String username;
	private String password;

	public Trader(String name, String address, String contactNumber, String username, String password) {
		super();
		this.name = name;
		this.address = address;
		this.contactNumber = contactNumber;
		this.username = username;
		this.password = password;
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

	
}
