package com.duckyshop.model;

import java.io.Serializable;

public class CartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4433601387621612042L;

	private int itemId;
	private int quantity;
	private String name;	
	private double price;
	
	public CartItem() {
		super();
	}

	public CartItem(int itemId, int quantity, String name, double price) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
		this.name = name;
		this.price = price;
	}

	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
