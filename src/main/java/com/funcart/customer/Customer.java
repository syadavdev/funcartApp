package com.funcart.customer;

public class Customer{
	
	private String username;
	private String password;
	private long phoneNumber;
	private String shippingAddress;
	private String billingAddress;
	
	//getter setter
	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	private String email;

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Customer(String username, String password, long phoneNumber, String shippingAddress, String billingAddress,
			String email) {
		super();
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.shippingAddress = shippingAddress;
		this.billingAddress = billingAddress;
		this.email = email;
	}

	public Customer() {
		super();
	}
	
}
