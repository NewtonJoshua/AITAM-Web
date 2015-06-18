package com.AITAM.demo.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger ;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

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


/**
 * Servlet implementation class MobileServices
 */
public class MobileServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MobileServices() {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private final static Logger LOGGER = Logger.getLogger("MobileServices"); 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action= request.getParameter("action");
		int EmpId;

		
		//Employee ID
		EmpBean emp=new EmpBean();
		EmpId=Integer.parseInt(request.getParameter("EmpID"));
		emp.setID(EmpId);
		LOGGER.log(Level.SEVERE,action+" - "+emp.getID());
		
		//CORS
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Content-Type", "application/json");
		response.setContentType("application/json");
		
		//JSON
		JSONObject json      = new JSONObject();
		JSONArray  jsonArray = new JSONArray();
		JSONObject jsonEle;
		
		NotificationBean bean= new NotificationBean();
		try
		{
			
		//Login
		if(action.equals("Login")){
			int id=Integer.parseInt(request.getParameter("EmpID"));
			String pw=request.getParameter("pw");
			String name  =Login.mobLogin(id, pw);
			json.put("Login", name);
			Summary sum=new Summary();
			String chart=sum.count(emp);
			json.put("mySummary", chart);
			String ourChart= sum.ourCount(emp);
			json.put("ourSummary", ourChart);
		}
		
		//Add Device
		if(action.equals("DeviceAdd")){
			int EmpID=Integer.parseInt(request.getParameter("EmpID"));
			String DeviceID=(request.getParameter("DeviceID"));
			String platform=(request.getParameter("platform"));
			DeviceBean device = new DeviceBean();
			device.setEmpID(EmpID);
			device.setDeviceID(DeviceID);
			device.setPlatform(platform);
			DeviceAdd add= new DeviceAdd();
			add.deviceAdd(device);
			json.put("DeviceAdd", "Success");
		}
		
		//Device Login
		if(action.equals("DeviceLogin")){
			//int EmpID=Integer.parseInt(request.getParameter("EmpID"));
			String DeviceID=(request.getParameter("DeviceID"));		
			DeviceBean device = new DeviceBean();
			//device.setEmpID(EmpID);
			device.setDeviceID(DeviceID);		
			DeviceLogin login = new DeviceLogin();
			String name  =login.deviceLogin(device);
			emp.setID(Integer.parseInt(name.split(";")[2])) ;
			json.put("Login", name);
			Summary sum=new Summary();
			String chart=sum.count(emp);
			json.put("mySummary", chart);
			String ourChart= sum.ourCount(emp);
			json.put("ourSummary", ourChart);
		}		
		
		//Register Device
		
		if(action.equals("DeviceRegister")){
			String DeviceID=(request.getParameter("DeviceID"));
			String Token= request.getParameter("Token");
			String Platform= request.getParameter("Platform");
			String Model= request.getParameter("Model");
			DeviceBean device = new DeviceBean();
			device.setDeviceID(DeviceID);
			device.setToken(Token);
			device.setPlatform(Platform);
			device.setModel(Model);
			DeviceRegister reg= new DeviceRegister();
			reg.deviceRegister(device);
			json.put("DeviceRegister", "Success");
		}
		
		//mySummary
		
		if(action.equals("mySummary")){
			
			Summary sum=new Summary();
			String chart=sum.count(emp);
			json.put("mySummary", chart);
		}
		
		
				
		//MyNew Tasks
		if(action.equals("MyNewTask")){
			ViewMyNewTasks viewTask= new ViewMyNewTasks();
			List<TaskBean> l= new ArrayList<TaskBean>();
				l=viewTask.view(emp);			
				if(l!=null){
					for(TaskBean task: l){
						jsonEle = new JSONObject();
						jsonEle.put("TaskID", task.getTaskId());
						jsonEle.put("Title", task.getTitle());
						jsonEle.put("CreatedDate", task.getCreatedDate());
						jsonEle.put("ReviewerName", task.getReviewerName());
						jsonEle.put("Days", task.getDays());
						jsonEle.put("Priority", task.getPriority());
						jsonEle.put("AssigneeName", task.getAssigneeName());
						jsonEle.put("Description", task.getDesc());
						jsonEle.put("DueDate", task.getDueDate());
						
						jsonArray.put(jsonEle);
					}
				}
				else{
					jsonEle = new JSONObject();
					jsonEle.put("TaskID", "NoTask");
					jsonArray.put(jsonEle);
				}
				json.put("MyNewTask", jsonArray);
		}
		
		//My Open Tasks
		if(action.equals("MyOpenTask")){
			
			ViewMyTask viewTask= new ViewMyTask();
			List<TaskBean> l= new ArrayList<TaskBean>();
				l=viewTask.view(emp);			
				if(l!=null){
					for(TaskBean task: l){
						jsonEle = new JSONObject();
						jsonEle.put("TaskID", task.getTaskId());
						jsonEle.put("Title", task.getTitle());
						jsonEle.put("Status", task.getStatus());
						jsonEle.put("CreatedDate", task.getCreatedDate());
						jsonEle.put("DueDate", task.getDueDate());
						jsonEle.put("Priority", task.getPriority());
						jsonEle.put("AssigneeName", task.getAssigneeName());
						jsonEle.put("Description", task.getDesc());
						jsonArray.put(jsonEle);
					}
				}
				else{
					jsonEle = new JSONObject();
					jsonEle.put("TaskID", "NoTask");
					jsonArray.put(jsonEle);
				}
				json.put("MyOpenTask", jsonArray);
		}
		
		//My Archives
				if(action.equals("MyArchive")){
					ViewMyCompleted viewTask= new ViewMyCompleted();
					List<TaskBean> l= new ArrayList<TaskBean>();
						l=viewTask.view(emp);			
						if(l!=null){
							for(TaskBean task: l){
								jsonEle = new JSONObject();
								jsonEle.put("TaskID", task.getTaskId());
								jsonEle.put("Title", task.getTitle());
								jsonEle.put("CreatedDate", task.getCreatedDate());
								jsonEle.put("DueDate", task.getDueDate());
								jsonEle.put("CompletedDate", task.getCompletedDate());
								jsonEle.put("Priority", task.getPriority());
								jsonEle.put("AssigneeName", task.getAssigneeName());
								jsonEle.put("Description", task.getDesc());
								jsonEle.put("Rating", task.getRating());
								jsonArray.put(jsonEle);
							}
						}
						else{
							jsonEle = new JSONObject();
							jsonEle.put("TaskID", "NoTask");
							jsonArray.put(jsonEle);
						}
						json.put("MyArchive", jsonArray);
				}
				
				//Create Task
				if(action.equals("CreateTask")){
					String title= request.getParameter("title");
					String desc= request.getParameter("desc");
					String dueDate=request.getParameter("eta");
					String priority=request.getParameter("priority");
					TaskBean task= new TaskBean();
					task.setTitle(title);
					task.setDesc(desc);
					task.setDueDate(dueDate);
					task.setPriority(priority);
					task.setPriority("Low");
					task.setReviewer(000000);
					String Approve=request.getParameter("Approve");
					if(Approve.equalsIgnoreCase("true")){
						task.setStatus("Approve");
					}
					else{
						task.setStatus("Accepted");				
					}
					task.setCreator(emp.getID());
					task.setAssigned(emp.getID());
					CreateMyTask create=new CreateMyTask();
					int id=create.createMyTask(task);
					json.put("CreateTask", id);
					// Notification
					GetManager get = new GetManager();
					if(task.getStatus().equalsIgnoreCase("Approve")){
					bean.setFrom(emp.getID());
					bean.setTo(get.getManagerID(emp.getID()));
					bean.setTask(id);
					bean.setId(8);
					Notification.pushNotification(bean);
					}
				}
				
				//Accept
				if(action.equals("Accept")){
					int id=Integer.parseInt(request.getParameter("id"));
					TaskBean task=new TaskBean();
					task.setTaskId(id);
					task.setStatus("Accepted");
					task.setAssigned(emp.getID());
					ChangeStatus change= new ChangeStatus();
					change.changeStatus( task);
					json.put("Accept", "Succcess");
				}
				
				//Appeal
				if(action.equals("Appeal")){
					int id=Integer.parseInt(request.getParameter("id"));
					String appealDate=request.getParameter("appealDate");
					TaskBean task=new TaskBean();
					task.setTaskId(id);
					task.setStatus("Appeal");
					task.setAppealedDate(appealDate);
					task.setAssigned(emp.getID());
					ChangeStatus change= new ChangeStatus();
					change.changeStatus( task);
					json.put("Appeal", "Succcess");
					//Notification
					GetTaskDetails get = new GetTaskDetails();
					int Rev_ID= get.getReviewer(task);
					bean.setFrom(emp.getID());
					bean.setTo(Rev_ID);
					bean.setTask(task.getTaskId());
					bean.setId(5);
					Notification.pushNotification(bean);
				}
				
				//Change Status
				if(action.equals("Change")){
					String toChange=request.getParameter("toChange");
					String Status=request.getParameter("status");
					TaskBean task=new TaskBean();
					task.setStatus(Status);
					task.setTaskId(Integer.parseInt(toChange));
					task.setAssigned(emp.getID());
					ChangeStatus change= new ChangeStatus();
					change.changeStatus( task);
					json.put("Change", "Succcess");
					// Notification
					GetTaskDetails get= new GetTaskDetails();
					if(Status.equalsIgnoreCase("Review")){
					bean.setFrom(emp.getID());
					bean.setTo(get.getReviewer(task));
					bean.setTask(task.getTaskId());
					bean.setId(2);
					Notification.pushNotification(bean);
					}
				}
				
				//myPerfometer
				
				if(action.equals("MyPerfometer")){
					String from=request.getParameter("from");
					String to=request.getParameter("to");
					LOGGER.log(Level.SEVERE,"from:"+from+", to:"+to);
					Perfometer perf= new Perfometer();
					String str= perf.myChart(from, to, emp);
					json.put("MyPerfometer", str);
				}
				
				
				//ourSummary
				
				if(action.equals("ourSummary")){
					
					Summary sum=new Summary();
					String ourChart= sum.ourCount(emp);
					json.put("ourSummary", ourChart);
				}
				
				
				//Review Task
				
				if(action.equals("ReviewTask")){
					
					ReviewOurTask viewTask= new ReviewOurTask();
					List<TaskBean> l= new ArrayList<TaskBean>();
						l=viewTask.ReView(emp);			
						if(l!=null){
							for(TaskBean task: l){
								jsonEle = new JSONObject();
								jsonEle.put("TaskID", task.getTaskId());
								jsonEle.put("Title", task.getTitle());
								jsonEle.put("MainTitle", task.getMainTitle());
								jsonEle.put("Status", task.getStatus());
								jsonEle.put("CreatedDate", task.getCreatedDate());
								jsonEle.put("DueDate", task.getDueDate());
								jsonEle.put("MainDate", task.getMainDate());
								jsonEle.put("AppealedDate", task.getAppealedDate());
								jsonEle.put("Priority", task.getPriority());
								jsonEle.put("AssigneeName", task.getAssigneeName());
								jsonEle.put("Description", task.getDesc());
								jsonEle.put("MainDesc", task.getMainDesc());							
								jsonEle.put("Assigned", task.getAssigned());
								jsonArray.put(jsonEle);
							}
						}
						else{
							jsonEle = new JSONObject();
							jsonEle.put("TaskID", "NoTask");
							jsonArray.put(jsonEle);
						}
						json.put("ReviewTask", jsonArray);
				}
				
				//Review Approve
				
				if (action.equals("ReviewAccept")){
					TaskBean task= new TaskBean();
					String Status=request.getParameter("status");
					task.setTaskId(Integer.parseInt(request.getParameter("taskID")));
					task.setAssigned(Integer.parseInt(request.getParameter("assigned")));
					bean.setFrom(emp.getID());
					bean.setTo(task.getAssigned());
					bean.setTask(task.getTaskId());
					if (Status.equalsIgnoreCase("Appeal")){
						task.setStatus("Appeal-Acp");
						task.setDueDate(request.getParameter("AppealDate"));
						ChangeStatus change= new ChangeStatus();
						ChangeDate changeDt= new ChangeDate();
						changeDt.changeSub(task);
						change.changeStatus(task);
						// Notification
						bean.setId(6);
						Notification.pushNotification(bean);
					}
					if (Status.equalsIgnoreCase("Approve")){
						task.setStatus("Approve-Acp");
						task.setMainTitle(request.getParameter("MainTitle"));
						task.setMainDate(request.getParameter("DueDate"));
						task.setMainDesc(request.getParameter("Disc"));
						task.setPriority(request.getParameter("Priority"));
						task.setReviewer(emp.getID());
						ChangeStatus change= new ChangeStatus();
						CreateMyTask create = new CreateMyTask();
						int MainID=create.getMainID();
						task.setMainID(MainID+1);
						create.createOurTask(task);
						change.changeStatus(task);
						change.changeReviewer(task);
						// Notification
						bean.setId(9);
						Notification.pushNotification(bean);
					}
					if (Status.equalsIgnoreCase("Review")){
						task.setStatus("Completed");
						int Review=Integer.parseInt(request.getParameter("Rating"));
						task.setReview(Review);			
						task.setPriorInt(Integer.parseInt(request.getParameter("Priority")));
						Rating r=new Rating();
						ChangeStatus change= new ChangeStatus();
						ChangeDate comp= new ChangeDate();
						change.changeStatus(task);
						comp.changeCompleted(task);
						r.getRating(task);
						// Notification
						bean.setId(3);
						Notification.pushNotification(bean);
					}
					json.put("ReviewAccept", "Succcess");
				}
				
				//Review Reject
				
				if (action.equals("ReviewReject")){	
					TaskBean task= new TaskBean();
					task.setTaskId(Integer.parseInt(request.getParameter("taskID")));
					task.setAssigned(Integer.parseInt(request.getParameter("assigned")));
					String Status=request.getParameter("status");
					bean.setFrom(emp.getID());
					bean.setTo(task.getAssigned());
					bean.setTask(task.getTaskId());
					if (Status.equalsIgnoreCase("Appeal")){
						task.setStatus("Appeal-Dec");
						ChangeStatus change= new ChangeStatus();
						change.changeStatus(task);
						//Notification
						bean.setId(7);
						Notification.pushNotification(bean);
					}
					if (Status.equalsIgnoreCase("Approve")){
						task.setStatus("Approve-Dec");
						task.setPriority(request.getParameter("Priority"));
						ChangeStatus change= new ChangeStatus();
							change.changeStatus(task);
							change.changeReviewer(task);
							//Notification
							bean.setId(10);
							Notification.pushNotification(bean);
					}
					if (Status.equalsIgnoreCase("Review")){
						task.setStatus("ReWork");
						ChangeStatus change= new ChangeStatus();
						change.changeStatus(task);
						//Notification
						bean.setId(4);
						Notification.pushNotification(bean);
					}
					json.put("ReviewReject", "Succcess");
				}
				
				//our Open Task
				
				if(action.equals("OurOpenTask")){
					
					ViewOurTask viewTask= new ViewOurTask();
					List<TaskBean> l= new ArrayList<TaskBean>();
						l=viewTask.view(emp);			
						if(l!=null){
							for(TaskBean task: l){
								jsonEle = new JSONObject();
								jsonEle.put("MainTitle", task.getMainTitle());
								jsonEle.put("TaskID", task.getTaskId());
								jsonEle.put("Title", task.getTitle());
								jsonEle.put("Status", task.getStatus());
								jsonEle.put("CreatedDate", task.getCreatedDate());
								jsonEle.put("DueDate", task.getDueDate());
								jsonEle.put("MainDate", task.getMainDate());
								jsonEle.put("Priority", task.getPriority());
								jsonEle.put("AssigneeName", task.getAssigneeName());
								jsonEle.put("Description", task.getDesc());
								jsonEle.put("MainDesc", task.getMainDesc());
								jsonArray.put(jsonEle);
							}
						}
						else{
							jsonEle = new JSONObject();
							jsonEle.put("TaskID", "NoTask");
							jsonArray.put(jsonEle);
						}
						json.put("OurOpenTask", jsonArray);
				}
				
				// Change Main DueDate
				
				if (action.equals("changeDate")){
					int id=Integer.parseInt(request.getParameter("id"));
					String MainDate=request.getParameter("MainDate");
					TaskBean task= new TaskBean();
					task.setMainDate(MainDate);
					task.setTaskId(id);
					ChangeDate ch= new ChangeDate();
					ch.changeMain(task);
					json.put("changeDate", "Success");
				}
				
				// Change Sub Date
				
				if (action.equals("changeSubDate")){
					int id=Integer.parseInt(request.getParameter("id"));
					String subDate=request.getParameter("subDate");
					TaskBean task= new TaskBean();
					task.setDueDate(subDate);
					task.setTaskId(id);
					ChangeDate ch= new ChangeDate();
					ch.changeSub(task); 
					json.put("changeSubDate", "Success");
				}
				
				//Our Archives
				
				if(action.equals("OurArchive")){
					ViewOurCompleted viewTask= new ViewOurCompleted();
					List<TaskBean> l= new ArrayList<TaskBean>();
						l=viewTask.view(emp);			
						if(l!=null){
							for(TaskBean task: l){
								jsonEle = new JSONObject();
								jsonEle.put("MainTitle", task.getMainTitle());
								jsonEle.put("MainDesc", task.getMainDesc());
								jsonEle.put("TaskID", task.getTaskId());
								jsonEle.put("Title", task.getTitle());
								jsonEle.put("CreatedDate", task.getCreatedDate());
								jsonEle.put("DueDate", task.getDueDate());
								jsonEle.put("MainDate", task.getMainDate());
								jsonEle.put("CompletedDate", task.getCompletedDate());
								jsonEle.put("MainCompleted", task.getMainCompleted());
								jsonEle.put("Priority", task.getPriority());
								jsonEle.put("AssigneeName", task.getAssigneeName());
								jsonEle.put("Description", task.getDesc());
								jsonEle.put("Rating", task.getRating());
								jsonArray.put(jsonEle);
							}
						}
						else{
							jsonEle = new JSONObject();
							jsonEle.put("TaskID", "NoTask");
							jsonArray.put(jsonEle);
						}
						json.put("OurArchive", jsonArray);
				}
				
				//Team Members
				
				if(action.equals("TeamMembers")){
					GetMembers mem= new GetMembers();
					List<EmpBean> l= new ArrayList<EmpBean>();
					jsonEle = new JSONObject();
					jsonEle.put("ID", 0);
					jsonEle.put("Name", "ourTask");
					jsonArray.put(jsonEle);
					l=mem.getMembers(emp);			
						if(l!=null){
							for(EmpBean e: l){
								jsonEle = new JSONObject();
								jsonEle.put("ID", e.getID());
								jsonEle.put("Name", e.getName());
								jsonEle.put("Supervisor", e.getSup_Name());
								jsonArray.put(jsonEle);
							}
						}
						else{
							jsonEle = new JSONObject();
							jsonEle.put("ID", "NoMem");
							jsonArray.put(jsonEle);
						}
						json.put("TeamMembers", jsonArray);
				}
				
				//Create Our Task
				
				if (action.equals("CreateOurTask")){
					String Maintitle= request.getParameter("title");
					String Maindesc= request.getParameter("desc");
					String MainDate=request.getParameter("eta");
					String priority=request.getParameter("priority");
					String assignee=request.getParameter("assignee");
					String [] assignees=assignee.split(" ");
					CreateMyTask getID=new CreateMyTask();
					int MainID=0;
					MainID=  getID.getMainID();
					for(int i=1; i<assignees.length;i++){
							TaskBean task= new TaskBean();
							task.setMainID(MainID+1);
							task.setMainTitle(Maintitle);
							task.setMainDesc(Maindesc);
							task.setMainDate(MainDate);
							task.setPriority(priority);
							task.setStatus("New");
							task.setTitle(Maintitle);
							task.setDesc(Maindesc);
							task.setAssigned(Integer.parseInt(assignees[i]));
							task.setDueDate(MainDate);
							task.setReviewer(emp.getID());
							task.setCreator(emp.getID());
							CreateMyTask create=new CreateMyTask();
							int taskID=create.createMyTask(task);
							// Notification
							bean.setFrom(emp.getID());
							bean.setTo(task.getAssigned());
							bean.setTask(taskID);
							bean.setId(1);
							Notification.pushNotification(bean);
					}
					json.put("CreateOurTask", Maintitle);
				}
				
				//Our Performeter
				
				if (action.equals("OurPerformeter")){
					String from=request.getParameter("Ourfrom");
					String to=request.getParameter("Ourto");
					Perfometer perf= new Perfometer();
					String str= perf.teamPerfometer(from, to, emp);
					json.put("OurPerformeter", str);
				}
		
		
		String notify= "ID: "+bean.getId()+" From: "+bean.getFrom() +" To: "+ bean.getTo() + " Task: " + bean.getTask();		
		
		LOGGER.log(Level.SEVERE,notify);
		//Exception Handling
		}
		catch(Exception e){
			LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
		}
		//Send Response
		LOGGER.log(Level.SEVERE,"JSON:  "+json.toString());
		response.getWriter().write(json.toString());
		
	}

}
