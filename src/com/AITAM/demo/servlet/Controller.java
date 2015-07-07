package com.AITAM.demo.servlet;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger ;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.AITAM.demo.dao.DeviceAdd;
import com.AITAM.demo.dao.GetManager;
import com.AITAM.demo.dao.GetTaskDetails;
import com.AITAM.demo.dao.Notification;
import com.AITAM.demo.dao.ReCaptcha;
import com.AITAM.demo.dao.ChangeDate;
import com.AITAM.demo.dao.ChangeStatus;
import com.AITAM.demo.dao.CreateMyTask;
import com.AITAM.demo.dao.DaySession;
import com.AITAM.demo.dao.DeleteMember;
import com.AITAM.demo.dao.EditDetails;
import com.AITAM.demo.dao.Login;
import com.AITAM.demo.dao.Manager;
import com.AITAM.demo.dao.Perfometer;
import com.AITAM.demo.dao.Rating;
import com.AITAM.demo.dao.Register;
import com.AITAM.demo.bean.DeviceBean;
import com.AITAM.demo.bean.EmpBean;
import com.AITAM.demo.bean.NotificationBean;
import com.AITAM.demo.bean.TaskBean;


public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();

    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    private final static Logger LOGGER = Logger.getLogger("Controller"); 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, 
	IOException {

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(300);
		NotificationBean bean= new NotificationBean();
		//Login
		String submit= request.getParameter("submit");
		String submit1=request.getParameter("submit1");
		try
		{
		if (submit != null && submit.equals("Login")){
			int id=Integer.parseInt(request.getParameter("id"));
			String pw=request.getParameter("pw");
			String fingerprint=  request.getParameter("fingerprint");
			String browser=  request.getParameter("browser");
			String recap = request.getParameter("g-recaptcha-response");
			String res=ReCaptcha.verify(recap);
			
			if(res.equalsIgnoreCase("true}")){
			DeviceBean device = new DeviceBean();
			device.setEmpID(id);
			device.setDeviceID(fingerprint);
			device.setPlatform(browser);
			DeviceAdd add= new DeviceAdd();
			EmpBean emp= new EmpBean();
			emp.setID(id);
			Boolean status  =false;
				
					status  =Login.login(id, pw);
				
			if (status){
				add.deviceAdd(device);
						emp=Login.getDetails(emp);
				 
				request.setAttribute("Status", true);
				session.setAttribute("Employee", emp);
			    String ses=DaySession.daySession();
		    	
			   //Admin- 666666
			   System.out.println(id);
			   session.setAttribute("ses", ses);
			   if(id==666666){
				   System.out.println("Admin");
				   RequestDispatcher view = getServletContext().getRequestDispatcher("/Admin.jsp"); 
					view.forward(request,response);
			   }
			   else{
				request.setAttribute("msg", "Welcome to AITAM");
				request.setAttribute("tab", "Home");
				RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
				view.forward(request,response);
			   }
			}
			else{
	
				request.setAttribute("msg", "<font color='red'>Login Failed!!!</font><br> Kindly 
				enter the correct User name and Password. If you do not posses an AITAM account,
				kindly Register for AITAM.");
				RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
				view.forward(request,response);
			}

			}
			else{
				request.setAttribute("msg","<font color='red'> Prove that you are human!!!</font>");
				RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
				view.forward(request,response);
			}
		}
		
		//Register
		if (submit != null && submit.equals("Register")){
			int id= Integer.parseInt(request.getParameter("id"));
			String name= request.getParameter("name");
			String mail= request.getParameter("mail");
			long phone= Long.parseLong(request.getParameter("phone"));
			String pw= request.getParameter("pw");
			String ques=request.getParameter("ques");
			String ans=request.getParameter("answer");
			Register reg= new Register();
			EmpBean emp= new EmpBean();
			emp.setSecQues(ques);
			emp.setSecAns(ans);
			emp.setID(id);
			emp.setKey(pw);
			emp.setMail(mail);
			emp.setName(name);
			emp.setPhone( phone);
			emp.setSupervisor(0);
			emp.setAdmin(0);
			emp.setManager(0);
			try {
				reg.register(emp);
			} catch (Exception e) {
				 
				e.printStackTrace();
				session.setAttribute("Employee", emp);
				request.setAttribute("tab", "Home");
				request.setAttribute("msg", "<font color='red'>User ID already Exists</font>");
				RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
				view.forward(request,response);
			} 
			   String ses=DaySession.daySession();
		    	
		    	session.setAttribute("ses", ses);
			request.setAttribute("tab", "Home");
			request.setAttribute("msg", "Sucessfully Registered for AITAM!!! Contact your Admin to align
			your Supervisor");
			RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
			view.forward(request,response);
		}
		
		//Logout
		if (submit != null && submit.equals("Logout")){
			session.invalidate();
			request.setAttribute("msg", "Logged out of AITAM");
			RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
			view.forward(request,response);
		}
		
		//Home
		if (submit != null && submit.equals("Home")){
			request.setAttribute("msg", "Welcome to AITAM");
			request.setAttribute("tab", "Home");
			EmpBean emp=null;
			emp=(EmpBean)session.getAttribute("Employee"); 
			if(emp.getID()==666666){
				RequestDispatcher view = getServletContext().getRequestDispatcher("/Admin.jsp"); 
				view.forward(request,response);
			}
			else{
			RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
			view.forward(request,response);
			}
		}
		
		//Admin Home
				if (submit != null && submit.equals("AdminHome")){
					request.setAttribute("msg", "Welcome to AITAM Admin portal");
					request.setAttribute("tab", "Home");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/Admin.jsp"); 
					view.forward(request,response);
				}
		// Create myTask
		if (submit != null && submit.equals("Create myTask")){
			EmpBean emp=null;
			emp=(EmpBean)session.getAttribute("Employee");
			int id = 0;
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
			if(Approve.equalsIgnoreCase("Approve")){
				task.setStatus("Approve");

			}
			else{
				task.setStatus("Accepted");				
			}

 

			task.setCreator(emp.getID());
			task.setAssigned(emp.getID());
			CreateMyTask create=new CreateMyTask();
			
			id=create.createMyTask(task);
			// Notification
			GetManager get = new GetManager();
			if(task.getStatus().equalsIgnoreCase("Approve")){
			bean.setFrom(emp.getID());
			bean.setTo(get.getManagerID(emp.getID()));
			bean.setTask(id);
			bean.setId(8);
			Notification.pushNotification(bean);
			}

			EmpBean emp1=(EmpBean)session.getAttribute("Employee");
			session.setAttribute("Employee", emp1);
			
			request.setAttribute("msg", "Sucessfully Created myTask "+ id +" !!!");
			request.setAttribute("tab", "list5");
			RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
			view.forward(request,response);
			//response.sendRedirect("AITAM.jsp");
			
		}
		// my Open Task
		
		if (submit != null && submit.equals("myOpenTask")){
			String toChange=request.getParameter("toChange");
			String Status=request.getParameter(toChange);
			String Reviewer=request.getParameter("Rev"+toChange);
			TaskBean task=new TaskBean();
			task.setStatus(Status);
			task.setTaskId(Integer.parseInt(toChange));
			EmpBean emp=(EmpBean) session.getAttribute("Employee");
			task.setAssigned(emp.getID());
			ChangeStatus change= new ChangeStatus();

				change.changeStatus( task);
				// Notification
				if(Status.equalsIgnoreCase("Review")){
				bean.setFrom(emp.getID());
				bean.setTo(Integer.parseInt(Reviewer));
				bean.setTask(task.getTaskId());
				bean.setId(2);
				Notification.pushNotification(bean);
				}

			EmpBean emp1=(EmpBean)session.getAttribute("Employee");
			session.setAttribute("Employee", emp1);
			request.setAttribute("tab", "list3");
			request.setAttribute("msg", "Sucessfully Changed myTask Status!!!");
			RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
			view.forward(request,response);
		}
		
		// Accept
		
		if (submit != null && submit.equals("Accept")){
			int id=Integer.parseInt(request.getParameter("id"));
			TaskBean task=new TaskBean();
			task.setTaskId(id);
			task.setStatus("Accepted");
			EmpBean emp=(EmpBean) session.getAttribute("Employee");
			task.setAssigned(emp.getID());
			ChangeStatus change= new ChangeStatus();

				change.changeStatus( task);
 
			EmpBean emp1=(EmpBean)session.getAttribute("Employee");
			session.setAttribute("Employee", emp1);
			
			request.setAttribute("msg", "Sucessfully Accepted myTask!!!");
			request.setAttribute("tab", "list2");
			RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
			view.forward(request,response);
		}
		
		//Appeal
		if (submit != null && submit.equals("Appeal")){
			int id=Integer.parseInt(request.getParameter("id"));
			String appealDate=request.getParameter("appealDate");
			TaskBean task=new TaskBean();
			task.setTaskId(id);
			task.setStatus("Appeal");
			task.setAppealedDate(appealDate);
			EmpBean emp=(EmpBean) session.getAttribute("Employee");
			task.setAssigned(emp.getID());
			ChangeStatus change= new ChangeStatus();
				change.changeStatus( task);
				//Notification
				GetTaskDetails get = new GetTaskDetails();
				int Rev_ID= get.getReviewer(task);
				bean.setFrom(emp.getID());
				bean.setTo(Rev_ID);
				bean.setTask(task.getTaskId());
				bean.setId(5);
				Notification.pushNotification(bean);
			EmpBean emp1=(EmpBean)session.getAttribute("Employee");
			session.setAttribute("Employee", emp1);
			request.setAttribute("tab", "list2");
			request.setAttribute("msg", "Sucessfully Appealed for the DueDate of myTask!!!");
			RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
			view.forward(request,response);
		}
		
		// Create ourTask
				if (submit != null && submit.equals("Create ourTask")){

					String Maintitle= request.getParameter("title");
					String Maindesc= request.getParameter("desc");
					String MainDate=request.getParameter("eta");
					String priority=request.getParameter("priority");
					CreateMyTask getID=new CreateMyTask();
					int MainID=0;
						MainID=  getID.getMainID();

					for(int i=1; i<6;i++){
						String sel="s"+i;
						if (request.getParameter(sel).equalsIgnoreCase("selected")){
		
							TaskBean task= new TaskBean();
							task.setMainID(MainID+1);
							task.setMainTitle(Maintitle);
							task.setMainDesc(Maindesc);
							task.setMainDate(MainDate);
							task.setPriority(priority);
							task.setStatus("New");
							task.setTitle(request.getParameter(sel+"Title"));
							task.setDesc(request.getParameter(sel+"Desc"));
							task.setAssigned(Integer.parseInt(request.getParameter(sel+
							"Assignee")));
							task.setDueDate(request.getParameter(sel+"Date"));
							EmpBean emp=null;
							emp=(EmpBean)session.getAttribute("Employee"); 
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
					}
					
					EmpBean emp=(EmpBean)session.getAttribute("Employee");
					session.setAttribute("Employee", emp);
					request.setAttribute("tab", "list14");
					request.setAttribute("msg", "Sucessfully Created ourTask!!!");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
					view.forward(request,response);
					
				}
				
				// Change Main DueDate
				
				if (submit != null && submit.equals("changeDate")){
					int id=Integer.parseInt(request.getParameter("id"));
					
					String MainDate=request.getParameter("MainDate");
			
					TaskBean task= new TaskBean();
					task.setMainDate(MainDate);
					task.setTaskId(id);
					ChangeDate ch= new ChangeDate();

						ch.changeMain(task);

					EmpBean emp=(EmpBean)session.getAttribute("Employee");
					session.setAttribute("Employee", emp);
					request.setAttribute("tab", "list12");
					request.setAttribute("msg", "Sucessfully Changed Due Date of ourTask!!!");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
					view.forward(request,response);
				}
				
				// Change Sub Date
				
				if (submit1 != null && submit1.equals("changeSubDate")){
					int id=Integer.parseInt(request.getParameter("id"));
					
					String subDate=request.getParameter("subDate");
					
					TaskBean task= new TaskBean();
					task.setDueDate(subDate);
					task.setTaskId(id);
					ChangeDate ch= new ChangeDate();
						ch.changeSub(task);

					EmpBean emp=(EmpBean)session.getAttribute("Employee");
					session.setAttribute("Employee", emp);
					request.setAttribute("tab", "list12");
					request.setAttribute("msg", "Sucessfully Changed Due Date of ourTask activity!!!");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
					view.forward(request,response);
				}
				
				// Review Approve
				
				if (submit != null && submit.equals("ReviewAccept")){
					EmpBean emp=(EmpBean)session.getAttribute("Employee");
					TaskBean task= new TaskBean();
					String Status=request.getParameter("status");
					task.setTaskId(Integer.parseInt(request.getParameter("taskID")));
					task.setAssigned(Integer.parseInt(request.getParameter("Assignee")));
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
					
					session.setAttribute("Employee", emp);
					request.setAttribute("msg", "Sucessfully Reviewed ourTask!!!");
					request.setAttribute("tab", "list11");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
					view.forward(request,response);
				}
				
				//Review Reject
				if (submit1 != null && submit1.equals("ReviewReject")){
					EmpBean emp= (EmpBean) session.getAttribute("Employee");
					TaskBean task= new TaskBean();
					task.setTaskId(Integer.parseInt(request.getParameter("taskID")));
					task.setAssigned(Integer.parseInt(request.getParameter("Assignee")));
					String Status=request.getParameter("status");
					bean.setFrom(emp.getID());
					bean.setTo(task.getAssigned());
					bean.setTask(task.getTaskId());
					if (Status.equalsIgnoreCase("Appeal")){
						
						task.setStatus("Appeal-Dec");
						ChangeStatus change= new ChangeStatus();

							change.changeStatus(task);
							// Notification
							bean.setId(7);
							Notification.pushNotification(bean);
					}
					if (Status.equalsIgnoreCase("Approve")){
						task.setStatus("Approve-Dec");
						task.setPriority(request.getParameter("Priority"));
						ChangeStatus change= new ChangeStatus();

							change.changeStatus(task);
							change.changeReviewer(task);
							// Notification
							bean.setId(10);
							Notification.pushNotification(bean);
					}
					if (Status.equalsIgnoreCase("Review")){
						
						task.setStatus("ReWork");
						ChangeStatus change= new ChangeStatus();

							change.changeStatus(task);
							// Notification
							bean.setId(4);

							Notification.pushNotification(bean);
					}
					session.setAttribute("Employee", emp);
					request.setAttribute("msg", "Sucessfully Rejected ourTask request!!!");
					request.setAttribute("tab", "list11");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
					view.forward(request,response);
				}
				
				//myPerfometer
				if (submit != null && submit.equals("getChart")){
					String from=request.getParameter("from");
					String to=request.getParameter("to");
					EmpBean emp= (EmpBean) session.getAttribute("Employee");
					long time1=System.currentTimeMillis();
					Perfometer perf= new Perfometer();
					String str=null;

						str= perf.myChart(from, to, emp);
				   		System.out.println(str);

					long time2=System.currentTimeMillis();
					long time=time2-time1;
					System.out.println(String.format("%d min, %d sec", 
						    TimeUnit.MILLISECONDS.toMinutes(time),
						    TimeUnit.MILLISECONDS.toSeconds(time) - 
						    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));
					session.setAttribute("chart", str);
					session.setAttribute("Employee", emp);
					request.setAttribute("msg", "myPerfometer Graph generated!!!");
					request.setAttribute("tab", "list7");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
					view.forward(request,response);
					
				}
				
				// ourPerfometer
				if (submit != null && submit.equals("getOurChart")){
					String from=request.getParameter("Ourfrom");
					String to=request.getParameter("Ourto");
					EmpBean emp= (EmpBean) session.getAttribute("Employee");
					Perfometer perf= new Perfometer();
					long time1=System.currentTimeMillis();
					String str=null;

						str= perf.teamPerfometer(from, to, emp);

					long time2=System.currentTimeMillis();
					long time=time2-time1;
					System.out.println(String.format("%d min, %d sec", 
						    TimeUnit.MILLISECONDS.toMinutes(time),
						    TimeUnit.MILLISECONDS.toSeconds(time) - 
						    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))));
					session.setAttribute("Ourchart", str);
					session.setAttribute("Employee", emp);
					request.setAttribute("msg", "ourPerfometer Graph generated!!!");
					request.setAttribute("tab", "list16");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/AITAM.jsp"); 
					view.forward(request,response);
					
				}
				
				//Assign Manager
				if (submit != null && submit.equals("AssignManager")){
					int mem=Integer.parseInt(request.getParameter("mem1"));
					int man=Integer.parseInt(request.getParameter("man1"));
					Manager m=new Manager();

						m.assignManager(mem, man);
	
					request.setAttribute("msg", man+" has been assigned as the manager of "+mem+" !!!");
					request.setAttribute("tab", "list1");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/Admin.jsp"); 
					view.forward(request,response);
				}
				
				//Re Assign Manager
				if (submit != null && submit.equals("ReAssignManager")){
					int mem=Integer.parseInt(request.getParameter("mem2"));
					int man=Integer.parseInt(request.getParameter("man2"));
					Manager m=new Manager();

						m.assignManager(mem, man);
	
					request.setAttribute("msg", man+" has been assigned as the manager of "+mem+" !!!");
					if(man==0){
						request.setAttribute("msg", mem+" has been dissociated from his/her 
						manager!!!");
					}
					request.setAttribute("tab", "list1");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/Admin.jsp"); 
					view.forward(request,response);
				}
				
				//Remove Member
				if (submit != null && submit.equals("RemoveMember")){
					int mem=Integer.parseInt(request.getParameter("mem1"));
					DeleteMember del=  new DeleteMember();

						del.deleteMember(mem);

					request.setAttribute("msg", mem+" has been removed!!!");
					request.setAttribute("tab", "list2");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/Admin.jsp"); 
					view.forward(request,response);
				}
				
				//Edit Mail
				if (submit != null && submit.equals("editMail")){
					EmpBean emp= (EmpBean) session.getAttribute("Employee");					
					String mail=request.getParameter("mail");
					emp.setMail(mail);
					EditDetails e=new EditDetails();

						e.editMail(emp);
	
					request.setAttribute("msg", "Mail id has been updated!!!");
					request.setAttribute("tab", "list1");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/Profile.jsp"); 
					view.forward(request,response);
				}
				//Edit Phone
				if (submit != null && submit.equals("editPhone")){
					EmpBean emp= (EmpBean) session.getAttribute("Employee");					
					long phone=Long.parseLong(request.getParameter("phone"));
					emp.setPhone( phone);
					EditDetails e=new EditDetails();

						e.editPhone(emp);

					request.setAttribute("msg", "Phone no has been updated!!!");
					request.setAttribute("tab", "list1");
					RequestDispatcher view = getServletContext().getRequestDispatcher("/Profile.jsp"); 
					view.forward(request,response);
				}
				
				String notify= "ID: "+bean.getId()+" From: "+bean.getFrom() +" To: "+ bean.getTo() +
				" Task: " + bean.getTask();		
				LOGGER.log(Level.SEVERE,notify);
		}
		
		catch(Exception e){
			LOGGER.log(Level.SEVERE,"\nException :"+e.toString()+"  Details: "+e.getStackTrace().toString());
			request.setAttribute("msg",e.getMessage());
			RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
			view.forward(request,response);
		}
		

				
		}
		
}

