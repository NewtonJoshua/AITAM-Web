<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import="com.AITAM.demo.dao.GetMembers"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
request.setAttribute("tab", "list2");
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee");
if (emp.equals(null)){
	response.sendRedirect("/Login.jsp");
}
GetMembers get=new GetMembers();
List<EmpBean> l=(List<EmpBean>)get.getAllMembers();
%>
</head>
<body>
<div id="Assign" align="left" style="padding-left:30%">
<form method="post" action="Controller">
<font  size="5" ><b><u>Assign Manager:</u></b></font><br></br>
Unassigned Member:
<select name="mem1" id="mem1">
    	<option value="select">Select Member</option>
    	<%for (EmpBean e:l){ %>
    	<% if(e.getSupervisor()==0){%>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%}} %>
</select>
Assign Manager
<select name="man1" id="man1">
    	<option value="select">Select Manager</option>
    	<%for (EmpBean e:l){ %>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%} %>
</select><br></br>
<div align="center"><button  type="submit" name="submit"  value="AssignManager" onclick="return checkAssign();">
    	<b>Assign</b>
		</button></div><br></br><br>
</form>
</div>
<div id="Edit" align="left" style="padding-left:30%">
<form method="post" action="Controller">
<font size="5" ><b><u>Re-Assign Manager:</u></b></font><br></br>
Assigned Member:
<select name="mem2" id="mem2" onchange="getManager(this.value);">
    	<option value="select">Select Member</option>
    	<%for (EmpBean e:l){ 
    	if(e.getSupervisor()!=0){%>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%}} %>
</select>
Re-Assign Manager

<select name="man2" id="man2" >
    	<option value="select">Select Manager</option>
    	<%for (EmpBean e:l){%>
			<option value=<%=e.getID()%>><%=e.getName() %></option>
			<%} %>
		<option value=0>Dissociate Manager</option>
</select><br>
<div id="currentManager">

</div>
<br>
<div align="center"><button  type="submit" name="submit"  value="ReAssignManager" onclick="return checkReAssign();">
    	<b>ReAssign</b>
		</button></div>
</form>
</div>

</body>
</html>