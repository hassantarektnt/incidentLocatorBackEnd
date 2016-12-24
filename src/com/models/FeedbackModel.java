package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class FeedbackModel {
 private int feedbackId ;
 private int reportId ; 
 private String description ;
 
 
public int getFeedbackId() {
	return feedbackId;
}
public void setFeedbackId(int feedbackId) {
	this.feedbackId = feedbackId;
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
public static FeedbackModel addFeedback(int reportId , String description) throws SQLException{
	Connection conn = DBConnection.getActiveConnection();
	String Sql = "Insert into `feedback` (`report_id`,`description`) VALUES  (?,?)";
	PreparedStatement stmt;
	stmt = conn.prepareStatement(Sql , Statement.RETURN_GENERATED_KEYS);
	stmt.setInt(1 , reportId);
	stmt.setString(2, description);
	System.out.println(stmt.toString());
	stmt.executeUpdate();
	ResultSet rs= stmt.getGeneratedKeys();
	System.out.println("Here");
	if(rs.next()){
		FeedbackModel data = new FeedbackModel();
		data.feedbackId = rs.getInt(1);
		data.description = description;
		data.reportId = reportId;
		return data;
	}
	return null;
	
}
//public static boolean checkDuplicatedFeedback (int reportId , int userId){
	//String Sql = "Select * From `feedback` Where reportid = ?";
	
//}
 
}
