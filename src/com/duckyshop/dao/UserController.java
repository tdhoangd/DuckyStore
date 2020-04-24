package com.duckyshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.duckyshop.model.Role;
import com.duckyshop.model.User;


public class UserController {
	
	private Connection connection = null;
	private static int DEFAULT_ROLE_ID = 4;	// customer role
	
	public  UserController(Connection connection) {
		this.connection = connection;
	}
	
	public int addUser(String username, String hashpass, String fullname, String salt, String email) {
		int rc = 0;
		String template = "INSERT INTO USER(username, hashpass, fullname, salt, email) values(?, ?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = this.connection.prepareStatement(template);
			ps.setString(1, username);
			ps.setString(2, hashpass);
			ps.setString(3, fullname);
			ps.setString(4, salt);
			ps.setString(5, email);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return (rc);
	}
	
	public int addUserRole(int uid) {
		// only allow to new role as customer
		int rc = 0;
		String template = "INSERT INTO USER_ROLE(uid, role) values (?, ?)";
		try {
			PreparedStatement ps = this.connection.prepareStatement(template);
			ps.setInt(1, uid);
			ps.setInt(2, DEFAULT_ROLE_ID);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rc;
	}
	
	public User addUserAndRole(String username, String hashpass, String fullname, String salt, String email) {		
		if (addUser(username, hashpass, fullname, salt, email) != 0) {
			User user = getUserRecord(username);
			if (user != null) {
				addUserRole(user.getId());
				return user;
			}
		}
		return null;
	}
	
	public int addUserContact(int uid, int cid) {
		int rc = 0;
		String template = "INSERT INTO USER_CONTACT(uid, cid) values (?, ?)";
		try {
			PreparedStatement ps = this.connection.prepareStatement(template);
			ps.setInt(1, uid);
			ps.setInt(2, cid);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rc;
	}
		
	public int editUser(int uid, String fullname, String email) {
		// Edit user info like full name or/and email
		int rc = 0;
		String template = "UPDATE USER SET fullname = ?, email = ? WHERE uid = ?"; 
		
		try {
			PreparedStatement ps = connection.prepareStatement(template);
			ps.setString(1, fullname);
			ps.setString(2, email);
			ps.setInt(3, uid);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rc;
	}
	
	public int deleteUser(int uid, String username, String hashpass) {
		int rc = 0;
		String template = "DELETE FROM USER WHERE UID = ? and USERNAME = ? and HASHPASS = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(template);
			ps.setInt(1, uid);
			ps.setString(2, username);
			ps.setString(3, hashpass);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rc;
	}
	
	public int changePassword(int uid, String oldHashPass, String newHashPass, String newSalt) {
		int rc = 0;
		String template = "UPDATE USER SET HASHPASS = ?, SALT = ? where UID = ? and HASHPASS = ?";
		
		try {
			PreparedStatement ps = connection.prepareStatement(template);
			ps.setString(1, newHashPass);
			ps.setString(2, newSalt);
			ps.setInt(3, uid);
			ps.setString(4, oldHashPass);
			rc = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rc;
	}
	
//	public ResultSet getUserRecord(String username) {
//		ResultSet rs = null;
//		String template = "SELECT * FROM USER WHERE USERNAME = '" + username + "'";
//		try {
//			Statement st = connection.createStatement();
//			rs = st.executeQuery(template);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return rs;
//	}
	
	public User getUserRecord(String username) {
		User user = null;
		String template = "SELECT * FROM USER WHERE USERNAME = '" + username + "'";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(template);
			
			if (rs.next()) {
				user = new User(rs.getInt("uid"),
								rs.getString("username"),
								rs.getString("hashpass"),
								rs.getString("fullname"),
								rs.getString("salt"),
								rs.getString("email"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return user;
	}
	

	
	public ResultSet getUsersList() {
		ResultSet rs = null;
		String template = "SELECT * FROM USER"; 
		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(template);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public ResultSet getUsersList(String keyword) {
		ResultSet rs = null;
		String template = "SELECT * FROM USER WHERE fullname LIKE '%" + StringUtil.fixSqlFieldValue(keyword) + "%'"; 
		try {
			Statement st = connection.createStatement();
			rs = st.executeQuery(template);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rs;
	}
	
	public boolean searchUser(String username) {
		boolean ret = false;
		String template = "SELECT * FROM USER WHERE USERNAME = '" + username + "'";
		try {
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(template);
			
			if (rs.next()) {
				ret = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public boolean verifyUser(String user, String password) {
		boolean result = false;
		
		String query = "SELECT * FROM USER WHERE USERNAME='" + user + "'";
		
		try {
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery(query);
			
			if (rs.next()) {
				result = true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public Role getUserRole(int uid) {
		Role role = null;	// there is no role 0 
		
		String template = "SELECT ROLE.ROLE, ROLE.NAME "
				+ "FROM ROLE, USER_ROLE "
				+ "WHERE ROLE.ROLE = USER_ROLE.ROLE and USER_ROLE.UID = "
				+ uid;
		
		try {
			Statement st = this.connection.createStatement();
			ResultSet rs = st.executeQuery(template);
			
			if (rs.next()) {
				role = new Role();
				role.setRole(rs.getInt("ROLE"));
				role.setRoleName(rs.getString("NAME"));

			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
					
		return role;
	}
	
	
}
