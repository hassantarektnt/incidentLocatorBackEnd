package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

public class ReportModel {

	private int userId;
	private int reportId;
	private String description;
	private String type;
	private String imageUrl;
	private double longtuide;
	private double latitude;
	private int priority;
	private int solved;

	public int getSolved() {
		return solved;
	}

	public void setSolved(int solved) {
		this.solved = solved;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getLongtuide() {
		return longtuide;
	}

	public void setLongtuide(double longtuide) {
		this.longtuide = longtuide;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public static ReportModel addReport(int userId, String description,
			String type, String imageUrl, int priority, double longtuide,
			double latitude, int solved) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into `report` (`user_id`,`description` , `type` ,`imageUrl`, `priority` ,`solved`, `latitude`,`longitude` )  VALUES  (?, ?, ?, ?, ?, ?, ?, ?) ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, userId);
		stmt.setString(2, description);
		stmt.setString(3, type);
		stmt.setString(4, imageUrl);
		stmt.setInt(5, priority);
		stmt.setInt(6, solved);
		stmt.setDouble(7, latitude);
		stmt.setDouble(8, longtuide);
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		ReportModel report = new ReportModel();
		if (rs.next()) {

			report.reportId = rs.getInt(1);
			report.userId = userId;
			report.description = description;
			report.type = type;
			report.imageUrl = imageUrl;
			report.priority = priority;
			report.latitude = latitude;
			report.longtuide = longtuide;
			report.solved = solved;
		}
		String descPlusId;
		descPlusId = "Description:" + report.getDescription() + ".Report#:"
				+ report.getReportId();
		String updateQuery = "Update `report` Set `DescID` = ? Where `report_id` = ?";
		Connection connection = DBConnection.getActiveConnection();
		PreparedStatement updatestmt;
		updatestmt = connection.prepareStatement(updateQuery);
		updatestmt.setString(1, descPlusId);
		updatestmt.setInt(2, report.getReportId());
		int returnRows = updatestmt.executeUpdate();
		if (returnRows != 0) {
			return report;
		}
		return null;

	}

	public static ArrayList<ReportModel> getAllReports() throws SQLException {
		ArrayList<ReportModel> returnData = new ArrayList<ReportModel>();
		Connection conn = DBConnection.getActiveConnection();
		String Sql = "Select * From `report`";
		PreparedStatement stmt = conn.prepareStatement(Sql);
		ResultSet st = stmt.executeQuery();
		if (st != null) {
			while (st.next()) {
				ReportModel iterateObject = new ReportModel();
				iterateObject.setUserId(st.getInt("user_id"));
				iterateObject.setType(st.getString("type"));
				iterateObject.setDescription(st.getString("description"));
				iterateObject.setImageUrl(st.getString("imageUrl"));
				iterateObject.setReportId(st.getInt("report_id"));
				iterateObject.setPriority(st.getInt("priority"));
				iterateObject.setLongtuide(st.getDouble("latitude"));
				iterateObject.setLatitude(st.getDouble("longitude"));
				iterateObject.setSolved(st.getInt("solved"));
				returnData.add(iterateObject);
			}
			return returnData;
		}
		return null;

	}

	public static boolean increasePriority(int reportId) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String Sql = "Select * From `report` Where `report_id` = ?";
		PreparedStatement stmt = conn.prepareStatement(Sql);
		stmt.setInt(1, reportId);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			String updateSql = "Update `report` Set `priority` = ? Where `report_id` = ?";
			stmt = conn.prepareStatement(updateSql);
			stmt.setInt(1, reportId);
			int status = stmt.executeUpdate();
			if (status != 0) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}
