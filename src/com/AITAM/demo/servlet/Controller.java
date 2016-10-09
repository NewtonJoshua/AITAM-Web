package com.AITAM.demo.servlet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.AITAM.demo.bean.DeviceBean;
import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.NotificationBean;
import com.AITAM.demo.bean.TaskBean;
import com.AITAM.demo.dao.ChangeDate;
import com.AITAM.demo.dao.ChangeStatus;
import com.AITAM.demo.dao.CreateMyTask;
import com.AITAM.demo.dao.DaySession;
import com.AITAM.demo.dao.DeleteMember;
import com.AITAM.demo.dao.DeviceAdd;
import com.AITAM.demo.dao.EditDetails;
import com.AITAM.demo.dao.GetManager;
import com.AITAM.demo.dao.GetTaskDetails;
import com.AITAM.demo.dao.Login;
import com.AITAM.demo.dao.Manager;
import com.AITAM.demo.dao.Notification;
import com.AITAM.demo.dao.Perfometer;
import com.AITAM.demo.dao.Rating;
import com.AITAM.demo.dao.ReCaptcha;
import com.AITAM.demo.dao.Register;
import com.AITAM.demo.servlet.Messages;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private final static Logger LOGGER = Logger.getLogger(Messages.getString("Controller.0")); //$NON-NLS-1$

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Controller() {
		super();

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(300);
		NotificationBean bean = new NotificationBean();
		// Login
		String submit = request.getParameter(Messages.getString("Controller.1")); //$NON-NLS-1$
		String submit1 = request.getParameter(Messages.getString("Controller.2")); //$NON-NLS-1$
		try {
			if (submit != null && submit.equals(Messages.getString("Controller.3"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("Controller.4"))); //$NON-NLS-1$
				String pw = request.getParameter(Messages.getString("Controller.5")); //$NON-NLS-1$
				String fingerprint = request.getParameter(Messages.getString("Controller.6")); //$NON-NLS-1$
				String browser = request.getParameter(Messages.getString("Controller.7")); //$NON-NLS-1$
				String recap = request.getParameter(Messages.getString("Controller.8")); //$NON-NLS-1$
				String res = ReCaptcha.verify(recap);
				if (res.equalsIgnoreCase(Messages.getString("Controller.9"))) { //$NON-NLS-1$
					DeviceBean device = new DeviceBean();
					device.setEmpID(id);
					device.setDeviceID(fingerprint);
					device.setPlatform(browser);
					DeviceAdd add = new DeviceAdd();
					EmpBean emp = new EmpBean();
					emp.setID(id);
					Boolean status = false;

					status = Login.login(id, pw);

					if (status) {
						add.deviceAdd(device);
						emp = Login.getDetails(emp);

						request.setAttribute(Messages.getString("Controller.10"), true); //$NON-NLS-1$
						session.setAttribute(Messages.getString("Controller.11"), emp); //$NON-NLS-1$
						String ses = DaySession.daySession();

						// Admin- 666666
						System.out.println(id);
						session.setAttribute(Messages.getString("Controller.12"), ses); //$NON-NLS-1$
						if (id == 666666) {
							System.out.println(Messages.getString("Controller.13")); //$NON-NLS-1$
							RequestDispatcher view = getServletContext()
									.getRequestDispatcher(Messages.getString("Controller.14")); //$NON-NLS-1$
							view.forward(request, response);
						} else {
							request.setAttribute(Messages.getString("Controller.15"), //$NON-NLS-1$
									Messages.getString("Controller.16")); //$NON-NLS-1$
							request.setAttribute(Messages.getString("Controller.17"), //$NON-NLS-1$
									Messages.getString("Controller.18")); //$NON-NLS-1$
							RequestDispatcher view = getServletContext()
									.getRequestDispatcher(Messages.getString("Controller.19")); //$NON-NLS-1$
							view.forward(request, response);
						}
					} else {

						request.setAttribute(Messages.getString("Controller.20"), //$NON-NLS-1$
								Messages.getString("Controller.21")); //$NON-NLS-1$
						RequestDispatcher view = getServletContext()
								.getRequestDispatcher(Messages.getString("Controller.22")); //$NON-NLS-1$
						view.forward(request, response);
					}

				} else {
					request.setAttribute(Messages.getString("Controller.23"), Messages.getString("Controller.24")); //$NON-NLS-1$ //$NON-NLS-2$
					RequestDispatcher view = getServletContext()
							.getRequestDispatcher(Messages.getString("Controller.25")); //$NON-NLS-1$
					view.forward(request, response);
				}
			}

			// Register
			if (submit != null && submit.equals(Messages.getString("Controller.26"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("Controller.27"))); //$NON-NLS-1$
				String name = request.getParameter(Messages.getString("Controller.28")); //$NON-NLS-1$
				String mail = request.getParameter(Messages.getString("Controller.29")); //$NON-NLS-1$
				long phone = Long.parseLong(request.getParameter(Messages.getString("Controller.30"))); //$NON-NLS-1$
				String pw = request.getParameter(Messages.getString("Controller.31")); //$NON-NLS-1$
				String ques = request.getParameter(Messages.getString("Controller.32")); //$NON-NLS-1$
				String ans = request.getParameter(Messages.getString("Controller.33")); //$NON-NLS-1$
				Register reg = new Register();
				EmpBean emp = new EmpBean();
				emp.setSecQues(ques);
				emp.setSecAns(ans);
				emp.setID(id);
				emp.setKey(pw);
				emp.setMail(mail);
				emp.setName(name);
				emp.setPhone(phone);
				emp.setSupervisor(0);
				emp.setAdmin(0);
				emp.setManager(0);
				try {
					reg.register(emp);
				} catch (Exception e) {

					e.printStackTrace();
					session.setAttribute(Messages.getString("Controller.34"), emp); //$NON-NLS-1$
					request.setAttribute(Messages.getString("Controller.35"), Messages.getString("Controller.36")); //$NON-NLS-1$ //$NON-NLS-2$
					request.setAttribute(Messages.getString("Controller.37"), Messages.getString("Controller.38")); //$NON-NLS-1$ //$NON-NLS-2$
					RequestDispatcher view = getServletContext()
							.getRequestDispatcher(Messages.getString("Controller.39")); //$NON-NLS-1$
					view.forward(request, response);
				}
				String ses = DaySession.daySession();

				session.setAttribute(Messages.getString("Controller.40"), ses); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.41"), Messages.getString("Controller.42")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.43"), //$NON-NLS-1$
						Messages.getString("Controller.44")); //$NON-NLS-1$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.45")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Logout
			if (submit != null && submit.equals(Messages.getString("Controller.46"))) { //$NON-NLS-1$
				session.invalidate();
				request.setAttribute(Messages.getString("Controller.47"), Messages.getString("Controller.48")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.49")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Home
			if (submit != null && submit.equals(Messages.getString("Controller.50"))) { //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.51"), Messages.getString("Controller.52")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.53"), Messages.getString("Controller.54")); //$NON-NLS-1$ //$NON-NLS-2$
				EmpBean emp = null;
				emp = (EmpBean) session.getAttribute(Messages.getString("Controller.55")); //$NON-NLS-1$
				if (emp.getID() == 666666) {
					RequestDispatcher view = getServletContext()
							.getRequestDispatcher(Messages.getString("Controller.56")); //$NON-NLS-1$
					view.forward(request, response);
				} else {
					RequestDispatcher view = getServletContext()
							.getRequestDispatcher(Messages.getString("Controller.57")); //$NON-NLS-1$
					view.forward(request, response);
				}
			}

			// Admin Home
			if (submit != null && submit.equals(Messages.getString("Controller.58"))) { //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.59"), Messages.getString("Controller.60")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.61"), Messages.getString("Controller.62")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.63")); //$NON-NLS-1$
				view.forward(request, response);
			}
			// Create myTask
			if (submit != null && submit.equals(Messages.getString("Controller.64"))) { //$NON-NLS-1$
				EmpBean emp = null;
				emp = (EmpBean) session.getAttribute(Messages.getString("Controller.65")); //$NON-NLS-1$
				int id = 0;
				String title = request.getParameter(Messages.getString("Controller.66")); //$NON-NLS-1$
				String desc = request.getParameter(Messages.getString("Controller.67")); //$NON-NLS-1$
				String dueDate = request.getParameter(Messages.getString("Controller.68")); //$NON-NLS-1$
				String priority = request.getParameter(Messages.getString("Controller.69")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTitle(title);
				task.setDesc(desc);
				task.setDueDate(dueDate);
				task.setPriority(priority);
				task.setPriority(Messages.getString("Controller.70")); //$NON-NLS-1$
				task.setReviewer(000000);
				String Approve = request.getParameter(Messages.getString("Controller.71")); //$NON-NLS-1$
				if (Approve.equalsIgnoreCase(Messages.getString("Controller.72"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("Controller.73")); //$NON-NLS-1$

				} else {
					task.setStatus(Messages.getString("Controller.74")); //$NON-NLS-1$
				}

				task.setCreator(emp.getID());
				task.setAssigned(emp.getID());
				CreateMyTask create = new CreateMyTask();

				id = create.createMyTask(task);
				// Notification
				GetManager get = new GetManager();
				if (task.getStatus().equalsIgnoreCase(Messages.getString("Controller.75"))) { //$NON-NLS-1$
					bean.setFrom(emp.getID());
					bean.setTo(get.getManagerID(emp.getID()));
					bean.setTask(id);
					bean.setId(8);
					Notification.pushNotification(bean);
				}

				EmpBean emp1 = (EmpBean) session.getAttribute(Messages.getString("Controller.76")); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.77"), emp1); //$NON-NLS-1$

				request.setAttribute(Messages.getString("Controller.78"), //$NON-NLS-1$
						Messages.getString("Controller.79") + id + Messages.getString("Controller.80")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.81"), Messages.getString("Controller.82")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.83")); //$NON-NLS-1$
				view.forward(request, response);
				// response.sendRedirect("AITAM.jsp");

			}
			// my Open Task

			if (submit != null && submit.equals(Messages.getString("Controller.84"))) { //$NON-NLS-1$
				String toChange = request.getParameter(Messages.getString("Controller.85")); //$NON-NLS-1$
				String Status = request.getParameter(toChange);
				String Reviewer = request.getParameter(Messages.getString("Controller.86") + toChange); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setStatus(Status);
				task.setTaskId(Integer.parseInt(toChange));
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.87")); //$NON-NLS-1$
				task.setAssigned(emp.getID());
				ChangeStatus change = new ChangeStatus();

				change.changeStatus(task);
				// Notification
				if (Status.equalsIgnoreCase(Messages.getString("Controller.88"))) { //$NON-NLS-1$
					bean.setFrom(emp.getID());
					bean.setTo(Integer.parseInt(Reviewer));
					bean.setTask(task.getTaskId());
					bean.setId(2);
					Notification.pushNotification(bean);
				}

				EmpBean emp1 = (EmpBean) session.getAttribute(Messages.getString("Controller.89")); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.90"), emp1); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.91"), Messages.getString("Controller.92")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.93"), Messages.getString("Controller.94")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.95")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Accept

			if (submit != null && submit.equals(Messages.getString("Controller.96"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("Controller.97"))); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTaskId(id);
				task.setStatus(Messages.getString("Controller.98")); //$NON-NLS-1$
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.99")); //$NON-NLS-1$
				task.setAssigned(emp.getID());
				ChangeStatus change = new ChangeStatus();

				change.changeStatus(task);

				EmpBean emp1 = (EmpBean) session.getAttribute(Messages.getString("Controller.100")); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.101"), emp1); //$NON-NLS-1$

				request.setAttribute(Messages.getString("Controller.102"), Messages.getString("Controller.103")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.104"), Messages.getString("Controller.105")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.106")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Appeal
			if (submit != null && submit.equals(Messages.getString("Controller.107"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("Controller.108"))); //$NON-NLS-1$
				String appealDate = request.getParameter(Messages.getString("Controller.109")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTaskId(id);
				task.setStatus(Messages.getString("Controller.110")); //$NON-NLS-1$
				task.setAppealedDate(appealDate);
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.111")); //$NON-NLS-1$
				task.setAssigned(emp.getID());
				ChangeStatus change = new ChangeStatus();
				change.changeStatus(task);
				// Notification
				GetTaskDetails get = new GetTaskDetails();
				int Rev_ID = get.getReviewer(task);
				bean.setFrom(emp.getID());
				bean.setTo(Rev_ID);
				bean.setTask(task.getTaskId());
				bean.setId(5);
				Notification.pushNotification(bean);
				EmpBean emp1 = (EmpBean) session.getAttribute(Messages.getString("Controller.112")); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.113"), emp1); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.114"), Messages.getString("Controller.115")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.116"), Messages.getString("Controller.117")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.118")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Create ourTask
			if (submit != null && submit.equals(Messages.getString("Controller.119"))) { //$NON-NLS-1$

				String Maintitle = request.getParameter(Messages.getString("Controller.120")); //$NON-NLS-1$
				String Maindesc = request.getParameter(Messages.getString("Controller.121")); //$NON-NLS-1$
				String MainDate = request.getParameter(Messages.getString("Controller.122")); //$NON-NLS-1$
				String priority = request.getParameter(Messages.getString("Controller.123")); //$NON-NLS-1$
				CreateMyTask getID = new CreateMyTask();
				int MainID = 0;
				MainID = getID.getMainID();

				for (int i = 1; i < 6; i++) {
					String sel = Messages.getString("Controller.124") + i; //$NON-NLS-1$
					if (request.getParameter(sel).equalsIgnoreCase(Messages.getString("Controller.125"))) { //$NON-NLS-1$

						TaskBean task = new TaskBean();
						task.setMainID(MainID + 1);
						task.setMainTitle(Maintitle);
						task.setMainDesc(Maindesc);
						task.setMainDate(MainDate);
						task.setPriority(priority);
						task.setStatus(Messages.getString("Controller.126")); //$NON-NLS-1$
						task.setTitle(request.getParameter(sel + Messages.getString("Controller.127"))); //$NON-NLS-1$
						task.setDesc(request.getParameter(sel + Messages.getString("Controller.128"))); //$NON-NLS-1$
						task.setAssigned(
								Integer.parseInt(request.getParameter(sel + Messages.getString("Controller.129")))); //$NON-NLS-1$
						task.setDueDate(request.getParameter(sel + Messages.getString("Controller.130"))); //$NON-NLS-1$
						EmpBean emp = null;
						emp = (EmpBean) session.getAttribute(Messages.getString("Controller.131")); //$NON-NLS-1$
						task.setReviewer(emp.getID());
						task.setCreator(emp.getID());
						CreateMyTask create = new CreateMyTask();
						int taskID = create.createMyTask(task);
						// Notification
						bean.setFrom(emp.getID());
						bean.setTo(task.getAssigned());
						bean.setTask(taskID);
						bean.setId(1);
						Notification.pushNotification(bean);
					}
				}

				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.132")); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.133"), emp); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.134"), Messages.getString("Controller.135")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.136"), Messages.getString("Controller.137")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.138")); //$NON-NLS-1$
				view.forward(request, response);

			}

			// Change Main DueDate

			if (submit != null && submit.equals(Messages.getString("Controller.139"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("Controller.140"))); //$NON-NLS-1$

				String MainDate = request.getParameter(Messages.getString("Controller.141")); //$NON-NLS-1$

				TaskBean task = new TaskBean();
				task.setMainDate(MainDate);
				task.setTaskId(id);
				ChangeDate ch = new ChangeDate();

				ch.changeMain(task);

				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.142")); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.143"), emp); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.144"), Messages.getString("Controller.145")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.146"), Messages.getString("Controller.147")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.148")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Change Sub Date

			if (submit1 != null && submit1.equals(Messages.getString("Controller.149"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("Controller.150"))); //$NON-NLS-1$

				String subDate = request.getParameter(Messages.getString("Controller.151")); //$NON-NLS-1$

				TaskBean task = new TaskBean();
				task.setDueDate(subDate);
				task.setTaskId(id);
				ChangeDate ch = new ChangeDate();
				ch.changeSub(task);

				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.152")); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.153"), emp); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.154"), Messages.getString("Controller.155")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.156"), Messages.getString("Controller.157")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.158")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Review Approve

			if (submit != null && submit.equals(Messages.getString("Controller.159"))) { //$NON-NLS-1$
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.160")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				String Status = request.getParameter(Messages.getString("Controller.161")); //$NON-NLS-1$
				task.setTaskId(Integer.parseInt(request.getParameter(Messages.getString("Controller.162")))); //$NON-NLS-1$
				task.setAssigned(Integer.parseInt(request.getParameter(Messages.getString("Controller.163")))); //$NON-NLS-1$
				bean.setFrom(emp.getID());
				bean.setTo(task.getAssigned());
				bean.setTask(task.getTaskId());
				if (Status.equalsIgnoreCase(Messages.getString("Controller.164"))) { //$NON-NLS-1$

					task.setStatus(Messages.getString("Controller.165")); //$NON-NLS-1$
					task.setDueDate(request.getParameter(Messages.getString("Controller.166"))); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();
					ChangeDate changeDt = new ChangeDate();

					changeDt.changeSub(task);
					change.changeStatus(task);
					// Notification
					bean.setId(6);
					Notification.pushNotification(bean);
				}
				if (Status.equalsIgnoreCase(Messages.getString("Controller.167"))) { //$NON-NLS-1$

					task.setStatus(Messages.getString("Controller.168")); //$NON-NLS-1$
					task.setMainTitle(request.getParameter(Messages.getString("Controller.169"))); //$NON-NLS-1$
					task.setMainDate(request.getParameter(Messages.getString("Controller.170"))); //$NON-NLS-1$
					task.setMainDesc(request.getParameter(Messages.getString("Controller.171"))); //$NON-NLS-1$
					task.setPriority(request.getParameter(Messages.getString("Controller.172"))); //$NON-NLS-1$
					task.setReviewer(emp.getID());
					ChangeStatus change = new ChangeStatus();
					CreateMyTask create = new CreateMyTask();

					int MainID = create.getMainID();
					task.setMainID(MainID + 1);
					create.createOurTask(task);
					change.changeStatus(task);
					change.changeReviewer(task);
					// Notification
					bean.setId(9);
					Notification.pushNotification(bean);
				}
				if (Status.equalsIgnoreCase(Messages.getString("Controller.173"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("Controller.174")); //$NON-NLS-1$
					int Review = Integer.parseInt(request.getParameter(Messages.getString("Controller.175"))); //$NON-NLS-1$
					task.setReview(Review);

					task.setPriorInt(Integer.parseInt(request.getParameter(Messages.getString("Controller.176")))); //$NON-NLS-1$
					Rating r = new Rating();
					ChangeStatus change = new ChangeStatus();
					ChangeDate comp = new ChangeDate();
					change.changeStatus(task);
					comp.changeCompleted(task);
					r.getRating(task);
					// Notification
					bean.setId(3);
					Notification.pushNotification(bean);
				}

				session.setAttribute(Messages.getString("Controller.177"), emp); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.178"), Messages.getString("Controller.179")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.180"), Messages.getString("Controller.181")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.182")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Review Reject
			if (submit1 != null && submit1.equals(Messages.getString("Controller.183"))) { //$NON-NLS-1$
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.184")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTaskId(Integer.parseInt(request.getParameter(Messages.getString("Controller.185")))); //$NON-NLS-1$
				task.setAssigned(Integer.parseInt(request.getParameter(Messages.getString("Controller.186")))); //$NON-NLS-1$
				String Status = request.getParameter(Messages.getString("Controller.187")); //$NON-NLS-1$
				bean.setFrom(emp.getID());
				bean.setTo(task.getAssigned());
				bean.setTask(task.getTaskId());
				if (Status.equalsIgnoreCase(Messages.getString("Controller.188"))) { //$NON-NLS-1$

					task.setStatus(Messages.getString("Controller.189")); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();

					change.changeStatus(task);
					// Notification
					bean.setId(7);
					Notification.pushNotification(bean);
				}
				if (Status.equalsIgnoreCase(Messages.getString("Controller.190"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("Controller.191")); //$NON-NLS-1$
					task.setPriority(request.getParameter(Messages.getString("Controller.192"))); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();

					change.changeStatus(task);
					change.changeReviewer(task);
					// Notification
					bean.setId(10);
					Notification.pushNotification(bean);
				}
				if (Status.equalsIgnoreCase(Messages.getString("Controller.193"))) { //$NON-NLS-1$

					task.setStatus(Messages.getString("Controller.194")); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();

					change.changeStatus(task);
					// Notification
					bean.setId(4);

					Notification.pushNotification(bean);
				}
				session.setAttribute(Messages.getString("Controller.195"), emp); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.196"), Messages.getString("Controller.197")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.198"), Messages.getString("Controller.199")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.200")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// myPerfometer
			if (submit != null && submit.equals(Messages.getString("Controller.201"))) { //$NON-NLS-1$
				String from = request.getParameter(Messages.getString("Controller.202")); //$NON-NLS-1$
				String to = request.getParameter(Messages.getString("Controller.203")); //$NON-NLS-1$
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.204")); //$NON-NLS-1$
				long time1 = System.currentTimeMillis();
				Perfometer perf = new Perfometer();
				String str = null;

				str = perf.myChart(from, to, emp);
				System.out.println(str);

				long time2 = System.currentTimeMillis();
				long time = time2 - time1;
				System.out.println(
						String.format(Messages.getString("Controller.205"), TimeUnit.MILLISECONDS.toMinutes(time), //$NON-NLS-1$
								TimeUnit.MILLISECONDS.toSeconds(time)
										- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));
				session.setAttribute(Messages.getString("Controller.206"), str); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.207"), emp); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.208"), Messages.getString("Controller.209")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.210"), Messages.getString("Controller.211")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.212")); //$NON-NLS-1$
				view.forward(request, response);

			}

			// ourPerfometer
			if (submit != null && submit.equals(Messages.getString("Controller.213"))) { //$NON-NLS-1$
				String from = request.getParameter(Messages.getString("Controller.214")); //$NON-NLS-1$
				String to = request.getParameter(Messages.getString("Controller.215")); //$NON-NLS-1$
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.216")); //$NON-NLS-1$
				Perfometer perf = new Perfometer();
				long time1 = System.currentTimeMillis();
				String str = null;

				str = perf.teamPerfometer(from, to, emp);

				long time2 = System.currentTimeMillis();
				long time = time2 - time1;
				System.out.println(
						String.format(Messages.getString("Controller.217"), TimeUnit.MILLISECONDS.toMinutes(time), //$NON-NLS-1$
								TimeUnit.MILLISECONDS.toSeconds(time)
										- TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));
				session.setAttribute(Messages.getString("Controller.218"), str); //$NON-NLS-1$
				session.setAttribute(Messages.getString("Controller.219"), emp); //$NON-NLS-1$
				request.setAttribute(Messages.getString("Controller.220"), Messages.getString("Controller.221")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.222"), Messages.getString("Controller.223")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.224")); //$NON-NLS-1$
				view.forward(request, response);

			}

			// Assign Manager
			if (submit != null && submit.equals(Messages.getString("Controller.225"))) { //$NON-NLS-1$
				int mem = Integer.parseInt(request.getParameter(Messages.getString("Controller.226"))); //$NON-NLS-1$
				int man = Integer.parseInt(request.getParameter(Messages.getString("Controller.227"))); //$NON-NLS-1$
				Manager m = new Manager();

				m.assignManager(mem, man);

				request.setAttribute(Messages.getString("Controller.228"), //$NON-NLS-1$
						man + Messages.getString("Controller.229") + mem + Messages.getString("Controller.230")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.231"), Messages.getString("Controller.232")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.233")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Re Assign Manager
			if (submit != null && submit.equals(Messages.getString("Controller.234"))) { //$NON-NLS-1$
				int mem = Integer.parseInt(request.getParameter(Messages.getString("Controller.235"))); //$NON-NLS-1$
				int man = Integer.parseInt(request.getParameter(Messages.getString("Controller.236"))); //$NON-NLS-1$
				Manager m = new Manager();

				m.assignManager(mem, man);

				request.setAttribute(Messages.getString("Controller.237"), //$NON-NLS-1$
						man + Messages.getString("Controller.238") + mem + Messages.getString("Controller.239")); //$NON-NLS-1$ //$NON-NLS-2$
				if (man == 0) {
					request.setAttribute(Messages.getString("Controller.240"), //$NON-NLS-1$
							mem + Messages.getString("Controller.241")); //$NON-NLS-1$
				}
				request.setAttribute(Messages.getString("Controller.242"), Messages.getString("Controller.243")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.244")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Remove Member
			if (submit != null && submit.equals(Messages.getString("Controller.245"))) { //$NON-NLS-1$
				int mem = Integer.parseInt(request.getParameter(Messages.getString("Controller.246"))); //$NON-NLS-1$
				DeleteMember del = new DeleteMember();

				del.deleteMember(mem);

				request.setAttribute(Messages.getString("Controller.247"), mem + Messages.getString("Controller.248")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.249"), Messages.getString("Controller.250")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.251")); //$NON-NLS-1$
				view.forward(request, response);
			}

			// Edit Mail
			if (submit != null && submit.equals(Messages.getString("Controller.252"))) { //$NON-NLS-1$
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.253")); //$NON-NLS-1$
				String mail = request.getParameter(Messages.getString("Controller.254")); //$NON-NLS-1$
				emp.setMail(mail);
				EditDetails e = new EditDetails();

				e.editMail(emp);

				request.setAttribute(Messages.getString("Controller.255"), Messages.getString("Controller.256")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.257"), Messages.getString("Controller.258")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.259")); //$NON-NLS-1$
				view.forward(request, response);
			}
			// Edit Phone
			if (submit != null && submit.equals(Messages.getString("Controller.260"))) { //$NON-NLS-1$
				EmpBean emp = (EmpBean) session.getAttribute(Messages.getString("Controller.261")); //$NON-NLS-1$
				long phone = Long.parseLong(request.getParameter(Messages.getString("Controller.262"))); //$NON-NLS-1$
				emp.setPhone(phone);
				EditDetails e = new EditDetails();

				e.editPhone(emp);

				request.setAttribute(Messages.getString("Controller.263"), Messages.getString("Controller.264")); //$NON-NLS-1$ //$NON-NLS-2$
				request.setAttribute(Messages.getString("Controller.265"), Messages.getString("Controller.266")); //$NON-NLS-1$ //$NON-NLS-2$
				RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.267")); //$NON-NLS-1$
				view.forward(request, response);
			}

			String notify = Messages.getString("Controller.268") + bean.getId() + Messages.getString("Controller.269") //$NON-NLS-1$ //$NON-NLS-2$
					+ bean.getFrom() + Messages.getString("Controller.270") + bean.getTo() //$NON-NLS-1$
					+ Messages.getString("Controller.271") //$NON-NLS-1$
					+ bean.getTask();
			LOGGER.log(Level.SEVERE, notify);
		}

		catch (Exception e) {
			LOGGER.log(Level.SEVERE, Messages.getString("Controller.272") + e.toString() //$NON-NLS-1$
					+ Messages.getString("Controller.273") + e.getStackTrace().toString()); //$NON-NLS-1$
			request.setAttribute(Messages.getString("Controller.274"), e.getMessage()); //$NON-NLS-1$
			RequestDispatcher view = getServletContext().getRequestDispatcher(Messages.getString("Controller.275")); //$NON-NLS-1$
			view.forward(request, response);
		}

	}

}
