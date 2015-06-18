<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.dao.TeamTree"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
request.setAttribute("tab", "list15");
EmpBean emp=null;
emp=(EmpBean)session.getAttribute("Employee");
if (emp.equals(null)){
	response.sendRedirect("/Login.jsp");
}
String str=(String)session.getAttribute("teamTree");
if (str==null){
TeamTree team= new TeamTree();
str=team.buildTree(emp);
session.setAttribute("teamTree",str);
}
%>
</head>
<body>
<input type="hidden" value="<%=str%>" id="buildTreeChart"></input>
<div id="tree_div"  style="width:80%">
</div>
</body>
</html>