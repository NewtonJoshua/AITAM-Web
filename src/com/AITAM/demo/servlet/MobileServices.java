package com.AITAM.demo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.AITAM.demo.bean.DeviceBean;
import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.NotificationBean;
import com.AITAM.demo.bean.TaskBean;
import com.AITAM.demo.dao.ChangeDate;
import com.AITAM.demo.dao.ChangeStatus;
import com.AITAM.demo.dao.CreateMyTask;
import com.AITAM.demo.dao.DeviceAdd;
import com.AITAM.demo.dao.DeviceLogin;
import com.AITAM.demo.dao.DeviceRegister;
import com.AITAM.demo.dao.GetManager;
import com.AITAM.demo.dao.GetMembers;
import com.AITAM.demo.dao.GetTaskDetails;
import com.AITAM.demo.dao.Login;
import com.AITAM.demo.dao.Notification;
import com.AITAM.demo.dao.Perfometer;
import com.AITAM.demo.dao.Rating;
import com.AITAM.demo.dao.ReviewOurTask;
import com.AITAM.demo.dao.Summary;
import com.AITAM.demo.dao.ViewMyCompleted;
import com.AITAM.demo.dao.ViewMyNewTasks;
import com.AITAM.demo.dao.ViewMyTask;
import com.AITAM.demo.dao.ViewOurCompleted;
import com.AITAM.demo.dao.ViewOurTask;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * Servlet implementation class MobileServices
 */
