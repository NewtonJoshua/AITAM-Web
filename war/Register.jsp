<?xml version="1.0" encoding="ISO-8859-1" ?>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>Insert title here</title>
</head>
<body>
  <form method="post" action="Controller">
	    <table>
		<tr><td>User ID:</td><td><input required type="text" pattern="[0-9]{6}" Title="6 digit Employee Id" name="id">
		</input></td></tr>
		<tr><td>User Name:</td><td><input required type="text" name="name"></input></td></tr>
		<tr><td>Mail ID:</td><td><input required type="email" name="mail"></input></td></tr>
		<tr><td>Phone No:</td><td><input required pattern="[0-9]{10}" title="10 digit phone number" type="text" 
		name="phone"></input></td></tr>
		<tr><td>Security Question:</td><td><textarea required maxlength="100" rows="2"  cols=22% name="ques">
		</textarea></td></tr>
		<tr><td>Security Answer:</td><td><input required type="textarea" name="answer"></input></td></tr>
		<tr><td>Password: </td><td><input required type="password" name="pw" id="pw"></input></td></tr>
		<tr><td>Confirm Password:</td><td><input required type="password" name="con" id="con" onchange="confirmPW();">
		</input></td></tr>
		<tr></tr><tr></tr>
		<tr><td align="center">
		<button  id="register" type="submit" name="submit"  value="Register" onclick="return confirmPW();">
    	<b>Register for AITAM</b>
		</button></td>
		<td align="center">		
		<button  type="reset"   value="reset">
    	<b>&nbsp; &nbsp;Reset &nbsp; &nbsp;</b>
		</button></td></tr>
		</table>
		</form>
</body>
</html>
