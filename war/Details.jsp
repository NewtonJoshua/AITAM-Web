<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ page import="com.AITAM.demo.bean.EmpBean"%>
	<%@ page import="com.AITAM.demo.dao.GetDetails"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee"); 
GetDetails g=new GetDetails();
emp=g.getDetails(emp);
%>
</head>
<body>
<table>
<tr><th align="left">Employee ID:</th><td><%=emp.getID()%></td></tr>
<tr><th align="left">Name:</th><td><%=emp.getName() %></td></tr>
<tr><th align="left">Mail ID:</th><td>
<form method="post" action="Controller">
<input required type="email"  id="mail" name="mail" value="<%=emp.getMail()%>"></input>
<button  type="submit" name="submit"  value="editMail"><b>Update Mail</b></button>
</form>
</td></tr>
<tr><th align="left">Phone No:</th><td>
<form method="post" action="Controller">
<input type="text" id="phone" name="phone" value="<%=emp.getPhone() %>"  required pattern="[0-9]{10}" title="10 digit 
phone number"></input>
<button  type="submit" name="submit"  value="editPhone"><b>Update Phone</b></button>
</form>
</td></tr>
<tr><th align="left">Supervisor:</th><td><%=emp.getSup_Name() %></td></tr>

</table>
</body>
</html>
