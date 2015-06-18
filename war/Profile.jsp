<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
	<%@ page import="com.AITAM.demo.dao.Summary"%>
	<%@ page import="com.AITAM.demo.dao.GetDetails"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<title>AITAM</title>
<script src="JavaScript/charts.js"></script>
<script type="text/javascript" src="JavaScript/jsapi.js"></script>
<script src="JavaScript/scriptFiles.js"></script>
<script type="text/javascript">
google.load("visualization", "1", {packages:["corechart","gauge","orgchart"]});
 </script>
 	<link rel="stylesheet" type="text/css" href="CSS/Styles.css"></link>
<%
String user="User";
String msg="";
String ses="";
String str="";
String tab="";
EmpBean emp=null;
try{
tab=(String)request.getAttribute("tab");
if(tab==null){
	 tab="Home";
}
emp=(EmpBean)session.getAttribute("Employee"); 

user=emp.getName();

ses=(String)session.getAttribute("ses"); 
String defaultMsg= "Welcome to AITAM Profle Management";
msg=(String)request.getAttribute("msg");
if (msg == null){
	msg=defaultMsg;
}
GetDetails g=new GetDetails();
emp=g.getDetails(emp);
}
catch(Exception e){
	request.setAttribute("msg", "Login to AITAM");
	RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
	view.forward(request,response);
}
%>
</head>
<body onload="startTime();ProfileactiveClass('<%=tab%>');">
<div id="title" align="center">
<img alt="" src="images/action_item.png" height="40px"></img>
<font color="#204060" face="Old English Text MT" size="7" >
A<font size="5" face="Monotype Corsiva">ction</font> 
I<font size="5" face="Monotype Corsiva">tem</font> 
T<font size="5" face="Monotype Corsiva">racking</font> 
A<font size="5" face="Monotype Corsiva">nd</font> 
M<font size="5" face="Monotype Corsiva">anagement</font></font>
<img alt="" src="images/Action_Manager.png" height="40px"></img>
</div>
<div id="" style="height:10px">

</div>
<div align="center">

 <table width="90%"><tr><td width="60%">
 <div  align="left">
<font face="Times New Roman" size="4" color="#336699"><b><i><%=msg  %></i></b></font>
</div>
 </td>
 <td  width="10%"><div id="time" style="font-family:Monotype Corsiva;font-size:large" style="color:#204060">
 </div>
 </td><td width="25%">
  <div align="right">
 <font face="Times New Roman" size="4" color="#336699">Good <span><%=ses %></span> <b><i><%=user  %></i></b></font>
</div>
 </td>
 <td width="2.5%">
 <form method="post" action="Controller">
 <input type="hidden" name="submit" value="Home">
 <input   align="right" type="image" height="27px" id="btn" value="Logout" src="images/Home.png" alt="submit Button">
</form></td>
 <td width="2.5%">
 <form method="post" action="Controller">
 <input type="hidden" name="submit" value="Logout">
 <input   align="right" type="image" height="27px" id="btn" value="Logout" src="images/logout.png" alt="submit Button">
</form></td>
</tr>
 </table>

</div>



<div id="talltabs-blue">
	<ul>
		
		<li id="list1"><a href="#" onclick="ProfileactiveClass('list1')">edit Contact Details</a></li>
		<li id="list2"><a href="#" onclick="ProfileactiveClass('list2')">edit Login Credentials</a></li>
		<li id="list3"><a href="#" onclick="ProfileactiveClass('list3')">provide Feedback</a></li>
	</ul>
	<div  align="right"><font size="6" color="#336699" face="Monotype Corsiva">AITAM Profile</font></div>
</div>
<div id="borderDiv">

</div>
<div  align="center" id="txtHint">
<table>
<tr><th align="left">Employee ID:</th><td><%=emp.getID()%></td></tr>
<tr><th align="left">Name:</th><td><%=emp.getName() %></td></tr>
<tr><th align="left">Mail ID:</th><td><%=emp.getMail() %></td></tr>
<tr><th align="left">Phone No:</th><td><%=emp.getPhone()%></td></tr>
<tr><th align="left">Supervisor:</th><td><%=emp.getSup_Name() %></td></tr>
</table>
</div>

 <div id="title" align="center">
 <div align="right">AITAM v1.0 &nbsp; &nbsp;</div>
 </div>
<div id="" style="height:10px">

</div>
</body>
</html>