package com.duckyshop.model;

import java.io.Serializable;

public class OrderDetail implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5002914449878324758L;
	private int itemId;
	private String itemName;
	private int delivered;
	private int remaining;
	private int qty;
	
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getDelivered() {
		return delivered;
	}
	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}
	public int getRemaining() {
		return remaining;
	}
	public void setRemaining(int remaining) {
		this.remaining = remaining;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
	
}
