package com.duckyshop.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.duckyshop.model.CartItem;
import com.duckyshop.model.Contact;
import com.duckyshop.model.Item;
import com.duckyshop.model.Order;
import com.duckyshop.model.User;

public interface ServiceInterface {

	public User addNewUser(String username, String password, String fullname, String email);
	public User	login(String username, String password);
	public User modifyUserInfo(User user, String newFullName, String newEmail);
	public boolean changePassword(User user, String newPassword);
	public Contact getContact(int uid);
	public Contact addContact(int uid, String fname, String mname, String lname, String email, String address,
			String city, String state, String zipcode, String country);
	public Contact updateContact(int cid, String fname, String mname, String lname, String email, String address,
			String city, String state, String zipcode, String country);
	
	public ArrayList<Item> getItems(String keyword);
	public Item getItem(int iid);
	
	public boolean checkout(int uid, int cid, HashMap<Integer, CartItem> cart);
	public ArrayList<Order> getOrders(int uid);
	public boolean cancelOrder(int uid, int oid);
}
