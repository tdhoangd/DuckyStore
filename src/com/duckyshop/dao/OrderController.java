package com.duckyshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.duckyshop.model.CartItem;
import com.duckyshop.model.Order;
import com.duckyshop.model.OrderDetail;
import com.duckyshop.model.Status;

public class OrderController {
	private Connection connection;

	public OrderController(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public int addOrder(int uid, int cid) {
		int oid = 0;
		String template = "INSERT INTO ORDER(uid, cid) VALUES (?, ?)";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, uid);
			ps.setInt(2, cid);
			
			int rc = ps.executeUpdate();
						
			if (rc > 0) {
				ResultSet generatedKeys = ps.getGeneratedKeys();
				if (generatedKeys.next()) 
					oid = generatedKeys.getInt(1);
			}
				
						
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return oid;
	}
	
	public void addOrderDetail(int oid, HashMap<Integer, CartItem> map) {
		if (oid <= 0) return; 
		
		int batchSize = 1000;
		String template = "INSERT INTO ORDER_DETAIL(oid, iid, remaining) values (?, ?, ?)";
				
		try {
			PreparedStatement ps = this.connection.prepareStatement(template);
			int count = 0;
			for (Map.Entry<Integer, CartItem> entry: map.entrySet()) {
				
				ps.setInt(1, oid);
				ps.setInt(2, entry.getKey());
				ps.setInt(3, entry.getValue().getQuantity());
				ps.addBatch();
				if (++count % batchSize == 0) {
					ps.executeBatch();
				}
			}
			ps.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<OrderDetail> getOrderDetail(int oid) {
		ArrayList<OrderDetail> list = new ArrayList<>();
		
		String sql = "SELECT ORDER_DETAIL.*, ITEM.NAME "
				+ "FROM ORDER_DETAIL, ITEM "
				+ "WHERE ORDER_DETAIL.iid = ITEM.iid and "
				+ "ORDER_DETAIL.oid = ?";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setInt(1, oid);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				OrderDetail od = new OrderDetail();
				od.setItemId(rs.getInt("iid"));
				od.setItemName(rs.getString("name"));
				od.setDelivered(rs.getInt("delivered"));
				od.setRemaining(rs.getInt("remaining"));
				od.setQty(od.getDelivered() + od.getRemaining());

				list.add(od);		
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public ArrayList<Order> getOrders(int uid) {
		ArrayList<Order> list = new ArrayList<>();
		
		try {
			String sql = "SELECT order.oid, order.cid, order.sid, varchar_format(order_date, "
					+ "'YYYY-MM-DD') as order_date, STATUS.name "
					+ "FROM ORDER, STATUS "
					+ "WHERE ORDER.sid = STATUS.sid and ORDER.uid = ? "
					+ "order by order_date desc";
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setInt(1, uid);
		
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Status s = new Status();
				s.setId(rs.getInt("sid"));
				s.setName(rs.getString("name"));
				
				Order o = new Order();
				o.setOrderId(rs.getInt("oid"));
				o.setContactId(rs.getInt("cid"));
				o.setOrderDate(rs.getString("order_date"));
				o.setStatus(s);
				
				list.add(o);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}
	
	public int deleteOrder(int uid, int oid) {
		int rc = 0;
		String sql = "DELETE FROM ORDER "
				+ "WHERE uid = ? and oid = ?";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setInt(1, uid);
			ps.setInt(2, oid);
			
			rc = ps.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rc;
	}

	public int getOrderStatus(int oid) {
		int status = -1;
		String sql = "SELECT sid FROM ORDER WHERE oid = ? ";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(sql);
			ps.setInt(1, oid);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				status = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return status;
	}
	
}
