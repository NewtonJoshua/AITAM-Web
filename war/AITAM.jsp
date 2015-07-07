<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<%@ page import="com.AITAM.demo.bean.EmpBean"%>
<%@ page import="com.AITAM.demo.dao.Summary"%>
	<title>AITAM</title>
	
<script src="JavaScript/charts.js"></script>
<script type="text/javascript" src="JavaScript/jsapi.js"></script>
<script src="JavaScript/scriptFiles.js"></script>
<script type="text/javascript">
google.load("visualization", "1", {packages:["corechart","gauge","orgchart"]});
 </script>
 	<link rel="stylesheet" type="text/css" href="CSS/Styles.css"></link>

<link rel="shortcut icon" href="images/action_item.png">

</head>
<% 

EmpBean emp=null;
	String user="User";
	String msg="";
	String ses="";
	emp=(EmpBean)session.getAttribute("Employee");
	session.setAttribute("Employee", emp);
	try{
	user=emp.getName();
	}
	catch(Exception e){
		request.setAttribute("msg", "Login to AITAM");
		RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
		view.forward(request,response);
	}
	 msg= (String)request.getAttribute("msg");
	 if (msg==null){
		 msg="Welcome to AITAM";
	 }
	 String tab=(String)request.getAttribute("tab");
	 if(tab==null){
		 tab="Home";
	 }
	 int isMan=0;
	 try{
	 isMan=emp.getManager();
	 }
	 catch(NullPointerException e){
	 	
	 }
	 ses=(String)session.getAttribute("ses"); 
		request.setAttribute("Employee", emp);
		String chart=null;
		String ourChart=null;
		if(emp!=null && tab=="Home"){
			Summary sum=new Summary();
			chart=sum.count(emp);
			if(isMan==1){
				ourChart=sum.ourCount(emp);
			}
		}
	%>

<body onload="startTime();onloadActive('<%=tab%>','<%=chart%>', '<%=ourChart%>');">
<div id="title" align="center">
<img alt="" src="images/action_item.png" height="40px">
<font color="#204060" face="Old English Text MT" size="7" >
A<font size=5 face="Monotype Corsiva">ction</font> 
I<font size=5 face="Monotype Corsiva">tem</font> 
T<font size=5 face="Monotype Corsiva">racking</font> 
A<font size=5 face="Monotype Corsiva">nd</font> 
M<font size=5 face="Monotype Corsiva">anagement</font></font>
<img alt="" src="images/Action_Manager.png" height="40px">
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
 <font face="Times New Roman" size="4" color="#336699">Good <span><%=ses  %></span> <b><i><%=user  %></i></b></font>
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
		<li id="list1"><a href="#" onclick="activeClass('list1')">Notifications</a></li>
		<li id="list2"><a href="#" onclick="activeClass('list2')">New myTasks</a></li>
		<li id="list3" ><a href="#" onclick="activeClass('list3')">Open myTasks</a></li>
		<li id="list4"><a href="#" onclick="activeClass('list4')">myTasks Archive</a></li>
		<li id="list5" ><a href="#" onclick="activeClass('list5')">Create myTask</a></li>
		
		<li id="list7" ><a href="#" onclick="activeClass('list7')">myPerforMeter</a></li>
	</ul>
	<div  align="right"><font size=6 color="#336699" face="Monotype Corsiva">myTask</font></div>
</div>
<div id="borderDiv">

</div>
<div  align="center" id="loading"></div>
 <div  align="center" id="txtHint">

</div>
<%
if(isMan==1){ %>
<div id="ManagerPane">
 <div id="talltabs-blue">
	<ul>
		<li id="list11"><a href="#" onclick="activeClass1('list11')">Review ourTasks</a></li>
		<li id="list12"><a href="#" onclick="activeClass1('list12')">Open ourTasks</a></li>
		<li id="list13" ><a href="#" onclick="activeClass1('list13')">ourTasks Archive</a></li>
		<li id="list14"><a href="#" onclick="activeClass1('list14')">Create ourTask</a></li>
		<li id="list15" ><a href="#" onclick="activeClass1('list15');">Team Tree</a></li>
		<li id="list16" ><a href="#" onclick="activeClass1('list16')">ourPerforMeter</a></li>
		
	</ul>
	<div align="right"><font size=6 color="#336699" face="Monotype Corsiva">ourTask</font></div>
</div>
<div id="borderDiv">

</div>

 <div  align="center" id="txtHint1">

 </div>
</div>

 <%} %>
 
 <div id="title" align="center">
 <div align="right"><i>
 <a href="Profile.jsp">Manage your AITAM profile </a>&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp;
 &nbsp;&nbsp; &nbsp;
 &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
 <a href="https://github.com/NewtonJoshua/AITAM/blob/master/AITAM.ppt?raw=true">Download AITAM HandBook</a> &nbsp; 
 &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;
 &nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;&nbsp; &nbsp;</i>
  AITAM v1.0 &nbsp; &nbsp;</div>
 </div>
<div id="" style="height:10px">

</div>
</body>
</html>
