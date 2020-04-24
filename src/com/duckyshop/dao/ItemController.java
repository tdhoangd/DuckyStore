package com.duckyshop.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.duckyshop.model.Item;

public class ItemController {
	private Connection connection;

	public ItemController(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public ArrayList<Item> getItemsList() {
		ResultSet rs = null;
		ArrayList<Item> list = new ArrayList<>();
		
		String template = "SELECT * FROM ITEM ";
			
		try {
			Statement st = this.connection.createStatement();
			rs = st.executeQuery(template);
			
			while (rs.next()) {
				Item item = new Item();
				item.setItemId(rs.getInt("iid"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setPicPath(rs.getString("pic_path"));
				item.setStock(rs.getInt("stock"));
				item.setCost(rs.getDouble("cost"));
				item.setPrice(rs.getDouble("price"));
					
				list.add(item);
						
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Item> getItemsList(String keyword) {
		ResultSet rs = null;
		ArrayList<Item> list = new ArrayList<>();
		
		String template = "SELECT * FROM ITEM WHERE NAME LIKE '%" + StringUtil.fixSqlFieldValue(keyword) + "%'";
			
		try {
			Statement st = this.connection.createStatement();
			rs = st.executeQuery(template);
			
			while (rs.next()) {
				Item item = new Item();
				item.setItemId(rs.getInt("iid"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setPicPath(rs.getString("pic_path"));
				item.setStock(rs.getInt("stock"));
				item.setCost(rs.getDouble("cost"));
				item.setPrice(rs.getDouble("price"));
				
				list.add(item);
						
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public Item getItemRecord(int id) {
		ResultSet rs = null;
		Item item = new Item();
		
		String template = "SELECT * FROM ITEM WHERE iid = " + id;
			
		try {
			Statement st = this.connection.createStatement();
			rs = st.executeQuery(template);
			
			if (rs.next()) {
				item.setItemId(rs.getInt("iid"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setPicPath(rs.getString("pic_path"));
				item.setStock(rs.getInt("stock"));
				item.setCost(rs.getDouble("cost"));
				item.setPrice(rs.getDouble("price"));
			}
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return item;
	}
	
	
}

