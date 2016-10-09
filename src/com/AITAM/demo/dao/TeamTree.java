package com.AITAM.demo.dao;

import java.sql.SQLException;
import java.util.List;

import com.AITAM.demo.bean.EmpBean;

public class TeamTree {
	public String buildTree(EmpBean n) throws ClassNotFoundException, SQLException {
		EmpBean emp = Login.getDetails(n);
		String Sup = null;

		Sup = emp.getSup_Name();

		if (Sup == null) {
			Sup = Messages.getString("TeamTree.0"); //$NON-NLS-1$
		}

		String str = emp.getID() + Messages.getString("TeamTree.1") + emp.getName() + Messages.getString("TeamTree.2") //$NON-NLS-1$ //$NON-NLS-2$
				+ emp.getPhone() + Messages.getString("TeamTree.3") + Sup; //$NON-NLS-1$
		GetMembers get = new GetMembers();
		List<EmpBean> l = get.getMembers(emp);

		for (EmpBean e : l) {
			str = str + Messages.getString("TeamTree.4") + e.getID() + Messages.getString("TeamTree.5") + e.getName() //$NON-NLS-1$ //$NON-NLS-2$
					+ Messages.getString("TeamTree.6") + e.getPhone() + Messages.getString("TeamTree.7") //$NON-NLS-1$ //$NON-NLS-2$
					+ e.getManager();
		}

		System.out.println(str);

		return str;
	}
}

// ~ Formatted by Jindent --- http://www.jindent.com
