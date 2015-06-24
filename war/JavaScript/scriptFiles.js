/**
 * 
 */




  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', '{{Google Analytics Tracking ID}}', 'auto');
  ga('send', 'pageview');

function getBrowser(){
	var nVer = navigator.appVersion;
	var nAgt = navigator.userAgent;
	var browserName  = navigator.appName;
	var fullVersion  = ''+parseFloat(navigator.appVersion); 
	var majorVersion = parseInt(navigator.appVersion,10);
	var nameOffset,verOffset,ix;

	// In Opera 15+, the true version is after "OPR/" 
	if ((verOffset=nAgt.indexOf("OPR/"))!=-1) {
	 browserName = "Opera";
	 fullVersion = nAgt.substring(verOffset+4);
	}
	// In older Opera, the true version is after "Opera" or after "Version"
	else if ((verOffset=nAgt.indexOf("Opera"))!=-1) {
	 browserName = "Opera";
	 fullVersion = nAgt.substring(verOffset+6);
	 if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	   fullVersion = nAgt.substring(verOffset+8);
	}
	// In MSIE, the true version is after "MSIE" in userAgent
	else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
	 browserName = "Microsoft Internet Explorer";
	 fullVersion = nAgt.substring(verOffset+5);
	}
	// In Chrome, the true version is after "Chrome" 
	else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
	 browserName = "Chrome";
	 fullVersion = nAgt.substring(verOffset+7);
	}
	// In Safari, the true version is after "Safari" or after "Version" 
	else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {
	 browserName = "Safari";
	 fullVersion = nAgt.substring(verOffset+7);
	 if ((verOffset=nAgt.indexOf("Version"))!=-1) 
	   fullVersion = nAgt.substring(verOffset+8);
	}
	// In Firefox, the true version is after "Firefox" 
	else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
	 browserName = "Firefox";
	 fullVersion = nAgt.substring(verOffset+8);
	}
	// In most other browsers, "name/version" is at the end of userAgent 
	else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) < 
	          (verOffset=nAgt.lastIndexOf('/')) ) 
	{
	 browserName = nAgt.substring(nameOffset,verOffset);
	 fullVersion = nAgt.substring(verOffset+1);
	 if (browserName.toLowerCase()==browserName.toUpperCase()) {
	  browserName = navigator.appName;
	 }
	}
	// trim the fullVersion string at semicolon/space if present
	if ((ix=fullVersion.indexOf(";"))!=-1)
	   fullVersion=fullVersion.substring(0,ix);
	if ((ix=fullVersion.indexOf(" "))!=-1)
	   fullVersion=fullVersion.substring(0,ix);

	majorVersion = parseInt(''+fullVersion,10);
	if (isNaN(majorVersion)) {
	 fullVersion  = ''+parseFloat(navigator.appVersion); 
	 majorVersion = parseInt(navigator.appVersion,10);
	}
	return(browserName+' '+majorVersion);
	  
	
	 
}

function onloadActive(str,chart,ourChart){
	if(str=="Home"){
		summary(chart);
		ourSummary(ourChart);
	}
	if (str=="list1" || str=="list2" ||str=="list3" ||str=="list4" ||str=="list5" ||str=="list6" ||str=="list7"){
		activeClass(str);
	}
	if (str=="list11" || str=="list12" ||str=="list13" ||str=="list14" ||str=="list15" ||str=="list16"){
		activeClass1(str);
	}
}

function LoginAjax(str)
{
var xmlhttp;    
if (str=="")
  {
  document.getElementById("txtHint0").innerHTML="";
  return;
  }
else
{
	  document.getElementById("txtHint0").innerHTML=  '<img width="250px" src="images/gears-animation.gif"/>';
	  
	  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHint0").innerHTML=xmlhttp.responseText;
    }
  };
if (str== "list1"){
xmlhttp.open("POST","myLogin.jsp?q="+str,true);
}
if (str== "list2"){
	xmlhttp.open("POST","Register.jsp?q="+str,true);
	}
if (str== "list3"){
	xmlhttp.open("POST","ResetPassword.jsp?q="+str,true);
	}
