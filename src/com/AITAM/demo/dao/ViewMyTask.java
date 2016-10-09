package com.AITAM.demo.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.TaskBean;

public class ViewMyTask {
	public List<TaskBean> view(EmpBean emp) throws ClassNotFoundException, SQLException {
		List<TaskBean> l = new ArrayList<TaskBean>();
		Connection conn = Connect.connect();
		Statement st1 = null;
		ResultSet rs1 = null;
		Statement st = null;
		ResultSet rs = null;
		Statement st5 = null;
		ResultSet rs5 = null;
		Statement st6 = null;
		ResultSet rs6 = null;

		try {
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("ViewMyTask.0") + emp.getID() //$NON-NLS-1$
					+ Messages.getString("ViewMyTask.1") //$NON-NLS-1$
					+ Messages.getString("ViewMyTask.2")); //$NON-NLS-1$

			while ((rs1 != null) && rs1.next()) {
				int taskId = rs1.getInt(1);

				st = conn.createStatement();
				rs = st.executeQuery(Messages.getString("ViewMyTask.3") + taskId); //$NON-NLS-1$

				while (rs.next()) {
					TaskBean task = new TaskBean();

					task.setTaskId(rs.getInt(1));
					task.setTitle(rs.getString(2));

					Date date = rs.getDate(3);
					DateFormat df = new SimpleDateFormat(Messages.getString("ViewMyTask.4")); //$NON-NLS-1$
					String dateN = df.format(date);

					task.setDueDate(dateN);
					task.setPriority(rs.getString(4));
					task.setReviewer(rs.getInt(5));
					task.setStatus(rs.getString(6));
					task.setDesc(rs.getString(7));
					task.setCommnets(rs.getString(8));
					task.setCreator(rs.getInt(9));

					Date date1 = rs.getDate(10);
					DateFormat df1 = new SimpleDateFormat(Messages.getString("ViewMyTask.5")); //$NON-NLS-1$
					String dateN1 = df1.format(date1);

					task.setCreatedDate(dateN1);

					int Creator = (rs.getInt(9));

					if (rs.getInt(5) == 0) {
						task.setReviewerName(Messages.getString("ViewMyTask.6")); //$NON-NLS-1$
						task.setAssigneeName(Messages.getString("ViewMyTask.7")); //$NON-NLS-1$
					} else {
						st5 = conn.createStatement();
						rs5 = st5.executeQuery(Messages.getString("ViewMyTask.8") + (rs.getInt(5))); //$NON-NLS-1$

						while (rs5.next()) {
							task.setReviewerName(rs5.getString(1));
						}

						st6 = conn.createStatement();
						rs6 = st6.executeQuery(Messages.getString("ViewMyTask.9") + Creator); //$NON-NLS-1$

						while (rs6.next()) {
							task.setAssigneeName(rs6.getString(1));
						}
					}

					l.add(task);
				}
			}
		} finally {
			if (st1 != null) {
				st1.close();
			}

			if (rs1 != null) {
				rs1.close();
			}

			if (st != null) {
				st.close();
			}

			if (rs != null) {
				rs.close();
			}

			if (st5 != null) {
				st5.close();
			}

			if (rs5 != null) {
				rs5.close();
			}

			if (st6 != null) {
				st6.close();
			}

			if (rs6 != null) {
				rs6.close();
			}

			conn.close();
		}

		return l;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
