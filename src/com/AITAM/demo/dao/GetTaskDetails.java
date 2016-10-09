package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.AITAM.demo.bean.TaskBean;

public class GetTaskDetails {
	public int getReviewer(TaskBean task) throws ClassNotFoundException, SQLException {
		int Rev_id = 0;
		Connection conn = Connect.connect();
		ResultSet rs = null;
		Statement st = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(Messages.getString("GetTaskDetails.0") + task.getTaskId()); //$NON-NLS-1$

			while (rs.next()) {
				Rev_id = rs.getInt(1);
			}
		} finally {
			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			conn.close();
		}

		return Rev_id;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