xmlhttp.send();
}



function LoginactiveClass(ActiveList) {

    // this adds the 'active' class to the classes that the element already has
     
	if (ActiveList != 'list1'){
	changeClass('list1');
	}
    
	if (ActiveList != 'list2'){
	changeClass('list2');
	}
	
	if (ActiveList != 'list3'){
		changeClass('list3');
		}
		
    document.getElementById(ActiveList).classList.add('active');
    if (ActiveList != 'list1'){
    	LoginAjax(ActiveList);
    }
    
};

function myAjax(str)
{
var xmlhttp;    
if (str=="")
  {
  document.getElementById("txtHint").innerHTML="";
  
  return;
  }
else
{		
	
	  document.getElementById("txtHint").innerHTML=  '<img width="250px" src="images/gears-animation.gif"/>'
	  + "\n You are almost there .. .. ..";
	  
	  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
    if (str== "list7"){
    	var td=getToday();
    	document.getElementById('from').setAttribute("max", td);
    	document.getElementById('to').setAttribute("max", td);
    	td=getDay(12);
    	document.getElementById('from').setAttribute("min", td);
    	getChart(document.getElementById('ChartValue').value);
    }
    if (str== "list5"){
    	var td=getToday();
    	document.getElementById('dat').setAttribute("min", td);
    }
    }
  };
if (str== "list1"){
	xmlhttp.open("POST","Notification.jsp?q="+str,true);
	}
if (str== "list2"){
	xmlhttp.open("POST","ViewMyNewTask.jsp?q="+str,true);
	}
if (str== "list3"){
	xmlhttp.open("POST","viewMyTask.jsp?q="+str,true);
	}
if (str== "list4"){
	xmlhttp.open("POST","ViewMyCompleted.jsp?q="+str,true);
	}
if (str== "list5"){
	xmlhttp.open("POST","CreateMyTask.jsp?q="+str,true);
	}
if (str== "list7"){
	xmlhttp.open("POST","myPerfometer.jsp?q="+str,true);
	}
xmlhttp.send();
}

function ourAjax(str)
{
var xmlhttp;    
if (str=="")
  {
  document.getElementById("txtHint1").innerHTML="";
  
  return;
  }
else
{		
		  document.getElementById("txtHint1").innerHTML= '<img width="250px" src="images/gears-animation.gif"/>'
			  + "\n You are almost there .. .. ..";
	  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHint1").innerHTML=xmlhttp.responseText;
    if (str== "list16"){
    	var td=getToday();
    	document.getElementById('Ourfrom').setAttribute("max", td);
    	document.getElementById('Ourto').setAttribute("max", td);
    	td=getDay(6);
    	document.getElementById('Ourfrom').setAttribute("min", td);
    	getOurChart(document.getElementById('ourChartValue').value);
    }
    if (str== "list15"){
    	buildTree(document.getElementById('buildTreeChart').value);
    }
    if (str== "list14"){
    	var td=getToday();
    	document.getElementById('mDate').setAttribute("min", td);
    }
    }
  };

if (str== "list11"){
	xmlhttp.open("POST","ReviewOurTask.jsp?q="+str,true);
	}
if (str== "list12"){
	xmlhttp.open("POST","ViewOurTask.jsp?q="+str,true);
	}
if (str== "list13"){
	xmlhttp.open("POST","ViewOurCompleted.jsp?q="+str,true);
	}
if (str== "list14"){
	xmlhttp.open("POST","CreateOurTask.jsp?q="+str,true);
	}
if (str== "list15"){
	xmlhttp.open("POST","TeamTree.jsp?q="+str,true);
	}
if (str== "list16"){
	xmlhttp.open("POST","ourPerfometer.jsp?q="+str,true);
	}
xmlhttp.send();
}

