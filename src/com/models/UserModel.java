package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class UserModel {
	private String name;
	private String email;
	private String password;
	private Integer id;
	private Integer verified;
	private String type;
	private String phoneNumber;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Integer getVerified() {
		return verified;
	}

	public Integer isVerified() {
		return verified;
	}

	public void setVerified(Integer verified) {
		this.verified = verified;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPass() {
		return password;
	}

	public void setPass(String pass) {
		this.password = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static boolean checkUserAvailability(String email, String phoneNumber)
			throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Select * from account where `email` = ? OR `phoneNumber` = ?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setString(1, email);
		stmt.setString(2, phoneNumber);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {

			return false;
		}

		return true;
	}

	public static UserModel addNewUser(String name, String email,
			String password, String phoneNumber, int verified, String type)
			throws SQLException {
		if (!checkUserAvailability(email, phoneNumber)) {
			return null;

		}
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into `account` (`name`,`password`,`phoneNumber` , `email` , `verified`,`type`) VALUES  (?,?,?,?,?,?)";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, name);
		stmt.setString(2, password);
		stmt.setString(3, phoneNumber);
		stmt.setString(4, email);
		stmt.setInt(5, verified);
		stmt.setString(6, type);
		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();

		if (rs.next()) {
			UserModel user = new UserModel();

			user.id = rs.getInt(1);
			user.email = email;
			user.password = password;
			user.name = name;
			user.type = type;
			user.verified = verified;
			user.phoneNumber = phoneNumber;

			return user;
		}

		return null;

	}

	public static UserModel login(String email, String password) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from account where `email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(7);
				user.email = rs.getString("email");
				user.password = rs.getString("password");
				user.name = rs.getString("name");
				user.type = rs.getString("type");
				user.verified = rs.getInt("verified");
				user.phoneNumber = rs.getString("phoneNumber");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
/*
 * 
 * try { Connection conn = DBConnection.getActiveConnection(); String sql =
 * "Insert into users (`name`,`email`,`password`) VALUES  (?,?,?)";
 * PreparedStatement stmt; stmt = conn.prepareStatement(sql,
 * Statement.RETURN_GENERATED_KEYS); stmt.setString(1, name); stmt.setString(2,
 * email); stmt.setString(3, pass); stmt.executeUpdate(); ResultSet rs =
 * stmt.getGeneratedKeys(); if (rs.next()) { UserModel user = new UserModel();
 * user.id = rs.getInt(1); user.email = email; user.pass = pass; user.name =
 * name; user.lat = 0.0; user.lon = 0.0; return user; } return null; } catch
 * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
 */