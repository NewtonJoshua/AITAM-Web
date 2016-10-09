<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.AITAM.demo.bean.EmpBean"%>
    <%@ page import="com.AITAM.demo.bean.TaskBean"%>
    <%@ page import="com.AITAM.demo.dao.GetMembers"%>
    <%@ page import="java.util.ArrayList,java.util.List" %>
<title>Insert title here</title>
</head>
<body>

<%
request.setAttribute("tab", "list14");
EmpBean emp=null;
try{
emp=(EmpBean)session.getAttribute("Employee");
}
catch (NullPointerException e){
		request.setAttribute("msg", "Session Expired");
		RequestDispatcher view = getServletContext().getRequestDispatcher("/Login.jsp"); 
		view.forward(request,response);
}
GetMembers mem= new GetMembers();
List<EmpBean> l= null;
l=mem.getMembers(emp);

%>
<form method="post" action="Controller">
    	<table width="80%">
    	<tr>
    		<td width="15%">Title:</td><td width="35%">
    		<input required maxlength="100" type="text" size=40% id="mTitle" name="title" onchange="copyText('mTitle','s1Title')"></input>
    		</td><td width="15%">Due Date: </td><td width="20%"><input id="mDate" type="date" required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]" min="2015-01-01" title="yyyy-mm-dd"  name="eta" onchange="copyText('mDate','s1Date')"></input></td>
    	</tr><tr>
    		<td>Description</td><td><textarea required maxlength="250" id="mDesc" rows="3"  cols=40% name="desc" onchange="copyText('mDesc','s1Desc')"></textarea></td>
    		<td>Priority: </td>
    		<td><input type="radio" name="priority" value="High" >High<br>
    			<input type="radio" name="priority" value="Normal" checked>Normal<br>
    			<input type="radio" name="priority" value="Low" >Low<br>
    		</td>
    	</tr>
    	<tr></tr></table>
    	
    	<div  align="center"><b><u><font size=4 >Sub Tasks</font></u></b></div>
    	
    	<table  bgcolor="#336699" border="1" width="80%">
    	<tr><th>Sl NO</th><th>Sub Task Title</th><th>Description</th><th>Asignee</th><th>Due Date</th><th>Add/Remove</th>
    	</tr>

    	<tr id="tr1"><td>1</td>
    	<td><input type="text" id="s1Title" name="s1Title"></input></td>
    	<td><textarea rows="1"  id="s1Desc" name="s1Desc"></textarea></td>
    	<td><select name="s1Assignee" id="s1Assignee">
    		<option value="select">Select Assignee</option>
    	<%for (EmpBean e:l){ %>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%} %>
		</select></td>
    	<td><input type="date" name="s1Date" id="s1Date"></input></td>
    	<td align="center"><img  src="images/Add.png" width="18px" onclick="enableRow('2'); copyText('s1Title','s2Title'); copyText('s1Desc','s2Desc'); copyText('s1Date','s2Date'); RowSelected('s2')"></img>
    	<input type="hidden" id="s1" name="s1" value="selected"></input>
    	</td></tr>
		
		<tr id="tr2" style="display:none"><td>2</td>
    	<td><input type="text" id="s2Title" name="s2Title"></input></td>
    	<td><textarea rows="1"  id="s2Desc" name="s2Desc"></textarea></td>
    	<td><select name="s2Assignee" id="s2Assignee">
    	<option value="select">Select Assignee</option>
    	<%for (EmpBean e:l){ %>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%} %>
		</select></td>
    	<td><input type="date" id="s2Date" name="s2Date"></input></td>
    	<td align="center"><img  src="images/Add.png" width="18px" onclick="enableRow('3'); copyText('s2Title','s3Title'); copyText('s2Desc','s3Desc'); copyText('s2Date','s3Date'); RowSelected('s3')"></img>
    	<input type="hidden" id="s2" name="s2"></input>
    	&nbsp; <img  src="images/remove.png" width="22px" onclick="disableRow('2');"></img>
    	</td></tr>
    	
    	<tr id="tr3" style="display:none"><td>3</td>
    	<td><input type="text" id="s3Title" name="s3Title"></input></td>
    	<td><textarea rows="1"  id="s3Desc" name="s3Desc"></textarea></td>
    	<td><select name="s3Assignee" id="s3Assignee">
    	<option value="select">Select Assignee</option>
    	<%for (EmpBean e:l){ %>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%} %>
		</select></td>
    	<td><input type="date" id="s3Date" name="s3Date"></input></td>
    	<td align="center"><img  src="images/Add.png" width="18px" onclick="enableRow('4'); copyText('s3Title','s4Title'); copyText('s3Desc','s4Desc'); copyText('s3Date','s4Date'); RowSelected('s4')"></img>
    	<input type="hidden" id="s3" name="s3"></input>
    	&nbsp; <img  src="images/remove.png" width="22px" onclick="disableRow('3');"></img>
    	</td></tr>
    	
    	<tr id="tr4" style="display:none"><td>4</td>
    	<td><input type="text" id="s4Title" name="s4Title"></input></td>
    	<td><textarea rows="1" id="s4Desc" name="s4Desc"></textarea></td>
    	<td><select name="s4Assignee" id="s4Assignee">
    	<option value="select">Select Assignee</option>
    	<%for (EmpBean e:l){ %>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%} %>
		</select></td>
    	<td><input type="date" id="s4Date" name="s4Date"></input></td>
    	<td align="center"><img  src="images/Add.png" width="18px" onclick="enableRow('5'); copyText('s4Title','s5Title'); copyText('s4Desc','s5Desc'); copyText('s4Date','s5Date'); RowSelected('s5')"></img>
    	<input type="hidden" id="s4" name="s4"></input>
    	&nbsp; <img  src="images/remove.png" width="22px" onclick="disableRow('4');"></img>
    	</td></tr>
    	
    	<tr id="tr5" style="display:none"><td>5</td>
    	<td><input type="text" id="s5Title" name="s5Title"></input></td>
    	<td><textarea rows="1"  id="s5Desc" name="s5Desc"></textarea></td>
    	<td><select name="s5Assignee" id="s5Assignee">
    	<option value="select">Select Assignee</option>
    	<%for (EmpBean e:l){ %>
			<option value=<%=e.getID() %>><%=e.getName() %></option>
			<%} %>
		</select></td>
    	<td><input type="date" id="s5Date" name="s5Date"></input></td>
    	<td align="center"><input type="hidden" id="s5" name="s5"></input>
    	&nbsp; <img  src="images/remove.png" width="22px" onclick="disableRow('5');"></img>
    	</td></tr>
    	</table>
    	
    	<table>
    	<tr><td></td><td align="center">
    	<button  type="submit" name="submit"  value="Create ourTask">
    	<b>Create ourTask</b>
		</button></td>
    		<td align="center"><button type="reset" value="Reset">
    		<b>   &nbsp; 	&nbsp; Reset &nbsp;  	&nbsp; </b>
			</button></td><td></td>
    	</tr>
    	</table>
    	</form>
</body>
</html>