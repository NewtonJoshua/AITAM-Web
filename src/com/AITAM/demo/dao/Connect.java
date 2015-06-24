	package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class Connect {
		public static Connection connect() throws ClassNotFoundException, SQLException{
		String url = null;
 {		
	 if (SystemProperty.environment.value() ==
		      SystemProperty.Environment.Value.Production) {
		 		// Connecting from App Engine.
		  		// Load the class that provides the "jdbc:google:mysql://"
		  		// prefix.
		  Class.forName("com.mysql.jdbc.GoogleDriver");
		  url ="jdbc:google:mysql://{{Project ID}}:{{aitam}}/{{Database Name}}?user=root&password={{Password for root}}";
	 }
	 else{
		 		// Connecting from an external network.
		  Class.forName("com.mysql.jdbc.Driver");
		  url = "jdbc:mysql://{{IPv4 address}}.235:3306/{{Database Name}}?user={{User name}}&password={{Password for root}}";
	 }
		Connection conn = DriverManager.getConnection(url);
		return conn;
	}

}
}
