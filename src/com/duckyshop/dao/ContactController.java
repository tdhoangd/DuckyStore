package com.duckyshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.duckyshop.model.Contact;

public class ContactController {
	private Connection connection = null;

	public ContactController(Connection connection) {
		super();
		this.connection = connection;
	}
	
	public Contact getUserContact(int uid) {
		// get contact that associates with a user
		ResultSet rs = null;
		Contact contact = null;
		String template = "SELECT CONTACT.* "
				+ "FROM CONTACT, USER_CONTACT "
				+ "WHERE CONTACT.CID = USER_CONTACT.CID and USER_CONTACT.UID = "
				+ uid;
		
		try {
			Statement st = this.connection.createStatement();
			rs = st.executeQuery(template);
			
			if (rs.next()) {
				contact = new Contact();
				contact.setId(rs.getInt("cid"));
				contact.setfName(rs.getString("fname"));
				contact.setlName(rs.getString("lname"));
				contact.setmName(rs.getString("mname"));
				contact.setEmail(rs.getString("email"));
				contact.setAddress(rs.getString("address"));		  
				contact.setCity(rs.getString("city"));
				contact.setState(rs.getString("state"));
				contact.setZipcode(rs.getString("zipcode"));
				contact.setCountry(rs.getString("country"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return contact;
	}
	
	public Contact addContact(String fname, String lname, String mname, String email, String address, String city,
			String state, String zipcode, String country) {
		Contact contact = null;
		int rc = 0;
		String template = "INSERT INTO CONTACT(fname, lname, mname, email, address, city, state, zipcode, country) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement ps = this.connection.prepareStatement(template, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, fname);
			ps.setString(2, lname);
			ps.setString(3, mname);
			ps.setString(4, email);
			ps.setString(5, address);
			ps.setString(6, city);
			ps.setString(7, state);
			ps.setString(8, zipcode);
			ps.setString(9, country);
			
			rc = ps.executeUpdate();
			
			if (rc == 0) return null;
			
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				int cid = generatedKeys.getInt(1);
				contact = new Contact();
				contact.setId(cid);
				contact.setfName(fname);
				contact.setlName(lname);
				contact.setmName(mname);
				contact.setEmail(email);
				contact.setAddress(address);
				contact.setCity(city);
				contact.setState(state);
				contact.setZipcode(zipcode);
				contact.setCountry(country);
			
			} 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return contact;
	}
	
	public Contact updateContact(int cid, String fname, String mname, String lname, String email, String address,
			String city, String state, String zipcode, String country) {
		int rc = 0;
		String template = "UPDATE CONTACT SET fname = ?, mname = ?, lname = ?, email = ?, "
				+ "address = ?, city = ?, state = ?, zipcode = ?, country = ? "
				+ "WHERE cid = ? ";
		Contact contact = null;	
		try {
			PreparedStatement ps = connection.prepareStatement(template);
			ps.setString(1, fname);
			ps.setString(2, mname);
			ps.setString(3, lname);
			ps.setString(4, email);
			ps.setString(5, address);
			ps.setString(6, city);
			ps.setString(7, state);
			ps.setString(8, zipcode);
			ps.setString(9, country);
			ps.setInt(10, cid);
			
			rc = ps.executeUpdate();
			
			if (rc > 0) {
				contact = new Contact();
				contact.setId(cid);
				contact.setfName(fname);
				contact.setlName(lname);
				contact.setmName(mname);
				contact.setEmail(email);
				contact.setAddress(address);
				contact.setCity(city);
				contact.setState(state);
				contact.setZipcode(zipcode);
				contact.setCountry(country);
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return contact;
	}

	
}
