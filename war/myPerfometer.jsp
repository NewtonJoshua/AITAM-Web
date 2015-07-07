<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.dao.Perfometer"%>
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
String str=null;
str=(String)session.getAttribute("chart");

%>
</head>
<body>
<input type="hidden" value="<%=str%>" id="ChartValue"></input>
<table width="70%" height="30%" align="center"><tr>
<td width="40%">
<form method="post" action="Controller">
<div style="float:left">
From: <br>
<input required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]" onchange="myMin(this.value,'to');"  
title="yyyy-mm-dd" type="date" id="from" name="from"></input>
</div >
<div  style="float:left">
To: <br> 
<input required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]"  title="yyyy-mm-dd" type="date" id="to"
name="to"></input>
</div>
<div align="center">
<input type="hidden" value="getChart" name="submit"></input>
<button id="view" type="submit" onclick="myOnloading();">Enlighten me</button>
</div>
</form>

</td>
<td align="center" width="20%">
<div align="right" id="zoomDiv" style="display:none" >
<input type="image" src="images/previous.png" id="prev" height="40px"></input>
<input type="image" src="images/zoom.png" id="zoom" height="40px"></input>
<input type="image" src="images/next.png" id="next" height="40px"></input>
</div>

</td>
<td width="20%">
<div id="details" align="right"></div>
</td><td width="20%">
<div id="summary" align="left"></div>
</td>
</tr></table>


<div id="chart_div" align="center" >
<%if (str == null){ %>
Select a duration to generate the Performance Chart
<%} %>
</div>
<div id="hidden_div" style="display:none"></div>
<div id="guage_div" style="width: 400px; height: 120px;"></div>
  </body>
</html>
