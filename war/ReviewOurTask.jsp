<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.bean.TaskBean"%>
    <%@ page import="com.AITAM.demo.dao.ReviewOurTask"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>view myTask</title>

</head>
<body>
<% 
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee");
String user=null;
try{
	user=emp.getName();
	}
	catch(NullPointerException e){
		request.setAttribute("msg", "Login to AITAM");
		RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
		view.forward(request,response);
	}
if(user==null){
	request.setAttribute("msg", "Login to AITAM");
	RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
	view.forward(request,response);
}
ReviewOurTask review= new ReviewOurTask();
List<TaskBean> l=null;

	l=review.ReView(emp);


if(! (l.isEmpty())){
	
	request.setAttribute("tab", "list11");

%>

<table  bgcolor="#336699" border="1" width="80%">
<tr><th>Title</th><th>Activity</th><th>ID</th><th>Assignee</th><th>Priority</th><th>Assigned On</th><th>Activity Due On
</th><th>Task Due On</th><th>Approve</th><th>Review</th><th>Comments</th></tr>

<%for (TaskBean task:l){ %>

<tr><td>
<%if (task.getStatus().equalsIgnoreCase("Approve")){ %>
<%=task.getTitle() %>
<img align="right" width="20px" src="images/question-mark.png" onclick='alert("Description of activity: \n
<%out.print(task.getDesc()); %>");'/>
<%} else{ %>
<%=task.getMainTitle() %>
<img align="right" width="20px" src="images/question-mark.png" onclick='alert("Description of Task: \n
<%out.print(task.getMainDesc()); %>");'/>
<%} %>

</td>
<td><%=task.getTitle() %>
<img align="right" width="20px" src="images/question-mark.png" onclick='alert("Description of activity: \n
<%out.print(task.getDesc()); %>");'/>
</td>
<td><%=task.getTaskId() %></td>
<td><%=task.getAssigneeName() %></td>
<td><%=task.getPriority() %></td>
<td><%=task.getCreatedDate() %></td>
<td><%=task.getDueDate() %> </td>
<td><%if (task.getStatus().equalsIgnoreCase("Approve")){ %>
<%=task.getDueDate() %>
<%} else{ %>
<%=task.getMainDate()%> </td>
<%} %>
<td>
<%
int PrioInt=0;
if (task.getPriority()=="High"){
	PrioInt=3;
}
if (task.getPriority()=="Normal"){
	PrioInt=2;
}
if (task.getPriority()=="Low"){
	PrioInt=1;
}
//switch(task.getPriority()){
//						case "High": PrioInt=3; break;
//						case "Normal": PrioInt=2; break;
//						case "Low": PrioInt=1; break;
//						}
						%>
<form method="post" action="Controller">
<input type="hidden" name="status" value="<%=task.getStatus() %>"></input>
<input type="hidden" name="Assignee" value="<%=task.getAssigned() %>"></input>
<input type="hidden" name="taskID" value="<%=task.getTaskId() %>"></input>
<input type="hidden" name="AppealDate" value="<%=task.getAppealedDate() %>"></input>
<input type="hidden" name="MainTitle" value="<%=task.getTitle() %>"></input>
<input type="hidden" name="DueDate" value="<%=task.getDueDate() %> "></input>
<input type="hidden" name="Disc" value="<%out.print(task.getDesc()); %>"></input>
<input type="hidden" name="Priority" value="<%=PrioInt %>"></input>
<input type="hidden" name="Rating" id="rating<%=task.getTaskId() %>" value=""></input>
<input type="hidden" name="submit" value="ReviewAccept"></input>
<input  align="left" type="image" width="18px" id="btn"  src="images/accept.png" alt="Submit" 
onclick="copyText('ratingSrc<%=task.getTaskId() %>','rating<%=task.getTaskId() %>')"></input>
</form>

<form method="post" action="Controller">
<input type="hidden" name="status" value="<%=task.getStatus() %>"></input>
<input type="hidden" name="Assignee" value="<%=task.getAssigned() %>"></input>
<input type="hidden" name="taskID" value="<%=task.getTaskId() %>"></input>
<input type="hidden" name="AppealDate" value="<%=task.getAppealedDate() %>"></input>
<input type="hidden" name="MainTitle" value="<%=task.getTitle() %>"></input>
<input type="hidden" name="DueDate" value="<%=task.getDueDate() %> "></input>
<input type="hidden" name="Disc" value="<%out.print(task.getDesc()); %>"></input>
<input type="hidden" name="Priority" value="<%=task.getPriority() %> "></input>
<input type="hidden" name="Rating" id="rating<%=task.getTaskId() %>" value=""></input>
<input type="hidden" name="submit1" value="ReviewReject"></input>
<input  align="right" type="image" width="18px" id="btn1"  src="images/Reject.png" alt="Submit"></input>
</form>
</td>
<td><%if (task.getStatus().equalsIgnoreCase("Review")){ %>
<select name="ratingSrc" id="ratingSrc<%=task.getTaskId() %>">
<option value="5">Excellent</option>
<option value="4">Very Good</option>
<option value="3" selected>Good</option>
<option value="2">Insufficient</option>
<option value="1">Deficient</option>
</select>
<%} %>
</td>
<td><%if (task.getStatus().equalsIgnoreCase("Appeal")){ %>
Appealed Date: <%=task.getAppealedDate() %>
<%} %>
<%if (task.getStatus().equalsIgnoreCase("Approve")){ %>
Approve as ourTask
<%} %>
<%if (task.getStatus().equalsIgnoreCase("Review")){ %>
For Review: <%=task.getAppealedDate() %>
<%} %>
</td>
</tr>

<%} %>

</table>


<%}
else{%>
<div>No Task Pending your Review</div>
<%} %>
</body>
</html>
