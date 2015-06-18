<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.bean.TaskBean"%>
    <%@ page import="com.AITAM.demo.dao.ViewMyNewTasks"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>view myTask</title>

</head>
<body>
<% 
request.setAttribute("tab", "list2");
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee");
if (emp.equals(null)){
	response.sendRedirect("/Login.jsp");
}
ViewMyNewTasks viewTask= new ViewMyNewTasks();
List<TaskBean> l=null;

	l=viewTask.view(emp);
	if(! (l.isEmpty())){

%>
<table id="example"  bgcolor="#336699" border="1" width="80%">
<thead>
<tr><th>ID</th><th>Task Title</th><th>Accept</th><th>Assigned Date</th><th>Due Date</th><th>Days</th><th>Priority</th><th>Assigned By</th>
</tr>
</thead>
 <tbody>
<%for(TaskBean task: l){ %>
<tr >
<td><%=task.getTaskId() %></td>
<td><%=task.getTitle() %> <img align="right" width="20px" src="images/question-mark.png" onclick='alert("Description of Task: \n<%out.print(task.getDesc()); %>");'/></td>
<td align="center">
<form method="post" action="Controller">
<input name="id" type=text hidden=true value="<%=task.getTaskId() %>"></input>
<input name="submit" value="Accept" type="hidden"></input>
<input   align="center" type="image" width="18px" id="btn"  src="images/accept.png" alt="submit Button"></input>
</form>
</td>
<td><%=task.getCreatedDate() %></td>
<td>
<div>
<form method="post" action="Controller">

<input name="id" type="text" hidden=true value="<%=task.getTaskId() %>"></input>
<input name="<%=task.getTaskId() %>" type="text" hidden=true value=""></input>
<input type="Date"  required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]" min="2015-01-01" title="yyyy-mm-dd"  name="appealDate" value="<%=task.getDueDate() %>" onchange="visible('<%=task.getTaskId() %>')"></input>
<% String Reviewer=task.getReviewerName() ;
if(!Reviewer.equalsIgnoreCase("Self Reviewed")){ %>
<input type="hidden" name="submit" value="Appeal"></input>
<input  hidden=true id="<%=task.getTaskId() %>" align="right" type="image" width="18px" id="btn" value="" name="" src="images/Refresh.png" ></input>
<%} %>
</form>
</div>
</td>
<td><%=task.getDays() %></td>
<td><%=task.getPriority() %></td>
<td><%=task.getAssigneeName() %></td></tr>
<%} %>
<input name="toChange" type=text hidden=true value="Newton"></input>
</tbody>
</table>
<%}
else{%>
<div>Wow!!! No New Tasks</div>
<%} %>
</body>
</html>