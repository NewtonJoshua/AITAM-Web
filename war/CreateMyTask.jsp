<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>Insert title here</title>

</head>
<body>
<%			request.setAttribute("tab", "list5"); %>
<form method="post" action="Controller">
    	<table width="80%">
    	<tr>
    		<td width="15%">Title:</td><td width="35%"><input autofocus required maxlength="100" type="text" size=40% 
    		name="title"></input>
    		</td><td width="15%">Due Date: </td><td width="20%">
    		<input required pattern="[2][0][1][0-9][-][0-1][0-9][-][0-3][0-9]" min="2015-01-01" title="yyyy-mm-dd" 
    		type="date" name="eta" id="dat"></input>
    		</td>
    	</tr><tr>
    		<td>Description</td><td><textarea required maxlength="250" rows="3"  cols=40% name="desc"></textarea></td>
    		<td>Priority: </td>
    		<td><input type="radio" name="priority" value="High" >High<br>
    			<input type="radio" name="priority" value="Normal" >Normal<br>
    			<input type="radio" name="priority" value="Low" checked>Low<br>
    		</td>
    	</tr><tr><td colspan="4" align="center"> 
    	Is this an ourTask?
    	<input type="radio" name="Approve" value="No" checked>No
    	<input type="radio" name="Approve" value="Approve" >Yes</td>
    	</tr>
    	<tr><td></td><td align="center">
    	<button  type="submit" name="submit"  value="Create myTask">
    	<b>Create myTask</b>
		</button>
		
    		<td align="center">
    		<button type="reset" value="Reset">
    		<b>   &nbsp; 	&nbsp; Reset &nbsp;  	&nbsp; </b>
			</button>
		</td><td></td>
    	</tr>
    	</table>
    	</form>
</body>
</html>