public class MobileServices extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	private final static Logger LOGGER = Logger.getLogger(Messages.getString("MobileServices.0")); //$NON-NLS-1$

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MobileServices() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter(Messages.getString("MobileServices.1")); //$NON-NLS-1$
		int EmpId;

		// Employee ID
		EmpBean emp = new EmpBean();
		EmpId = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.2"))); //$NON-NLS-1$
		emp.setID(EmpId);
		LOGGER.log(Level.SEVERE, action + Messages.getString("MobileServices.3") + emp.getID()); //$NON-NLS-1$

		// CORS
		response.addHeader(Messages.getString("MobileServices.4"), Messages.getString("MobileServices.5")); //$NON-NLS-1$ //$NON-NLS-2$
		response.addHeader(Messages.getString("MobileServices.6"), Messages.getString("MobileServices.7")); //$NON-NLS-1$ //$NON-NLS-2$
		response.setContentType(Messages.getString("MobileServices.8")); //$NON-NLS-1$

		// JSON
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonEle;

		NotificationBean bean = new NotificationBean();
		try {

			// Login
			if (action.equals(Messages.getString("MobileServices.9"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.10"))); //$NON-NLS-1$
				String pw = request.getParameter(Messages.getString("MobileServices.11")); //$NON-NLS-1$
				String name = Login.mobLogin(id, pw);
				json.put(Messages.getString("MobileServices.12"), name); //$NON-NLS-1$
				Summary sum = new Summary();
				String chart = sum.count(emp);
				json.put(Messages.getString("MobileServices.13"), chart); //$NON-NLS-1$
				String ourChart = sum.ourCount(emp);
				json.put(Messages.getString("MobileServices.14"), ourChart); //$NON-NLS-1$
			}

			// Add Device
			if (action.equals(Messages.getString("MobileServices.15"))) { //$NON-NLS-1$
				int EmpID = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.16"))); //$NON-NLS-1$
				String DeviceID = (request.getParameter(Messages.getString("MobileServices.17"))); //$NON-NLS-1$
				String platform = (request.getParameter(Messages.getString("MobileServices.18"))); //$NON-NLS-1$
				DeviceBean device = new DeviceBean();
				device.setEmpID(EmpID);
				device.setDeviceID(DeviceID);
				device.setPlatform(platform);
				DeviceAdd add = new DeviceAdd();
				add.deviceAdd(device);
				json.put(Messages.getString("MobileServices.19"), Messages.getString("MobileServices.20")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// Device Login
			if (action.equals(Messages.getString("MobileServices.21"))) { //$NON-NLS-1$
				// int EmpID=Integer.parseInt(request.getParameter("EmpID"));
				String DeviceID = (request.getParameter(Messages.getString("MobileServices.22"))); //$NON-NLS-1$
				DeviceBean device = new DeviceBean();
				// device.setEmpID(EmpID);
				device.setDeviceID(DeviceID);
				DeviceLogin login = new DeviceLogin();
				String name = login.deviceLogin(device);
				emp.setID(Integer.parseInt(name.split(Messages.getString("MobileServices.23"))[2])); //$NON-NLS-1$
				json.put(Messages.getString("MobileServices.24"), name); //$NON-NLS-1$
				Summary sum = new Summary();
				String chart = sum.count(emp);
				json.put(Messages.getString("MobileServices.25"), chart); //$NON-NLS-1$
				String ourChart = sum.ourCount(emp);
				json.put(Messages.getString("MobileServices.26"), ourChart); //$NON-NLS-1$
			}

			// Register Device

			if (action.equals(Messages.getString("MobileServices.27"))) { //$NON-NLS-1$
				String DeviceID = (request.getParameter(Messages.getString("MobileServices.28"))); //$NON-NLS-1$
				String Token = request.getParameter(Messages.getString("MobileServices.29")); //$NON-NLS-1$
				String Platform = request.getParameter(Messages.getString("MobileServices.30")); //$NON-NLS-1$
				String Model = request.getParameter(Messages.getString("MobileServices.31")); //$NON-NLS-1$
				DeviceBean device = new DeviceBean();
				device.setDeviceID(DeviceID);
				device.setToken(Token);
				device.setPlatform(Platform);
				device.setModel(Model);
				DeviceRegister reg = new DeviceRegister();
				reg.deviceRegister(device);
				json.put(Messages.getString("MobileServices.32"), Messages.getString("MobileServices.33")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// mySummary

			if (action.equals(Messages.getString("MobileServices.34"))) { //$NON-NLS-1$

				Summary sum = new Summary();
				String chart = sum.count(emp);
				json.put(Messages.getString("MobileServices.35"), chart); //$NON-NLS-1$
			}

			// MyNew Tasks
			if (action.equals(Messages.getString("MobileServices.36"))) { //$NON-NLS-1$
				ViewMyNewTasks viewTask = new ViewMyNewTasks();
				List<TaskBean> l = new ArrayList<TaskBean>();
				l = viewTask.view(emp);
				if (l != null) {
					for (TaskBean task : l) {
						jsonEle = new JSONObject();
						jsonEle.put(Messages.getString("MobileServices.37"), task.getTaskId()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.38"), task.getTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.39"), task.getCreatedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.40"), task.getReviewerName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.41"), task.getDays()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.42"), task.getPriority()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.43"), task.getAssigneeName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.44"), task.getDesc()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.45"), task.getDueDate()); //$NON-NLS-1$

						jsonArray.put(jsonEle);
					}
				} else {
					jsonEle = new JSONObject();
					jsonEle.put(Messages.getString("MobileServices.46"), Messages.getString("MobileServices.47")); //$NON-NLS-1$ //$NON-NLS-2$
					jsonArray.put(jsonEle);
				}
				json.put(Messages.getString("MobileServices.48"), jsonArray); //$NON-NLS-1$
			}

			// My Open Tasks
			if (action.equals(Messages.getString("MobileServices.49"))) { //$NON-NLS-1$

				ViewMyTask viewTask = new ViewMyTask();
				List<TaskBean> l = new ArrayList<TaskBean>();
				l = viewTask.view(emp);
				if (l != null) {
					for (TaskBean task : l) {
						jsonEle = new JSONObject();
						jsonEle.put(Messages.getString("MobileServices.50"), task.getTaskId()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.51"), task.getTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.52"), task.getStatus()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.53"), task.getCreatedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.54"), task.getDueDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.55"), task.getPriority()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.56"), task.getAssigneeName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.57"), task.getDesc()); //$NON-NLS-1$
						jsonArray.put(jsonEle);
					}
				} else {
					jsonEle = new JSONObject();
					jsonEle.put(Messages.getString("MobileServices.58"), Messages.getString("MobileServices.59")); //$NON-NLS-1$ //$NON-NLS-2$
					jsonArray.put(jsonEle);
				}
				json.put(Messages.getString("MobileServices.60"), jsonArray); //$NON-NLS-1$
			}

			// My Archives
			if (action.equals(Messages.getString("MobileServices.61"))) { //$NON-NLS-1$
				ViewMyCompleted viewTask = new ViewMyCompleted();
				List<TaskBean> l = new ArrayList<TaskBean>();
				l = viewTask.view(emp);
				if (l != null) {
					for (TaskBean task : l) {
						jsonEle = new JSONObject();
						jsonEle.put(Messages.getString("MobileServices.62"), task.getTaskId()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.63"), task.getTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.64"), task.getCreatedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.65"), task.getDueDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.66"), task.getCompletedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.67"), task.getPriority()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.68"), task.getAssigneeName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.69"), task.getDesc()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.70"), task.getRating()); //$NON-NLS-1$
						jsonArray.put(jsonEle);
					}
				} else {
					jsonEle = new JSONObject();
					jsonEle.put(Messages.getString("MobileServices.71"), Messages.getString("MobileServices.72")); //$NON-NLS-1$ //$NON-NLS-2$
					jsonArray.put(jsonEle);
				}
				json.put(Messages.getString("MobileServices.73"), jsonArray); //$NON-NLS-1$
			}

			// Create Task
			if (action.equals(Messages.getString("MobileServices.74"))) { //$NON-NLS-1$
				String title = request.getParameter(Messages.getString("MobileServices.75")); //$NON-NLS-1$
				String desc = request.getParameter(Messages.getString("MobileServices.76")); //$NON-NLS-1$
				String dueDate = request.getParameter(Messages.getString("MobileServices.77")); //$NON-NLS-1$
				String priority = request.getParameter(Messages.getString("MobileServices.78")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTitle(title);
				task.setDesc(desc);
				task.setDueDate(dueDate);
				task.setPriority(priority);
				task.setPriority(Messages.getString("MobileServices.79")); //$NON-NLS-1$
				task.setReviewer(000000);
				String Approve = request.getParameter(Messages.getString("MobileServices.80")); //$NON-NLS-1$
				if (Approve.equalsIgnoreCase(Messages.getString("MobileServices.81"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("MobileServices.82")); //$NON-NLS-1$
				} else {
					task.setStatus(Messages.getString("MobileServices.83")); //$NON-NLS-1$
				}
				task.setCreator(emp.getID());
				task.setAssigned(emp.getID());
				CreateMyTask create = new CreateMyTask();
				int id = create.createMyTask(task);
				json.put(Messages.getString("MobileServices.84"), id); //$NON-NLS-1$
				// Notification
				GetManager get = new GetManager();
				if (task.getStatus().equalsIgnoreCase(Messages.getString("MobileServices.85"))) { //$NON-NLS-1$
					bean.setFrom(emp.getID());
					bean.setTo(get.getManagerID(emp.getID()));
					bean.setTask(id);
					bean.setId(8);
					Notification.pushNotification(bean);
				}
			}

			// Accept
			if (action.equals(Messages.getString("MobileServices.86"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.87"))); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTaskId(id);
				task.setStatus(Messages.getString("MobileServices.88")); //$NON-NLS-1$
				task.setAssigned(emp.getID());
				ChangeStatus change = new ChangeStatus();
				change.changeStatus(task);
				json.put(Messages.getString("MobileServices.89"), Messages.getString("MobileServices.90")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// Appeal
			if (action.equals(Messages.getString("MobileServices.91"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.92"))); //$NON-NLS-1$
				String appealDate = request.getParameter(Messages.getString("MobileServices.93")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTaskId(id);
				task.setStatus(Messages.getString("MobileServices.94")); //$NON-NLS-1$
				task.setAppealedDate(appealDate);
				task.setAssigned(emp.getID());
				ChangeStatus change = new ChangeStatus();
				change.changeStatus(task);
				json.put(Messages.getString("MobileServices.95"), Messages.getString("MobileServices.96")); //$NON-NLS-1$ //$NON-NLS-2$
				// Notification
				GetTaskDetails get = new GetTaskDetails();
				int Rev_ID = get.getReviewer(task);
				bean.setFrom(emp.getID());
				bean.setTo(Rev_ID);
				bean.setTask(task.getTaskId());
				bean.setId(5);
				Notification.pushNotification(bean);
			}

			// Change Status
			if (action.equals(Messages.getString("MobileServices.97"))) { //$NON-NLS-1$
				String toChange = request.getParameter(Messages.getString("MobileServices.98")); //$NON-NLS-1$
				String Status = request.getParameter(Messages.getString("MobileServices.99")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setStatus(Status);
				task.setTaskId(Integer.parseInt(toChange));
				task.setAssigned(emp.getID());
				ChangeStatus change = new ChangeStatus();
				change.changeStatus(task);
				json.put(Messages.getString("MobileServices.100"), Messages.getString("MobileServices.101")); //$NON-NLS-1$ //$NON-NLS-2$
				// Notification
				GetTaskDetails get = new GetTaskDetails();
				if (Status.equalsIgnoreCase(Messages.getString("MobileServices.102"))) { //$NON-NLS-1$
					bean.setFrom(emp.getID());
					bean.setTo(get.getReviewer(task));
					bean.setTask(task.getTaskId());
					bean.setId(2);
					Notification.pushNotification(bean);
				}
			}

			// myPerfometer

			if (action.equals(Messages.getString("MobileServices.103"))) { //$NON-NLS-1$
				String from = request.getParameter(Messages.getString("MobileServices.104")); //$NON-NLS-1$
				String to = request.getParameter(Messages.getString("MobileServices.105")); //$NON-NLS-1$
				LOGGER.log(Level.SEVERE, Messages.getString("MobileServices.106") + from //$NON-NLS-1$
						+ Messages.getString("MobileServices.107") + to); //$NON-NLS-1$
				Perfometer perf = new Perfometer();
				String str = perf.myChart(from, to, emp);
				json.put(Messages.getString("MobileServices.108"), str); //$NON-NLS-1$
			}

			// ourSummary

			if (action.equals(Messages.getString("MobileServices.109"))) { //$NON-NLS-1$

				Summary sum = new Summary();
				String ourChart = sum.ourCount(emp);
				json.put(Messages.getString("MobileServices.110"), ourChart); //$NON-NLS-1$
			}

			// Review Task

			if (action.equals(Messages.getString("MobileServices.111"))) { //$NON-NLS-1$

				ReviewOurTask viewTask = new ReviewOurTask();
				List<TaskBean> l = new ArrayList<TaskBean>();
				l = viewTask.ReView(emp);
				if (l != null) {
					for (TaskBean task : l) {
						jsonEle = new JSONObject();
						jsonEle.put(Messages.getString("MobileServices.112"), task.getTaskId()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.113"), task.getTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.114"), task.getMainTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.115"), task.getStatus()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.116"), task.getCreatedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.117"), task.getDueDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.118"), task.getMainDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.119"), task.getAppealedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.120"), task.getPriority()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.121"), task.getAssigneeName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.122"), task.getDesc()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.123"), task.getMainDesc()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.124"), task.getAssigned()); //$NON-NLS-1$
						jsonArray.put(jsonEle);
					}
				} else {
					jsonEle = new JSONObject();
					jsonEle.put(Messages.getString("MobileServices.125"), Messages.getString("MobileServices.126")); //$NON-NLS-1$ //$NON-NLS-2$
					jsonArray.put(jsonEle);
				}
				json.put(Messages.getString("MobileServices.127"), jsonArray); //$NON-NLS-1$
			}

			// Review Approve

			if (action.equals(Messages.getString("MobileServices.128"))) { //$NON-NLS-1$
				TaskBean task = new TaskBean();
				String Status = request.getParameter(Messages.getString("MobileServices.129")); //$NON-NLS-1$
				task.setTaskId(Integer.parseInt(request.getParameter(Messages.getString("MobileServices.130")))); //$NON-NLS-1$
				task.setAssigned(Integer.parseInt(request.getParameter(Messages.getString("MobileServices.131")))); //$NON-NLS-1$
				bean.setFrom(emp.getID());
				bean.setTo(task.getAssigned());
				bean.setTask(task.getTaskId());
				if (Status.equalsIgnoreCase(Messages.getString("MobileServices.132"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("MobileServices.133")); //$NON-NLS-1$
					task.setDueDate(request.getParameter(Messages.getString("MobileServices.134"))); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();
					ChangeDate changeDt = new ChangeDate();
					changeDt.changeSub(task);
					change.changeStatus(task);
					// Notification
					bean.setId(6);
					Notification.pushNotification(bean);
				}
				if (Status.equalsIgnoreCase(Messages.getString("MobileServices.135"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("MobileServices.136")); //$NON-NLS-1$
					task.setMainTitle(request.getParameter(Messages.getString("MobileServices.137"))); //$NON-NLS-1$
					task.setMainDate(request.getParameter(Messages.getString("MobileServices.138"))); //$NON-NLS-1$
					task.setMainDesc(request.getParameter(Messages.getString("MobileServices.139"))); //$NON-NLS-1$
					task.setPriority(request.getParameter(Messages.getString("MobileServices.140"))); //$NON-NLS-1$
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
				if (Status.equalsIgnoreCase(Messages.getString("MobileServices.141"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("MobileServices.142")); //$NON-NLS-1$
					int Review = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.143"))); //$NON-NLS-1$
					task.setReview(Review);
					task.setPriorInt(Integer.parseInt(request.getParameter(Messages.getString("MobileServices.144")))); //$NON-NLS-1$
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
				json.put(Messages.getString("MobileServices.145"), Messages.getString("MobileServices.146")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// Review Reject

			if (action.equals(Messages.getString("MobileServices.147"))) { //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setTaskId(Integer.parseInt(request.getParameter(Messages.getString("MobileServices.148")))); //$NON-NLS-1$
				task.setAssigned(Integer.parseInt(request.getParameter(Messages.getString("MobileServices.149")))); //$NON-NLS-1$
				String Status = request.getParameter(Messages.getString("MobileServices.150")); //$NON-NLS-1$
				bean.setFrom(emp.getID());
				bean.setTo(task.getAssigned());
				bean.setTask(task.getTaskId());
				if (Status.equalsIgnoreCase(Messages.getString("MobileServices.151"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("MobileServices.152")); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();
					change.changeStatus(task);
					// Notification
					bean.setId(7);
					Notification.pushNotification(bean);
				}
				if (Status.equalsIgnoreCase(Messages.getString("MobileServices.153"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("MobileServices.154")); //$NON-NLS-1$
					task.setPriority(request.getParameter(Messages.getString("MobileServices.155"))); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();
					change.changeStatus(task);
					change.changeReviewer(task);
					// Notification
					bean.setId(10);
					Notification.pushNotification(bean);
				}
				if (Status.equalsIgnoreCase(Messages.getString("MobileServices.156"))) { //$NON-NLS-1$
					task.setStatus(Messages.getString("MobileServices.157")); //$NON-NLS-1$
					ChangeStatus change = new ChangeStatus();
					change.changeStatus(task);
					// Notification
					bean.setId(4);
					Notification.pushNotification(bean);
				}
				json.put(Messages.getString("MobileServices.158"), Messages.getString("MobileServices.159")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// our Open Task

			if (action.equals(Messages.getString("MobileServices.160"))) { //$NON-NLS-1$

				ViewOurTask viewTask = new ViewOurTask();
				List<TaskBean> l = new ArrayList<TaskBean>();
				l = viewTask.view(emp);
				if (l != null) {
					for (TaskBean task : l) {
						jsonEle = new JSONObject();
						jsonEle.put(Messages.getString("MobileServices.161"), task.getMainTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.162"), task.getTaskId()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.163"), task.getTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.164"), task.getStatus()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.165"), task.getCreatedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.166"), task.getDueDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.167"), task.getMainDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.168"), task.getPriority()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.169"), task.getAssigneeName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.170"), task.getDesc()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.171"), task.getMainDesc()); //$NON-NLS-1$
						jsonArray.put(jsonEle);
					}
				} else {
					jsonEle = new JSONObject();
					jsonEle.put(Messages.getString("MobileServices.172"), Messages.getString("MobileServices.173")); //$NON-NLS-1$ //$NON-NLS-2$
					jsonArray.put(jsonEle);
				}
				json.put(Messages.getString("MobileServices.174"), jsonArray); //$NON-NLS-1$
			}

			// Change Main DueDate

			if (action.equals(Messages.getString("MobileServices.175"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.176"))); //$NON-NLS-1$
				String MainDate = request.getParameter(Messages.getString("MobileServices.177")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setMainDate(MainDate);
				task.setTaskId(id);
				ChangeDate ch = new ChangeDate();
				ch.changeMain(task);
				json.put(Messages.getString("MobileServices.178"), Messages.getString("MobileServices.179")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// Change Sub Date

			if (action.equals(Messages.getString("MobileServices.180"))) { //$NON-NLS-1$
				int id = Integer.parseInt(request.getParameter(Messages.getString("MobileServices.181"))); //$NON-NLS-1$
				String subDate = request.getParameter(Messages.getString("MobileServices.182")); //$NON-NLS-1$
				TaskBean task = new TaskBean();
				task.setDueDate(subDate);
				task.setTaskId(id);
				ChangeDate ch = new ChangeDate();
				ch.changeSub(task);
				json.put(Messages.getString("MobileServices.183"), Messages.getString("MobileServices.184")); //$NON-NLS-1$ //$NON-NLS-2$
			}

			// Our Archives

			if (action.equals(Messages.getString("MobileServices.185"))) { //$NON-NLS-1$
				ViewOurCompleted viewTask = new ViewOurCompleted();
				List<TaskBean> l = new ArrayList<TaskBean>();
				l = viewTask.view(emp);
				if (l != null) {
					for (TaskBean task : l) {
						jsonEle = new JSONObject();
						jsonEle.put(Messages.getString("MobileServices.186"), task.getMainTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.187"), task.getMainDesc()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.188"), task.getTaskId()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.189"), task.getTitle()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.190"), task.getCreatedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.191"), task.getDueDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.192"), task.getMainDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.193"), task.getCompletedDate()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.194"), task.getMainCompleted()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.195"), task.getPriority()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.196"), task.getAssigneeName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.197"), task.getDesc()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.198"), task.getRating()); //$NON-NLS-1$
						jsonArray.put(jsonEle);
					}
				} else {
					jsonEle = new JSONObject();
					jsonEle.put(Messages.getString("MobileServices.199"), Messages.getString("MobileServices.200")); //$NON-NLS-1$ //$NON-NLS-2$
					jsonArray.put(jsonEle);
				}
				json.put(Messages.getString("MobileServices.201"), jsonArray); //$NON-NLS-1$
			}

			// Team Members

			if (action.equals(Messages.getString("MobileServices.202"))) { //$NON-NLS-1$
				GetMembers mem = new GetMembers();
				List<EmpBean> l = new ArrayList<EmpBean>();
				jsonEle = new JSONObject();
				jsonEle.put(Messages.getString("MobileServices.203"), 0); //$NON-NLS-1$
				jsonEle.put(Messages.getString("MobileServices.204"), Messages.getString("MobileServices.205")); //$NON-NLS-1$ //$NON-NLS-2$
				jsonArray.put(jsonEle);
				l = mem.getMembers(emp);
				if (l != null) {
					for (EmpBean e : l) {
						jsonEle = new JSONObject();
						jsonEle.put(Messages.getString("MobileServices.206"), e.getID()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.207"), e.getName()); //$NON-NLS-1$
						jsonEle.put(Messages.getString("MobileServices.208"), e.getSup_Name()); //$NON-NLS-1$
						jsonArray.put(jsonEle);
					}
				} else {
					jsonEle = new JSONObject();
					jsonEle.put(Messages.getString("MobileServices.209"), Messages.getString("MobileServices.210")); //$NON-NLS-1$ //$NON-NLS-2$
					jsonArray.put(jsonEle);
				}
				json.put(Messages.getString("MobileServices.211"), jsonArray); //$NON-NLS-1$
			}

			// Create Our Task

			if (action.equals(Messages.getString("MobileServices.212"))) { //$NON-NLS-1$
				String Maintitle = request.getParameter(Messages.getString("MobileServices.213")); //$NON-NLS-1$
				String Maindesc = request.getParameter(Messages.getString("MobileServices.214")); //$NON-NLS-1$
				String MainDate = request.getParameter(Messages.getString("MobileServices.215")); //$NON-NLS-1$
				String priority = request.getParameter(Messages.getString("MobileServices.216")); //$NON-NLS-1$
				String assignee = request.getParameter(Messages.getString("MobileServices.217")); //$NON-NLS-1$
				String[] assignees = assignee.split(Messages.getString("MobileServices.218")); //$NON-NLS-1$
				CreateMyTask getID = new CreateMyTask();
				int MainID = 0;
				MainID = getID.getMainID();
				for (int i = 1; i < assignees.length; i++) {
					TaskBean task = new TaskBean();
					task.setMainID(MainID + 1);
					task.setMainTitle(Maintitle);
					task.setMainDesc(Maindesc);
					task.setMainDate(MainDate);
					task.setPriority(priority);
					task.setStatus(Messages.getString("MobileServices.219")); //$NON-NLS-1$
					task.setTitle(Maintitle);
					task.setDesc(Maindesc);
					task.setAssigned(Integer.parseInt(assignees[i]));
					task.setDueDate(MainDate);
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
				json.put(Messages.getString("MobileServices.220"), Maintitle); //$NON-NLS-1$
			}

			// Our Performeter

			if (action.equals(Messages.getString("MobileServices.221"))) { //$NON-NLS-1$
				String from = request.getParameter(Messages.getString("MobileServices.222")); //$NON-NLS-1$
				String to = request.getParameter(Messages.getString("MobileServices.223")); //$NON-NLS-1$
				Perfometer perf = new Perfometer();
				String str = perf.teamPerfometer(from, to, emp);
				json.put(Messages.getString("MobileServices.224"), str); //$NON-NLS-1$
			}

			String notify = Messages.getString("MobileServices.225") + bean.getId() //$NON-NLS-1$
					+ Messages.getString("MobileServices.226") + bean.getFrom() //$NON-NLS-1$
					+ Messages.getString("MobileServices.227") + bean.getTo() + Messages.getString("MobileServices.228") //$NON-NLS-1$ //$NON-NLS-2$
					+ bean.getTask();

			LOGGER.log(Level.SEVERE, notify);
			// Exception Handling
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, Messages.getString("MobileServices.229") + e.toString() //$NON-NLS-1$
					+ Messages.getString("MobileServices.230") + e.getStackTrace().toString()); //$NON-NLS-1$
		}
		// Send Response
		LOGGER.log(Level.SEVERE, Messages.getString("MobileServices.231") + json.toString()); //$NON-NLS-1$
		response.getWriter().write(json.toString());

	}

}
