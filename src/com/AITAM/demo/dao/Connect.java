package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

public class Connect {

	public static Connection connect() throws ClassNotFoundException, SQLException {
		String url = null;

		{
			if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

				// Connecting from App Engine.
				// Load the class that provides the "jdbc:google:mysql://"
				// prefix.
				Class.forName(Messages.getString("Connect.0")); //$NON-NLS-1$
				url = Messages.getString("Connect.1"); //$NON-NLS-1$
			} else {

				// Connecting from an external network.
				Class.forName(Messages.getString("Connect.2")); //$NON-NLS-1$
				url = Messages.getString("Connect.3"); //$NON-NLS-1$
			}

			Connection conn = DriverManager.getConnection(url);

			return conn;
		}
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
