<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.dao.GetMembers"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%
EmpBean emp=(EmpBean)session.getAttribute("Employee");
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
String str1=null;
str1=(String)session.getAttribute("Ourchart");
GetMembers get= new GetMembers();
List<EmpBean> l= get.getMembers(emp);
%>
</head>
<body>
<input type="hidden" value="<%=str1%>" id="ourChartValue"></input>
<table width="70%" height="30%" align="center"><tr>
<td width="40%">
<form method="post" action="Controller">
<div style="float:left">
From: <br>
<input required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]" onchange="myMin(this.value,'Ourto');"  title="yyyy-mm-dd" type="date" id="Ourfrom" name="Ourfrom"></input>
</div >
<div  style="float:left">
To: <br> 
<input required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]"  title="yyyy-mm-dd" type="date" id="Ourto" name="Ourto"></input>
</div>
<div align="center">
<input type="hidden" value="getOurChart" name="submit"></input>
<button id="view" onclick="onloadimg();">Enlighten me</button>
</div>
</form>
</td>
<td align="center" width="20%">
<div id="memOur" style="display:none;" >
Show the Perfometer of<br>
<%if (str1 != null){ %>
<select id="ourMembers" style="width:80%;">
<option value=0>ourTeam</option>
<%int i=1;
for (EmpBean e:l){ %>
<option value="<%=i++%>"><%=e.getName() %></option>
<%} %>
</select>
<%} %>
</div>
<div align="right" id="OurzoomDiv" style="display:none;" >
<input type="image" src="images/previous.png" id="Ourprev" height="40px"></input>
<input type="image" src="images/zoom.png" id="Ourzoom" height="40px"></input>
<input type="image" src="images/next.png" id="Ournext" height="40px"></input>
</div>

</td>
<td width="20%">
<div id="Ourdetails" align="right"></div>
</td><td width="20%">
<div id="Oursummary" align="left"></div>
</td>
</tr></table>

<div id="Ourchart_div" align="center" >
<%if (str1 == null){ %>
Select a duration to generate the Performance Chart
<%} %>

</div>


  </body>
</html>