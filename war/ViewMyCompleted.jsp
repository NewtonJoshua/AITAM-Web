<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.bean.TaskBean"%>
    <%@ page import="com.AITAM.demo.dao.ViewMyCompleted"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>view myTask</title>
</head>
<body>
<% 
request.setAttribute("tab", "list4");
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee");
if (emp.equals(null)){
	response.sendRedirect("/Login.jsp");
}
ViewMyCompleted viewTask= new ViewMyCompleted();
List<TaskBean> l=null;

	l=viewTask.view(emp);
	if(! (l.isEmpty())){

%>
<table  bgcolor="#336699" border="1" width="80%">
<tr><th>ID</th><th>Task Title</th><th>Status</th><th>Assigned Date</th><th>Due Date</th><th>Completed</th><th>Priority
</th><th>Assigned By</th><th>PerforMeter</th>
</tr>
<%for(TaskBean task: l){ %>
<tr >
<td><%=task.getTaskId() %></td>
<td><%=task.getTitle() %> <img align="right" width="20px" src="images/question-mark.png" 
onclick='alert("Description of Task: \n<%out.print(task.getDesc()); %>");'/></td>
<td><%=task.getStatus()%></td>
<td><%=task.getCreatedDate() %></td>
<td><%=task.getDueDate() %></td>
<td><%=task.getCompletedDate() %></td>
<td><%=task.getPriority() %></td>
<td><%=task.getAssigneeName() %></td>
<td align="center"><%=task.getRating() %></td>
</tr>
<%} %>
</table>
<%}
else{%>
<div>Oops!!! you've done nothing, so far</div>
<%} %>
</body>
</html>