function activeClass(ActiveList) {

    // this adds the 'active' class to the classes that the element already has
     
	if (ActiveList != 'list1'){
	changeClass('list1');
	}
    
	if (ActiveList != 'list2'){
	changeClass('list2');
	}
	
	if (ActiveList != 'list3'){
	changeClass('list3');
	}
	
	if (ActiveList != 'list4'){
	changeClass('list4');
	}
	
	if (ActiveList != 'list5'){
		changeClass('list5');
		}
	
	
	if (ActiveList != 'list7'){
		changeClass('list7');
		}
	
		
    document.getElementById(ActiveList).classList.add('active');
    document.getElementById("txtHint").style.display='block';

	myAjax(ActiveList);
	
   
}

function activeClass1(ActiveList) {

    // this adds the 'active' class to the classes that the element already has
     
	
	if (ActiveList != 'list11'){
		changeClass('list11');
		}
	if (ActiveList != 'list12'){
		changeClass('list12');
		}
	if (ActiveList != 'list13'){
		changeClass('list13');
		}
	if (ActiveList != 'list14'){
		changeClass('list14');
		}
	if (ActiveList != 'list15'){
		changeClass('list15');
		}
	if (ActiveList != 'list16'){
		changeClass('list16');
		}
    document.getElementById(ActiveList).classList.add('active');
    document.getElementById("txtHint1").style.display='block';
    document.getElementById("txtHint").style.display='none';
	ourAjax(ActiveList);
   
  
};

function changeClass(otherList){
	 if ( document.getElementById(otherList).classList.contains('active') ){

		    document.getElementById(otherList).classList.remove('active');
		  //  document.getElementById(otherList).classList.add('other');
		    }
	
}

function enableRow(r) {
	if (r==2){
		if(document.getElementById("s1Assignee").value == "select"){
			 alert('Please Select Assignee for this activity');
		 }
		else{
			 document.getElementById("tr2").style.display = 'table-row';
		}
	
	 
	}
	if (r==3){
		if(document.getElementById("s2Assignee").value == "select"){
			 alert('Please Select Assignee for this activity');
		 }
		else{
	    document.getElementById("tr3").style.display = 'table-row';}
	    }
	if (r==4){
		if(document.getElementById("s3Assignee").value == "select"){
			 alert('Please Select Assignee for this activity');
		 }
		else{
	    document.getElementById("tr4").style.display = 'table-row';}
	}
	if (r==5){
		if(document.getElementById("s4Assignee").value == "select"){
			 alert('Please Select Assignee for this activity');
		 }
		else{
	    document.getElementById("tr5").style.display = 'table-row';}
	}
}

function disableRow(r) {
	row="s"+r;
	dest= document.getElementById(row);
	dest.value="";
	if (r==2){
 
	 document.getElementById("tr2").style.display = 'none';
	 dest.value="";}
	if (r==3){

	    document.getElementById("tr3").style.display = 'none';
	    dest.value="";}
	if (r==4){

	    document.getElementById("tr4").style.display = 'none';
	    dest.value="";}
	if (r==5){

	    document.getElementById("tr5").style.display = 'none';
	    dest.value="";}
}

function copyText(from,to) {

    src = document.getElementById(from);
    dest = document.getElementById(to);
    dest.value = src.value;

    
}

function RowSelected(row){
	dest= document.getElementById(row);
	dest.value="selected";
}

function visible(id){

	document.getElementById(id).style.display = 'block';
}

function hide(id){

	document.getElementById(id).style.display = 'none';
}


function description(desc){

	alert(desc);
	
}

function CurDate(){
	return new Date();
}

function confirmPW(){
	if(document.getElementById("pw").value != document.getElementById("con").value){
		alert('Your entered Passwords does not match. Kindly try again  ');
		return false;
	}
}
function startTime() {
    var today=new Date();
    var h=today.getHours();
    var m=today.getMinutes();
    var s=today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('time').innerHTML = "IST: "+h+":"+m+":"+s;
    var t = setTimeout(function(){startTime();},500);
}

function checkTime(i) {
    if (i<10) {
    	i = "0" + i;
    	};  // add zero in front of numbers < 10
    return i;
}

function AdminactiveClass(ActiveList,str) {

    // this adds the 'active' class to the classes that the element already has
	if(ActiveList=="Home"){
		buildTeam(str);
	}
	if (ActiveList != 'list1'){
	changeClass('list1');
	}
    
	if (ActiveList != 'list2'){
	changeClass('list2');
	}
	
	
    document.getElementById(ActiveList).classList.add('active');
    AdminAjax(ActiveList);

};

