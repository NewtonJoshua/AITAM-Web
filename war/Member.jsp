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
<font  size="5" ><b><u>Remove Member:</u></b></font><br></br>
Member:
<select name="mem1" id="mem1">
    	<option value="select">Select Member</option>
    	<%for (EmpBean e:l){ %>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%} %>
</select>
<button  type="submit" name="submit"  value="RemoveMember">
    	<b>Remove</b>
		</button>
</form>
</div>

</body>
</html>