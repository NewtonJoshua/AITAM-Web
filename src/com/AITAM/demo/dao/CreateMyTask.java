package com.AITAM.demo.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.AITAM.demo.bean.TaskBean;

public class CreateMyTask {
	public int createMyTask(TaskBean task) throws ClassNotFoundException, SQLException, ParseException, IOException {
		Connection conn = Connect.connect();
		int taskID = 0;
		int sl = 0;
		SimpleDateFormat format = new SimpleDateFormat(Messages.getString("CreateMyTask.0")); //$NON-NLS-1$
		Date parsed = format.parse(task.getDueDate());
		java.sql.Date date = new java.sql.Date(parsed.getTime());

		// AITAM TASK
		ResultSet rs = null;
		Statement st = null;
		PreparedStatement pst = null;
		Statement st1 = null;
		ResultSet rs1 = null;
		PreparedStatement pst1 = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(Messages.getString("CreateMyTask.1")); //$NON-NLS-1$

			while (rs.next()) {
				taskID = rs.getInt(1);
			}

			pst = conn.prepareStatement(Messages.getString("CreateMyTask.2") //$NON-NLS-1$
					+ Messages.getString("CreateMyTask.3")); //$NON-NLS-1$
			task.setTaskId(taskID + 1);
			pst.setInt(1, task.getTaskId());
			pst.setString(2, task.getTitle());
			pst.setString(3, task.getDesc());
			pst.setDate(4, date);
			pst.setString(5, task.getPriority());
			pst.setInt(6, task.getReviewer());
			pst.setString(7, task.getStatus());
			pst.setInt(8, task.getCreator());

			Calendar currenttime1 = Calendar.getInstance();
			Date sqldate1 = new Date((currenttime1.getTime()).getTime());
			java.sql.Date sqlDate1 = new java.sql.Date(sqldate1.getTime());

			pst.setDate(9, sqlDate1);
			pst.setInt(10, task.getAssigned());
			pst.execute();

			// AITAM HIST
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("CreateMyTask.4")); //$NON-NLS-1$

			while (rs1.next()) {
				sl = rs1.getInt(1);
			}

			pst1 = conn.prepareStatement(Messages.getString("CreateMyTask.5") //$NON-NLS-1$
					+ Messages.getString("CreateMyTask.6")); //$NON-NLS-1$
			pst1.setInt(1, sl + 1);
			pst1.setInt(2, taskID + 1);
			pst1.setInt(3, task.getAssigned());

			Calendar currenttime = Calendar.getInstance();
			Date sqldate = new Date((currenttime.getTime()).getTime());
			java.sql.Date sqlDate = new java.sql.Date(sqldate.getTime());

			pst1.setDate(4, sqlDate);
			pst1.setString(5, task.getStatus());
			pst1.setString(6, task.getStatus());
			pst1.execute();
		} finally {
			if (rs != null) {
				rs.close();
			}

			if (st != null) {
				st.close();
			}

			if (pst != null) {
				pst.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (rs1 != null) {
				rs1.close();
			}

			if (pst1 != null) {
				pst1.close();
			}

			conn.close();
		}

		if (task.getMainTitle() != null) {
			createOurTask(task);
		}

		return (task.getTaskId());
	}

	public void createOurTask(TaskBean task) throws ClassNotFoundException, SQLException, ParseException {
		Connection conn2 = Connect.connect();
		PreparedStatement pst2 = null;

		try {
			pst2 = conn2.prepareStatement(Messages.getString("CreateMyTask.7") //$NON-NLS-1$
					+ Messages.getString("CreateMyTask.8")); //$NON-NLS-1$
			pst2.setInt(1, task.getTaskId());
			pst2.setString(2, task.getMainTitle());

			SimpleDateFormat format1 = new SimpleDateFormat(Messages.getString("CreateMyTask.9")); //$NON-NLS-1$
			Date parsed1 = format1.parse(task.getMainDate());
			java.sql.Date date1 = new java.sql.Date(parsed1.getTime());

			pst2.setDate(3, date1);
			pst2.setString(4, task.getMainDesc());
			pst2.setString(5, task.getPriority());
			pst2.setInt(6, task.getAssigned());
			pst2.setInt(7, task.getMainID());

			Calendar currenttime1 = Calendar.getInstance();
			Date sqldate1 = new Date((currenttime1.getTime()).getTime());
			java.sql.Date sqlDate1 = new java.sql.Date(sqldate1.getTime());

			pst2.setDate(8, sqlDate1);
			pst2.execute();
		} finally {
			if (pst2 != null) {
				pst2.close();
			}

			conn2.close();
		}
	}

	public int getMainID() throws ClassNotFoundException, SQLException {
		Connection conn = Connect.connect();
		int mainID = 0;
		Statement st = null;
		ResultSet rs = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery(Messages.getString("CreateMyTask.10")); //$NON-NLS-1$

			while (rs.next()) {
				mainID = rs.getInt(1);
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

		return mainID;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
