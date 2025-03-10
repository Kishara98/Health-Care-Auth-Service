package com.auth.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.auth.database.DBConnection;
import com.auth.model.Doctor;

public class DoctorService {

	/* User type - Doctor */

//Insert doctor
	public String insertDoctor(String type, String email, String password, String contactNo, String firstName,
			String lastName, String NIC, String sex, String specialization) {

		String output = "";
		try {
			Connection con = DBConnection.connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}

			// create a prepared statement
			String query = " insert into doctor (`id`,`type`,`email`,`password`,`contactNo`,`firstName`,`lastName`,`NIC`,`sex`,`specialization`)"
					+ " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, type);
			preparedStmt.setString(3, email);
			preparedStmt.setString(4, password);
			preparedStmt.setString(5, contactNo);
			preparedStmt.setString(6, firstName);
			preparedStmt.setString(7, lastName);
			preparedStmt.setString(8, NIC);
			preparedStmt.setString(9, sex);
			preparedStmt.setString(10, specialization);

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Inserted successfully";

		} catch (Exception e) {
			output = "Error while inserting the Doctor.";
			System.err.println(e.getMessage());

		}
		return output;
	}

//Read doctor list	
	public String readDoctor() {
		String output = "";
		try {
			Connection con = DBConnection.connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Doctor ID</th><th>User type</th><th>Doctor Email</th><th>Password</th><th>Contact no"
					+ "</th><th>First name</th><th>Last name</th><th>NIC</th><th>Sex</th><th>Specialization</th><th>Update</th><th>Remove</th></tr>";

			String query = "select * from doctor";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// iterate through the rows in the result set
			while (rs.next()) {
				String ID = Integer.toString(rs.getInt("ID"));
				String type = rs.getString("type");
				String email = rs.getString("email");
				String password = rs.getString("password");
				String contactNo = rs.getString("contactNo");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String NIC = rs.getString("NIC");
				String sex = rs.getString("sex");
				String specialization = rs.getString("specialization");

				// Add into the html table
				output += "<tr><td>" + ID + "</td>";
				output += "<td>" + type + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + password + "</td>";
				output += "<td>" + contactNo + "</td>";
				output += "<td>" + firstName + "</td>";
				output += "<td>" + lastName + "</td>";
				output += "<td>" + NIC + "</td>";
				output += "<td>" + sex + "</td>";
				output += "<td>" + specialization + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\""
						+ " value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"doctor.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"" + " class=\"btn btn-danger\">"
						+ "<input name=\"ID\" type=\"hidden\" value=\"" + ID + "\">" + "</form></td></tr>";
			}
			con.close();

			// Complete the html table
			output += "</table>";

		} catch (Exception e) {
			output = "Error while reading the doctor.";
			System.err.println(e.getMessage());
		}

		return output;
	}

//Doctor update
	public String updateDoctor(String ID, String contactNo) {
		String output = "";
		try {
			Connection con = DBConnection.connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE doctor SET contactNo=? " + "WHERE ID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values

			preparedStmt.setString(1, contactNo);
			preparedStmt.setInt(2, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Updated successfully";

		} catch (Exception e) {
			output = "Error while updating the doctor.";
			System.err.println(e.getMessage());
		}

		return output;
	}

//Delete existing doctor
	public String deleteDoctor(String ID) {
		String output = "";
		try {
			Connection con = DBConnection.connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from doctor " + "where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(ID));

			// execute the statement
			preparedStmt.execute();
			con.close();
			output = "Deleted successfully";

		} catch (Exception e) {
			output = "Error while deleting the doctor.";
			System.err.println(e.getMessage());
		}

		return output;
	}

//Retrieve  single doctor
	public static Doctor getDoctorDetails(String id) {
		Doctor doctor = null;
		try {

			Connection con = DBConnection.connect();

			String getSql = "SELECT * FROM doctor WHERE id = ? ";

			PreparedStatement ps_getDoctorDetails = con.prepareStatement(getSql);
			ps_getDoctorDetails.setInt(1, Integer.parseInt(id));

			ResultSet rs = ps_getDoctorDetails.executeQuery();

			while (rs.next()) {

				doctor = new Doctor(Integer.parseInt(id), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
						rs.getString(10));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return doctor;
	}

}
