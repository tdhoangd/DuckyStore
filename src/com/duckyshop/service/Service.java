package com.duckyshop.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.duckyshop.dao.ConnectionPool;
import com.duckyshop.dao.ContactController;
import com.duckyshop.dao.ItemController;
import com.duckyshop.dao.OrderController;
import com.duckyshop.dao.PasswordUtil;
import com.duckyshop.dao.UserController;
import com.duckyshop.model.*;

public class Service implements ServiceInterface {
	
	@Override
	public User addNewUser(String username, String password, String fullname, String email) {
		// add user and role 
		
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		String salt = PasswordUtil.generateSalt();
		String hashpass = PasswordUtil.generateHashPassword(password, salt);
		
		UserController controller = new UserController(connection);
		User user = controller.addUserAndRole(username, hashpass, fullname, salt, email);
			
		pool.freeConnection(connection);
		return user;
	}

	@Override
	public User login(String username, String password) {
		// return null if username and password are invalid
		// otherwise return a user object
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		User user = null;
		
		if (connection != null) {
			UserController uc = new UserController(connection);
			user = uc.getUserRecord(username);
			
			if (user == null || !PasswordUtil.verifyPassword(password, user.getHashpass(), user.getSalt())) {
				return null;
			} else {
				// set role, contact  to user
				ContactController cc = new ContactController(connection);
				
				Contact contact = cc.getUserContact(user.getId());
				if (contact != null) {
					user.setContact(contact);
				} 
				
				Role role = uc.getUserRole(user.getId());
				user.setRole(role);
								
				return user;
			}
		}
			
		pool.freeConnection(connection);
		return null;
	}

	@Override
	public User modifyUserInfo(User user, String newFullName, String newEmail) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		UserController uc = new UserController(connection);
		int rc = uc.editUser(user.getId(), newFullName, newEmail);
		
		if (rc > 0) {
			user.setFullname(newFullName);
			user.setEmail(newEmail);
		}
		
		pool.freeConnection(connection);
		return user;
	}

	@Override
	public boolean changePassword(User user, String newPassword) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		String newsalt = PasswordUtil.generateSalt();
		String newHash = PasswordUtil.generateHashPassword(newPassword, newsalt);
		
		UserController uc = new UserController(connection);
		int rc = uc.changePassword(user.getId(), user.getHashpass(), newHash, newsalt);
		
		pool.freeConnection(connection);
		
		if (rc > 0) {
			user.setSalt(newsalt);
			user.setHashpass(newHash);
			return true;
		}
		
		return false;
	}

	@Override
	public Contact getContact(int uid) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		ContactController cc = new ContactController(connection);
		Contact contact = cc.getUserContact(uid);
		
		pool.freeConnection(connection);
		return contact;
	}

	@Override
	public Contact addContact(int uid, String fname, String mname, String lname, String email, String address,
			String city, String state, String zipcode, String country) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		ContactController cc = new ContactController(connection);
		Contact contact  = cc.addContact(fname, lname, mname, email, address, city, state, zipcode, country);
		
		if (contact == null) return null;
		
		UserController uc = new UserController(connection);
		uc.addUserContact(uid, contact.getId());
		
		pool.freeConnection(connection);
		return contact;
	}

	@Override
	public Contact updateContact(int cid, String fname, String mname, String lname, String email, String address,
			String city, String state, String zipcode, String country) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		ContactController cc = new ContactController(connection);
		Contact contact = cc.updateContact(cid, fname, mname, lname, email, address, city, state, zipcode, country);
		
		pool.freeConnection(connection);
		return contact;
	}

	@Override
	public ArrayList<Item> getItems(String keyword) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		ItemController ic = new ItemController(connection);
		
		ArrayList<Item> list;
		
		if (keyword == null || keyword.length() == 0) {
			list = ic.getItemsList();
		} else {
			list = ic.getItemsList(keyword);
		}
		
		pool.freeConnection(connection);
		return list;
	}
	
	@Override
	public Item getItem(int iid) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		ItemController ic = new ItemController(connection);
		Item item = ic.getItemRecord(iid);
		
		pool.freeConnection(connection);
		return item;
	}

	@Override
	public boolean checkout(int uid, int cid, HashMap<Integer, CartItem> cart) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
				
		OrderController oc = new OrderController(connection);
		int oid = oc.addOrder(uid, cid);
		
		if (oid < 1) return false;
		oc.addOrderDetail(oid, cart);
		
		pool.freeConnection(connection);
		
		return true;
	}

	@Override
	public ArrayList<Order> getOrders(int uid) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();
		
		OrderController oc = new OrderController(connection);
		ArrayList<Order> list = oc.getOrders(uid);
				
		for (int i=0; i < list.size(); i++) {
			Order order = list.get(i);
			order.setItems(oc.getOrderDetail(order.getOrderId()));
			list.set(i, order);
		}
				
		pool.freeConnection(connection);
		return list;
	}

	@Override
	public boolean cancelOrder(int uid, int oid) {
		ConnectionPool pool = ConnectionPool.getInstance("jdbc/t0hoan01");
		Connection connection = pool.getConnection();

		OrderController oc = new OrderController(connection);
		
		int status = oc.getOrderStatus(oid);
		if (status != 1)   // cant cancel order that completed or inprogress. 
			return false; 
		
		int rc = oc.deleteOrder(uid, oid);
		pool.freeConnection(connection);

		return (rc>0) ? true : false;
	}
	
}
