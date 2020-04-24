package com.duckyshop.model;

import java.io.Serializable;

public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 517288895251866033L;
	
	private int		id;
	private String	username;
	private String 	hashpass;
	private String 	fullname;
	private String	salt;
	private String 	email;
	
	private Role	role;
	private Contact	contact;
	
	public User(int id, String username, String hashpass, String fullname, String salt, String email) {
		super();
		this.id = id;
		this.username = username;
		this.hashpass = hashpass;
		this.fullname = fullname;
		this.salt = salt;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashpass() {
		return hashpass;
	}

	public void setHashpass(String hashpass) {
		this.hashpass = hashpass;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	
	
}
