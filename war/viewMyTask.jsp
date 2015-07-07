<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.bean.TaskBean"%>
    <%@ page import="com.AITAM.demo.dao.ViewMyTask"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>view myTask</title>


</head>
<body>
<% 
request.setAttribute("tab", "list3");
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee");
try{
	String user=emp.getName();
	}
	catch(NullPointerException e){
		request.setAttribute("msg", "Login to AITAM");
		RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
		view.forward(request,response);
	}
ViewMyTask viewTask= new ViewMyTask();
List<TaskBean> l=null;

	l=viewTask.view(emp);


if(! (l.isEmpty())){
	


%>
<table  bgcolor="#336699" border="1" width="80%">
<tr><th>ID</th><th>Task Title</th><th>Status</th><th>Assigned Date</th><th>Due Date</th><th>Priority</th>
<th>Assigned By</th>
</tr>
<%for(TaskBean task: l){ %>
<tr >
<td><%=task.getTaskId() %></td>
<td><%=task.getTitle() %> <img align="right" width="20px" src="images/question-mark.png"
onclick='alert("Description of Task: \n<%out.print(task.getDesc()); %>");'/></td>
<td>
<%String stat= task.getStatus();
%>


<form method="post" action="Controller">
<input name="toChange" type=text hidden=true value="<%=task.getTaskId() %>"></input>
<input name="Rev<%=task.getTaskId()%>" type=text hidden=true value="<%=task.getReviewer()%>"></input>
<div style="float: left;">
<select name="<%=task.getTaskId() %>" onchange=visible('<%=task.getTaskId() %>')>
  
  <%if (stat.equalsIgnoreCase("Appeal")){ %><option value="Appeal"   selected >Appeal</option><%} %>
  <%if (stat.equalsIgnoreCase("Approve")){ %><option value="Approve"   selected >Approve</option><%} %>
  <%if (stat.equalsIgnoreCase("ReWork")){ %><option value="ReWork"   selected >ReWork</option><%} %>
  <%if (stat.equalsIgnoreCase("Approve-Acp")){ %><option value="Approve-Acp"   selected >Approve-Acp</option><%} %>
  <%if (stat.equalsIgnoreCase("Approve-Dec")){ %><option value="Approve-Dec"   selected >Approve-Dec</option><%} %>
  <%if (stat.equalsIgnoreCase("Appeal-Acp")){ %><option value="Appeal-Acp"   selected >Appeal-Acp</option><%} %>
  <%if (stat.equalsIgnoreCase("Appeal-Dec")){ %><option value="Appeal-Dec"   selected >Appeal-Dec</option><%} %>
   <%if (stat.equalsIgnoreCase("Appeal") || stat.equalsIgnoreCase("Approve") || stat.equalsIgnoreCase("Accepted"))
   { %><option value="Accepted"  <%if (stat.equalsIgnoreCase("Accepted")){ %> selected <%} %>>Accepted</option><%} %>
 <%if (!(stat.equalsIgnoreCase("Appeal") || stat.equalsIgnoreCase("Approve"))){ %><option value="Progress"  
 <%if (stat.equalsIgnoreCase("Progress")){ %> selected <%} %> >In Progress</option><%} %>
  <%if (!(stat.equalsIgnoreCase("Appeal") || stat.equalsIgnoreCase("Approve") ||
  task.getAssigneeName().equalsIgnoreCase("Self Assigned"))){ %><option value="Review"  
  <%if (stat.equalsIgnoreCase("Review")){ %> selected <%} %>>Review</option><%} %>
  <%if(task.getAssigneeName().equalsIgnoreCase("Self Assigned")) {%><option value="Completed"   >
  Completed</option><% }%>
</select>
</div>
<div style="float: right">
<input type="hidden" name="submit" value="myOpenTask"></input>
<input   hidden=true align="right" type="image" width="18px" id="<%=task.getTaskId() %>" value="myOpenTask"
src="images/Refresh.png" ></input>

</div>
</form>
 
</td>
<td><%=task.getCreatedDate() %></td>
<td><%=task.getDueDate() %></td>
<td><%=task.getPriority() %></td>
<td><%=task.getAssigneeName() %></td>

</tr>
<%} %>
<input name="toChange" type=text hidden=true value="Newton"></input>
</table>
<%}
else{%>
<div>Hurra!!No Open Tasks</div>
<%} %>
</body>
</html>
