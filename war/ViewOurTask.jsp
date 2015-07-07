<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.bean.TaskBean"%>
    <%@ page import="com.AITAM.demo.dao.ViewOurTask"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>view myTask</title>

</head>
<body>
<% 
request.setAttribute("tab", "list12");
EmpBean emp=null;
List<TaskBean> l=null;
try{
emp=(EmpBean)session.getAttribute("Employee");

if(emp==null){
	request.setAttribute("msg", "Session Expired!!!");
	RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
	view.forward(request,response);
}
ViewOurTask viewTask= new ViewOurTask();


	l=viewTask.view(emp);
}
catch(Exception e){
	request.setAttribute("msg", "Session Expired!!!");
	RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
	view.forward(request,response);
	
}

if(! (l.isEmpty())){
	


%>
<table  bgcolor="#336699" border="1" width="80%">
<tr><th>Title</th><th>Activity</th><th>ID</th><th>Assignee</th><th>Status</th><th>Priority</th><th>Assigned On
</th><th>Activity Due On</th><th>Task Due On</tr>
</tr>
<%for (TaskBean task:l){ %>
<tr><td><%=task.getMainTitle() %>
<img align="right" width="20px" src="images/question-mark.png" onclick='alert("Description of Task: \n
<%out.print(task.getMainDesc()); %>");'/>
</td>
<td><%=task.getTitle() %>
<img align="right" width="20px" src="images/question-mark.png" onclick='alert("Description of activity: \n
<%out.print(task.getDesc()); %>");'/>
</td>
<td><%=task.getTaskId() %></td>
<td><%=task.getAssigneeName() %></td>
<td><%=task.getStatus() %></td>
<td><%=task.getPriority() %></td>
<td><%=task.getCreatedDate() %></td>
<td><form method="post" action="Controller"><input type="date" value=<%=task.getDueDate() %> 
name="subDate" required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]" min="2015-01-01" 
title="yyyy-mm-dd" onchange="visible('Sub<%=task.getTaskId() %>')"></input>
<input name="submit1" value="changeSubDate" type="hidden"></input>
<input style="display:none"  align="right" type="image" width="18px"   src="images/Refresh.png" 
id="Sub<%=task.getTaskId() %>"></input>
<input type="hidden" name="id" value="<%=task.getTaskId() %>"></input>
</form></td>
<td><form method="post" action="Controller"><input type="date" value=<%=task.getMainDate() %> 
required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]" min="2015-01-01" title="yyyy-mm-dd" 
name="MainDate" onchange="visible('Main<%=task.getTaskId() %>')"></input>
<input name="submit" value="changeDate" type="hidden"></input>
<input style="display:none"  align="right" type="image" width="18px"   src="images/Refresh.png" 
id="Main<%=task.getTaskId() %>"></input>
<input type="hidden" name="id" value="<%=task.getTaskId() %>"></input>
</form></td>
</tr>
<%} %>
</table>
<%}
else{%>
<div>Hurra!!No Open Tasks</div>
<%} %>
</body>
</html>
