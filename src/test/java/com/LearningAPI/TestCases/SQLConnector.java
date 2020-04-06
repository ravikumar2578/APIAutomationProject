package com.LearningAPI.TestCases;

import java.io.Reader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SQLConnector {
	static boolean isUserRegistered;
	static boolean Forgotpassword;

	/*
	 * public static void main(String[] args) throws ClassNotFoundException,
	 * SQLException { boolean b=connectionsetup("8527654152", "test@gmail.com");
	 * System.out.println(b);
	 * 
	 * }
	 */

	public static boolean connectionsetup(String mobile, String email) throws ClassNotFoundException, SQLException {
		// Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
		// String dbUrl = "jdbc:mysql://10.0.3.99/website_ver2";

		/*
		 * String dbUrl = "jdbc:mysql://10.0.20.107/phpmyadmin";
		 */

		// test-automation server
		// boolean status = true;

		// mysql://localhost:3306/dbname
		String dbUrl = "jdbc:mysql://10.0.20.236/website_ver2_test";

		// Database Username

		String username = "automation"; // automation server
		String password = "AutoMation#123!!!"; // automation server

		// Query to Execute
		String query = "SELECT * from user where email=" + "'" + email + "'" + " or mobile=" + "'" + "+91-" + mobile
				+ "'";
		System.out.println(query);

		// Load mysql jdbc driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		// Create Connection to DB
		Connection con = DriverManager.getConnection(dbUrl, username, password);

		// Create Statement Object
		Statement stmt = con.createStatement();

		// Execute the SQL Query. Store results in ResultSet

		ResultSet rs = stmt.executeQuery(query);

		/*
		 * int count = rs.getRow();
		 * 
		 * System.out.println(count);
		 */
		System.out.println(rs);
		while (rs.next()) {
			int count = rs.getRow();

			System.out.println(count);
			count++;
			// int count = rs.getInt("count");

			if (count >= 1) {
				// boolean isUserRegistered =true;
				// System.out.println("user already exists.");
				isUserRegistered = true;
			} else {
				System.out.println("New user.");
				isUserRegistered = false;

			}

		}

		// closing DB Connection
		con.close();
		return isUserRegistered;

	}

//	***************************************************************************************************************************************

	/*
	 * public static boolean connectionsetup1(int mobile_number, int sms_text,
	 * String newpwd) throws ClassNotFoundException, SQLException {
	 * 
	 * String dbUrl = "jdbc:mysql://10.0.20.236/website_ver2_test"; String username
	 * = "automation"; String password = "AutoMation#123!!!";
	 * 
	 * String query = "SELECT * from t_sms_log"; System.out.println(query);
	 * Class.forName("com.mysql.cj.jdbc.Driver"); Connection con =
	 * DriverManager.getConnection(dbUrl, username, password); Statement stmt =
	 * con.createStatement();
	 * 
	 * ResultSet rs = stmt.executeQuery(query); rs.absolute(-1); int count =
	 * rs.getMetaData().getColumnCount(); System.out.println("columncount >" +
	 * count); for (int i = 1; i <= count; i++) { String ColumnNAME =
	 * rs.getMetaData().getColumnName(i); String ColName = i + " " + "ColName >" +
	 * ColumnNAME;
	 * 
	 * System.out.println(ColName); String Columnvalue = rs.getString(i);
	 * System.out.println(ColumnNAME + ":" + " " + Columnvalue); }
	 * 
	 * String OTPMessage = rs.getString(5); String MobileNumber = rs.getString(3);
	 * 
	 *//**
		 * Scanner input = new Scanner(System.in); System.out.print("Enter Mobile Number
		 * > "); String inputString = input.nextLine(); System.out.print("You entered :
		 * "); System.out.println(inputString);
		 * 
		 *//*
			 * 
			 * System.out.println("mobile no. > " + MobileNumber);
			 * System.out.println("SMS Text >" + OTPMessage); int OTP =
			 * Integer.parseInt(OTPMessage.replaceAll("\\D", ""));
			 * 
			 * System.out.println("Required OTP for Registration > " + OTP);
			 * 
			 * 
			 * if (inputString==MobileNumber){ System.out.println(OTPMessage); int OTP =
			 * Integer.parseInt(OTPMessage.replaceAll("\\D", ""));
			 * 
			 * 
			 * System.out.println("Required OTP for Registration : " + OTP);
			 * 
			 * } else { System.out.println(" mobile comparision is failed"); }
			 * 
			 * 
			 * rs.absolute(-2);
			 * 
			 * 
			 * String OTP1= rs.getString(5); System.out.println(OTP1); for(String
			 * w:OTP1.split("\\s",0)){ li.add(w); //System.out.println(w); } int
			 * Arraysize=li.size(); //System.out.println(Arraysize); String
			 * otpnumber=(String) li.get(4); System.out.println("OTP FOR REGISTRATION :" +
			 * otpnumber);
			 * 
			 * 
			 * 
			 * while (rs.next()){ //while (rs.absolute(3)){ String myName = rs.getString(1);
			 * String myAge = rs.getString(2); System. out.println(myName+"  "+myAge);
			 * 
			 * 
			 * String OTP= rs.getString(5);
			 * 
			 * System.out.println(OTP); }
			 * 
			 * 
			 * // closing DB Connection con.close(); return Forgotpassword;
			 */

}
