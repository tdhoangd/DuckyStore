package com.duckyshop.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1826220370560212117L;

	private int		orderId;
	private int		contactId;
	private String 	orderDate;
	private Status	status;
	private ArrayList<OrderDetail> items = new ArrayList<>();   
	
	public Order() {
		super();
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getContactId() {
		return contactId;
	}

	public void setContactId(int contactId) {
		this.contactId = contactId;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public ArrayList<OrderDetail> getItems() {
		return items;
	}

	public void setItems(ArrayList<OrderDetail> items) {
		this.items = items;
	}
	
	
}
