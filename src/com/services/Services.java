package com.services;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.models.FeedbackModel;
import com.models.ReportModel;
import com.models.UserModel;

@Path("/")
public class Services {

	@SuppressWarnings("unchecked")
	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email,
			@FormParam("password") String password,
			@FormParam("type") String type,
			@FormParam("phoneNumber") String phoneNumber,
			@FormParam("verified") int verified) throws SQLException {
		UserModel user = UserModel.addNewUser(name, email, password,
				phoneNumber, verified, type);
		if (user != null) {
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("password", user.getPass());
			json.put("type", user.getType());
			json.put("phoneNumber", user.getPhoneNumber());
			json.put("verified", user.getVerified());
			json.put("operation", "done");
			return json.toJSONString();
		} else {
			JSONObject json = new JSONObject();
			json.put("operation", "failed");
			return json.toString();
		}
		// return null;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("password") String pass) {

		UserModel user = UserModel.login(email, pass);
		if (user != null) {
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("password", user.getPass());
			json.put("type", user.getType());
			json.put("phoneNumber", user.getPhoneNumber());
			json.put("verified", user.getVerified());
			json.put("operation", "done");
			return json.toJSONString();
		}
		JSONObject fail = new JSONObject();
		fail.put("operation", "failed");
		return fail.toString();
	}

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public String test() {

		return "Test test";
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/addreport")
	@Produces(MediaType.TEXT_PLAIN)
	public String addReport(@FormParam("userid") int Id,
			@FormParam("description") String description,
			@FormParam("type") String type,
			@FormParam("priority") int priority,
			@FormParam("imageurl") String url,
			@FormParam("lon") double longtuide,
			@FormParam("lat") double latitude, @FormParam("solved") int solved)
			throws SQLException {
		UserModel user = new UserModel();
		user.setId(Id);
		ReportModel report = new ReportModel();

		report = ReportModel.addReport(Id, description, type, url, priority,
				longtuide, latitude, solved);
		if (report != null) {
			JSONObject json = new JSONObject();
			json.put("user_id", report.getUserId());
			json.put("description", report.getDescription());
			json.put("report_id", report.getReportId());
			json.put("type", report.getType());
			json.put("imageUrl", report.getImageUrl());
			json.put("priority", report.getPriority());
			json.put("lon", report.getLongtuide());
			json.put("lat", report.getLatitude());
			json.put("url", report.getImageUrl());
			json.put("operation", "done");
			return json.toString();
		} else {
			JSONObject json = new JSONObject();
			json.put("operation", "failed");

			return json.toString();
		}

	}

	@SuppressWarnings("unchecked")
	@GET
	@Path("/getallreports")
	@Produces(MediaType.TEXT_PLAIN)
	public String getAllReports() throws SQLException {
		ArrayList<ReportModel> data = new ArrayList<>();
		data = ReportModel.getAllReports();
		if (data != null) {
			JSONArray returnarray = new JSONArray();

			for (int i = 0; i < data.size(); i++) {
				ReportModel report = new ReportModel();
				JSONObject json = new JSONObject();
				report = data.get(i);
				json.put("user_id", report.getUserId());
				json.put("description", report.getDescription());
				json.put("report_id", report.getReportId());
				json.put("type", report.getType());
				json.put("imageUrl", report.getImageUrl());
				json.put("priority", report.getPriority());
				json.put("lon", report.getLongtuide());
				json.put("lat", report.getLatitude());
				returnarray.add(json);
			}
			return returnarray.toString();
		} else {
			JSONObject o = new JSONObject();
			o.put("operation", "failed");
			return o.toString();
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/addfeedback")
	@Produces(MediaType.TEXT_PLAIN)
	public String addFeedback(@FormParam("userid") int Id,
			@FormParam("reportid") int reportId,
			@FormParam("description") String description) throws SQLException {
		FeedbackModel feedback = FeedbackModel.addFeedback(reportId,
				description);
		if (feedback != null) {
			JSONObject data = new JSONObject();
			data.put("description", feedback.getDescription());
			data.put("feedbackid", feedback.getFeedbackId());
			data.put("reportid", feedback.getReportId());
			data.put("operation", "done");

			return data.toString();

		} else {
			JSONObject fail = new JSONObject();
			fail.put("operarion", "failed");
			return fail.toString();
		}
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/increasepriority")
	@Produces(MediaType.TEXT_PLAIN)
	public String increasePriority(@FormParam("reportid") int reportId)
			throws SQLException {
		boolean updateStatus = ReportModel.increasePriority(reportId);
		JSONObject returnData = new JSONObject();
		if (updateStatus) {

			returnData.put("operation", "done");
			return returnData.toString();
		} else {
			returnData.put("operation", "failed");
			return returnData.toString();
		}
	}
}
