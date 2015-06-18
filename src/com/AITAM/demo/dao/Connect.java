	package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class Connect {
	/**		public static Connection connect() throws ClassNotFoundException, SQLException{
		Connection conn=null;
		//System.out.println("connect");
		Class.forName ("oracle.jdbc.OracleDriver");
		 conn = DriverManager.getConnection("jdbc:oracle:thin:@10.24.0.101:1633/mbw_dev", "dev_joshua", "newton");
		 //System.out.println("Conneted");
		 return conn;
	}
**/	
/**	public static Connection connect() throws ClassNotFoundException, SQLException{
		Connection conn=null;
		Class.forName ("oracle.jdbc.OracleDriver");
		 conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "NewtonJoshua", "NewtonJoshua");
		 return conn;
		
	}
**/	

	
	public static Connection connect() throws ClassNotFoundException, SQLException{
		String url = null;
 {		
	 if (SystemProperty.environment.value() ==
		      SystemProperty.Environment.Value.Production) {
		 		// Connecting from App Engine.
		  		// Load the class that provides the "jdbc:google:mysql://"
		  		// prefix.
		  Class.forName("com.mysql.jdbc.GoogleDriver");
		  url ="jdbc:google:mysql://aitam-service:aitam/aitamDB?user=root&password=newton";
	 }
	 else{
		 		// Connecting from an external network.
		  Class.forName("com.mysql.jdbc.Driver");
		  url = "jdbc:mysql://173.194.248.235:3306/aitamDB?user=aitamUser&password=newton";
	 }
		Connection conn = DriverManager.getConnection(url);
		return conn;
	}

}
}
