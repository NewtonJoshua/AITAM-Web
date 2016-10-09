package com.AITAM.demo.dao;

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

public class ChangeDate {
	public void changeCompleted(TaskBean task) throws ClassNotFoundException, SQLException, ParseException {
		Connection conn = Connect.connect();
		PreparedStatement pst2 = null;
		PreparedStatement pst1 = null;
		Statement st1 = null;
		Statement st4 = null;
		Statement st3 = null;
		ResultSet rs1 = null;
		ResultSet rs4 = null;
		ResultSet rs3 = null;

		try {
			pst2 = conn.prepareStatement(Messages.getString("ChangeDate.0")); //$NON-NLS-1$

			Calendar currenttime = Calendar.getInstance();
			Date sqldate = new Date((currenttime.getTime()).getTime());
			java.sql.Date sqlDate = new java.sql.Date(sqldate.getTime());

			pst2.setDate(1, sqlDate);
			pst2.setInt(2, task.getTaskId());
			pst2.execute();
			st1 = conn.createStatement();
			rs1 = st1.executeQuery(Messages.getString("ChangeDate.1") + task.getTaskId()); //$NON-NLS-1$

			int isCompleted = 1;

			while ((rs1 != null) && rs1.next()) {
				task.setMainID(rs1.getInt(1));
				st4 = conn.createStatement();
				rs4 = st4.executeQuery(Messages.getString("ChangeDate.2") + rs1.getInt(1)); //$NON-NLS-1$

				while (rs4.next() & rs4 != null) {
					int subID = rs4.getInt(1);

					st3 = conn.createStatement();
					rs3 = st3.executeQuery(Messages.getString("ChangeDate.3") + subID); //$NON-NLS-1$

					while (rs3.next() && (rs3 != null)) {
						if (!(rs3.getString(1).equalsIgnoreCase(Messages.getString("ChangeDate.4")))) { //$NON-NLS-1$
							isCompleted = 0;
							System.out.println(subID + Messages.getString("ChangeDate.5") + isCompleted); //$NON-NLS-1$
						}
					}
				}
			}

			if (isCompleted == 1) {
				pst1 = conn.prepareStatement(Messages.getString("ChangeDate.6")); //$NON-NLS-1$
				pst1.setDate(1, sqlDate);
				pst1.setInt(2, task.getMainID());
				pst1.execute();
			}
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (rs3 != null) {
				rs3.close();
			}

			if (rs4 != null) {
				rs4.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (st3 != null) {
				st3.close();
			}

			if (st4 != null) {
				st4.close();
			}

			if (pst1 != null) {
				pst1.close();
			}

			if (pst2 != null) {
				pst2.close();
			}

			conn.close();
		}
	}

	public void changeMain(TaskBean task) throws ClassNotFoundException, SQLException, ParseException {
		Connection conn = Connect.connect();
		Statement st1 = conn.createStatement();
		ResultSet rs1 = null;
		PreparedStatement pst2 = null;

		try {
			rs1 = st1.executeQuery(Messages.getString("ChangeDate.7") + task.getTaskId()); //$NON-NLS-1$

			while ((rs1 != null) && rs1.next()) {
				task.setMainID(rs1.getInt(1));
			}

			pst2 = conn.prepareStatement(Messages.getString("ChangeDate.8")); //$NON-NLS-1$

			SimpleDateFormat format = new SimpleDateFormat(Messages.getString("ChangeDate.9")); //$NON-NLS-1$
			Date parsed = format.parse(task.getMainDate());
			java.sql.Date date = new java.sql.Date(parsed.getTime());

			System.out.println(date);
			pst2.setDate(1, date);
			pst2.setInt(2, task.getMainID());
			pst2.execute();
		} finally {
			if (rs1 != null) {
				rs1.close();
			}

			if (st1 != null) {
				st1.close();
			}

			if (pst2 != null) {
				pst2.close();
			}

			conn.close();
		}
	}

	public void changeSub(TaskBean task) throws ClassNotFoundException, SQLException, ParseException {
		Connection conn = Connect.connect();
		PreparedStatement pst2 = null;

		try {
			pst2 = conn.prepareStatement(Messages.getString("ChangeDate.10")); //$NON-NLS-1$

			SimpleDateFormat format = new SimpleDateFormat(Messages.getString("ChangeDate.11")); //$NON-NLS-1$
			Date parsed = format.parse(task.getDueDate());
			java.sql.Date date = new java.sql.Date(parsed.getTime());

			System.out.println(date);
			pst2.setDate(1, date);
			pst2.setInt(2, task.getTaskId());
			pst2.execute();
		} finally {
			if (pst2 != null) {
				pst2.close();
			}

			conn.close();
		}
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
