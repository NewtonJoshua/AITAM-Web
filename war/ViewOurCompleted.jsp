<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.bean.TaskBean"%>
    <%@ page import="com.AITAM.demo.dao.ViewOurCompleted"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
    
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>view myTask</title>

</head>
<body>
<% 
request.setAttribute("tab", "list13");
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee");

if(emp==null){
	request.setAttribute("msg", "Session Expired!!!");
	RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
	view.forward(request,response);
}
ViewOurCompleted viewTask= new ViewOurCompleted();
List<TaskBean> l=null;

	l=viewTask.view(emp);


if(! (l.isEmpty())){

%>
<table  bgcolor="#336699" border="1" width="80%">
<tr ><th>Title</th><th>ID</th><th>Assignee</th><th>Priority</th><th>Assigned On</th><th>Due On</th><th>Completed<th>Task Due On</th><th>Task Completed<th>PerforMeter</th></tr>

<%for (TaskBean task:l){ %>
<tr><td><%=task.getMainTitle() %>
<img align="right" width="20px" src="images/question-mark.png" onclick='alert("Description of Task: \n<%out.print(task.getMainDesc()); %>\nActivity: <%=task.getTitle() %>\nDescription of activity: \n<%out.print(task.getDesc()); %>");'/>
</td>
<td><%=task.getTaskId() %></td>
<td><%=task.getAssigneeName() %></td>
<td><%=task.getPriority() %></td>
<td><%=task.getCreatedDate() %></td>
<td><%=task.getDueDate() %> </td>
<td><%=task.getCompletedDate() %></td>
<td><%=task.getMainDate()%> </td>
<td><%=task.getMainCompleted() %></td>
<td align="center"><%=task.getRating() %></td>
</tr>
<%} %>
</table>
<%}
else{%>
<div>No Completed Task</div>
<%} %>
</body>
</html>