function AdminAjax(str)
{
var xmlhttp;    
if (str=="")
  {
  document.getElementById("txtHint").innerHTML="";
  return;
  }
else
{
	  document.getElementById("txtHint").innerHTML=  '<img width="250px" src="images/gears-animation.gif"/>';
	  
	  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
    }
  };

if (str== "list1"){
	xmlhttp.open("POST","Manager.jsp?q="+str,true);
	}
if (str== "list2"){
	xmlhttp.open("POST","Member.jsp?q="+str,true);
	}
xmlhttp.send();
}


//Current Manager
function getManager(str) {
    if (str.length == 0) { 
        document.getElementById("currentManager").innerHTML = "";
        return;
    } else {
        var xmlhttp = new XMLHttpRequest();
        xmlhttp.onreadystatechange = function() {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                document.getElementById("currentManager").innerHTML = xmlhttp.responseText;
            }
        }
        xmlhttp.open("POST", "getManager.jsp?q=" + str, true);
        xmlhttp.send();
    }
}

function checkAssign(){
	var mem1=document.getElementById("mem1").value;
	var man1=document.getElementById("man1").value;
	if((mem1=="select")){
		alert('You Have not selected a Member. Kindly select and try again');
		return false;
	}
	else if((man1=="select")){
		alert('You Have not selected a Manager. Kindly select and try again');
		return false;
	}	
}

function checkReAssign(){
	var mem2=document.getElementById("mem2").value;
	var man2=document.getElementById("man2").value;
	if((mem2=="select")){
		alert('You Have not selected a Member. Kindly select and try again');
		return false;
	}
	else if((man2=="select")){
		alert('You Have not selected a Manager. Kindly select and try again');
		return false;
	}
	
}

function getToday(){
	var d = new Date();
	var yr=d.getFullYear();
	var mon=d.getMonth()+1;
	if(mon<10){
	 mon="0"+mon;
	}
	var dt=d.getDate();
	var td=yr+"-"+mon+"-"+dt;
	return td;
}
function getDay(buf){
	var buf=parseInt(buf);
	var d = new Date();
	var mon=d.getMonth()+1-buf;
	if(mon<0){
	temp=mon;
	mon=12+temp;
	}
	if(mon<10){
	 mon="0"+mon;
	}
	temp=Math.ceil(-(temp/12));
	var yr=d.getFullYear()-temp;
	var dt=d.getDate();
	var td=yr+"-"+mon+"-"+dt;
	return td;
}

function myMin(dt,target){
	document.getElementById(target).setAttribute("min", dt);
}

function ProfileactiveClass(ActiveList) {

    // this adds the 'active' class to the classes that the element already has
     
	if (ActiveList != 'list1'){
	changeClass('list1');
	}
    
	if (ActiveList != 'list2'){
	changeClass('list2');
	}
	
	if (ActiveList != 'list3'){
		changeClass('list3');
		}
	
    document.getElementById(ActiveList).classList.add('active');
    ProfileAjax(ActiveList);

};

function ProfileAjax(str)
{
var xmlhttp;    
if (str=="")
  {
  document.getElementById("txtHint").innerHTML="";
  return;
  }
else
{
	  document.getElementById("txtHint").innerHTML=  '<img width="250px" src="images/gears-animation.gif"/>';
	  
	  }
if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
  xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
xmlhttp.onreadystatechange=function()
  {
  if (xmlhttp.readyState==4 && xmlhttp.status==200)
    {
    document.getElementById("txtHint").innerHTML=xmlhttp.responseText;
    }
  };
if (str== "list1"){
xmlhttp.open("POST","Details.jsp?q="+str,true);
}
if (str== "list2"){
	xmlhttp.open("POST","Credentials.jsp?q="+str,true);
	}
if (str== "list3"){
	xmlhttp.open("POST","Feedback.jsp?q="+str,true);
	}
xmlhttp.send();
}
