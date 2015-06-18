<!DOCTYPE html>

<head>
<title>AITAM</title>
	<script src="/www/js/fingerprint.js"></script>
	<script src="JavaScript/scriptFiles.js"></script>
 	<script src="JavaScript/scriptFiles.js"></script>
 	<script src="https://www.google.com/recaptcha/api.js" async defer></script>
 	<link rel="stylesheet" type="text/css" href="CSS/Styles.css"></link>
 	<link rel="chrome-webstore-item" href="https://chrome.google.com/webstore/detail/kchdcdekjfkcjdoighhbghbboomaabaa">

<script type="application/ld+json">
{
  "@context": "http://schema.org/",
  "@type": "SoftwareApplication",
  "name": "AITAM",
  "operatingSystem": "ANDROID",
  "applicationCategory": "http://schema.org/GameApplication",
  "aggregateRating":{
    "@type": "AggregateRating",
    "ratingValue": "4.6",
    "ratingCount": "8864"
  },
  "offers":{
    "@type": "Offer",
    "price": "1.00",
    "priceCurrency": "USD"
  }
}
</script>
<script>
function device(){
var fingerprint = (new Fingerprint().get()) + "-" +  new Fingerprint({screen_resolution: true}).get();
document.getElementById("fingerprint").value=fingerprint;
var browser= getBrowser();
document.getElementById("browser").value=browser;
}
</script>
</head>
<body onload="device();LoginactiveClass('list1')">
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
<div id="" style="height:10px"></div>
<%	String defaultMsg= "Welcome to AITAM";
	String msg=(String)request.getAttribute("msg");
	if (msg == null){
		msg=defaultMsg;
	}
	%>
	
<div align="center">
 <table width="90%"><tr><td>
 <div  align="left">
<font face="Times New Roman" size="4" color="#336699"><b><i><%=msg  %></i></b></font>
</div>
 </td></tr>
 </table>
</div>



<div id="talltabs-blue">
	<ul>
		<li id="list1"><a href="#" onclick="window.location='Login.jsp'">myLogin</a></li>
		<li id="list2"><a href="#" onclick="LoginactiveClass('list2')">Register for AITAM</a></li>
		<li id="list3"><a href="#" onclick="LoginactiveClass('list3')">Reset Password</a></li>
	</ul>
	<div  align="right"><font size="6" color="#336699" face="Monotype Corsiva">AITAM Login</font></div>
</div>
<div id="borderDiv">

</div>
<div  align="center" id="txtHint">
<div  align="center" id="txtHint0">
 		<form method="post" action="Controller">
	    <input type="hidden"></input>
	    <table>
		<tr><td>User ID:</td><td><input type="text" required minlength="6" maxlength="6" pattern="[0-9]{6}" title="6 digit Employee ID" name="id" maxlength="6" minlength="6" required autofocus></input></td></tr>
		<tr><td>Password: </td><td><input type="password" required name="pw"  minlength="6" required></input></td></tr>
		<tr><td><input type="hidden" id="fingerprint" name="fingerprint"></input></td>
		<td><input type="hidden" id="browser" name="browser"></input></td></tr>
		<tr>
		</tr>
		<tr></tr>
		<tr><td align="center">
		<button  type="submit" name="submit"  value="Login" onclick="extension();">
    	<b>Login to AITAM</b>
		</button>
		</td>
		<td>
		</td>
		<td align="center">	
			<button  type="reset" name="submit"  value="Reset">
    	<b> &nbsp;  &nbsp; Reset &nbsp;  &nbsp; </b>
		</button></td></tr>
		</table>
		<div align="center" class="g-recaptcha" data-sitekey="6LcTSgcTAAAAADt8zloaqUlMgeHSUqqHXkkK9pR4"></div>
		</form>
  
 
</div>
</div>	
 <div id="title" align="center">
 <div align="right">AITAM v1.0 &nbsp; &nbsp;</div>
 </div>
<div id="" style="height:10px">

</div>
 <script>
 function extension(){
if (chrome.app.isInstalled) {
	alert("Extension Installed");
}
else{
	chrome.webstore.install();
}
 }
</script>
</body>
</html>