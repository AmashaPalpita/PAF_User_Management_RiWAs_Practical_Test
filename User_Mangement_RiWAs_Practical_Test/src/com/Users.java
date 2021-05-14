package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Users {
	public Connection connect() {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/paf_db","root","1234");
			
			//for testing
			//System.out.println("Successfully Connected");
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not Connected");
		}
		
		return con;
	}
	
	public String insertUsers(String name, String email, String userType, String pw) { 
		String output = ""; 
		
		try { 
			Connection con = connect(); 
			
			if (con == null) { 
				return "Error while connecting to the database for inserting"; 
			} 
			
			// create a prepared statement
			String query = " insert into users (`userID`,`name`,`email`,`userType`,`pw`)"+ " values (?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
			// binding values
			preparedStmt.setInt(1, 0); 
			preparedStmt.setString(2, name); 
			preparedStmt.setString(3, email); 
			preparedStmt.setString(4,userType); 
			preparedStmt.setString(5, pw); 
	
			//execute the statement
			preparedStmt.execute(); 
			con.close(); 
	 
			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}"; 
			
		} catch (Exception e) { 
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the user.\"}"; 
			System.err.println(e.getMessage()); 
		} 
	
		return output; 
	}
	
	public String readUsers() {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for reading";
			}
			
			//Prepare the html table to be displayed
			output = "<table class='table table-dark table-striped'> <tr> <th>User Name</th> <th>User Email</th> <th>User Type</th> <th>User Password</th><th>Update</th> <th>Remove</th> </tr>";
			
			String query = "select * from users";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//iterate through the rows in the result set
			while (rs.next()) {
				String userID = Integer.toString(rs.getInt("userID"));
				String name = rs.getString("name");
				String email = rs.getString("email");
				String userType = rs.getString("userType");
				String pw = rs.getString("pw");
				
				//Add a row into the html table
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + userID
				+ "'>" + name + "</td>"; 
				//output += "<tr><td>" + itemCode + "</td>";
				output += "<td>" + email + "</td>";
				output += "<td>" + userType + "</td>";
				output += "<td>" + pw + "</td>";
				
				//Buttons
				output += "<td> <input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-warning' data-itemid='" + userID + "''></td>"
						+ "<td> <input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + userID + "''> "
						+ "<input name='hidItemIDDelete' type='hidden' value='" + userID + "'>"
						+ "</td></tr>";
			}
			
			con.close();
			
			//Complete the html table
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the users.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateUsers(String userID, String name, String email, String userType, String pw ) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for updating" ;
			}
			
			//Create a prepared statement
			String query = "update users set name=?, email=?, userType=?, pw=? where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, email);
			preparedStmt.setString(3, userType);
			preparedStmt.setString(4, pw);
			preparedStmt.setInt(5, Integer.parseInt(userID));
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newUsers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUsers + "\"}"; 

		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the user.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	public String deleteUsers(String userID) {
		String output = "";
		
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for deleting";
			}
			
			//Create a prepared statement
			String query = "delete from users where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			//binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newusers = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newusers + "\"}"; 
			
		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while deleting the user.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	}
}
