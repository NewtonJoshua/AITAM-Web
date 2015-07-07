/**
 * 
 */

function getChart(str){
	

	var splitted=str.split(",");
	var len=splitted.length;
	var MAX = len-1;
	var zMax = MAX/3;
	var step= zMax/2;
	if(step<1){
		step=1;
	}
	var temp=splitted[0].split(" ");
	var myData= [];
	var total=0;
	myData[0]=temp;
	for (var i=1;i<len;i++){
		var temp= splitted[i].split(" ");
			myData[i]=temp;
			total=total+parseFloat(myData[i][5]);
			for(var j=1;j<6;j++){
			myData[i][j]=parseFloat(myData[i][j]);
			}
			
	}

	total=parseFloat(total/MAX).toFixed(2);
	var start=new Date(myData[1][0]).toDateString();

	var end=new Date(myData[MAX][0]).toDateString();
	
	document.getElementById('details').innerHTML='<b>From:</b>   '+start+'<br><b>To:</b>   '+end;
	var data1 = google.visualization.arrayToDataTable(myData);

	


  var options1 = {
    title : 'myPerfometer- Self Performance Analysis',
	width: 1000,
      height: 250,
    vAxis: {title: "Perfometer"},
    hAxis: {title: "Days"},
    seriesType: "bars",
    series: {4: {type: "line"}},
	animation:{duration: 1000,easing: 'out',startup:true},
	 hAxis: {viewWindow: {min:0, max:MAX }},
	 chartArea: {width: '75%',height:'55%'},

  };
  
  
  
  


  var prevButton = document.getElementById('prev');
  var nextButton = document.getElementById('next');
  var changeZoomButton = document.getElementById('zoom');
  var chart1 = new google.visualization.ComboChart(document.getElementById('chart_div'));




prevButton.onclick = function() {
      options1.hAxis.viewWindow.min -= step;
      options1.hAxis.viewWindow.max -= step;
      drawChart();
    };
    nextButton.onclick = function() {
      options1.hAxis.viewWindow.min += step;
      options1.hAxis.viewWindow.max += step;
      drawChart();
    };
    var zoomed = true;
    changeZoomButton.onclick = function() {
      if (zoomed) {
        options1.hAxis.viewWindow.min = 0;
        options1.hAxis.viewWindow.max = zMax ;
      } else {
        options1.hAxis.viewWindow.min = 0;
        options1.hAxis.viewWindow.max = MAX;
      }
      zoomed = !zoomed;
      drawChart();
    };

 function drawChart() {
      // Disabling the button while the chart is drawing.
      prevButton.disabled = true;
      nextButton.disabled = true;
      changeZoomButton.disabled = true;
      google.visualization.events.addListener(chart1, 'ready',
          function() {
            prevButton.disabled = options1.hAxis.viewWindow.min <= 0;
            nextButton.disabled = options1.hAxis.viewWindow.max >= MAX;
            changeZoomButton.disabled = false;
          });
      chart1.draw(data1, options1);
    }
 
 //guage
 var chart = new google.visualization.Gauge(document.getElementById('summary'));
 var total1=parseFloat(total);
	var comments;
	if(total1<1.25){
	comments='Deficient';
	}
	else if(total1<2.5){
	comments='Sufficient';
	}
	else if(total1<3.75){
	comments='Good';
	}
	else{
	comments='Excellent';
	}
     var data = google.visualization.arrayToDataTable([
       ['Label', 'Value'],
       [comments, total1]  ]);

     var options = {
       width: 100, height: 100,
       redFrom: 0, redTo: 1.25,
       yellowFrom:1.25, yellowTo: 2.5,
		greenFrom:3.75, greenTo:5,
       minorTicks: 5,
		max:5,
		
     };

     

     chart.draw(data, options);


 document.getElementById('zoomDiv').style.display='block';
  chart1.draw(data1, options1);
}




function getOurChart(str){
	
	var z=str.split(";");
	var team=z[0];
	var teamSplit=team.split(",");
	var teamData=[];
	var teamTotal=0;
	teamData[0]=teamSplit[0].split(" ");
	var teamLength=teamSplit.length-1;
	for (memNo=1;memNo<teamSplit.length;memNo++){
		empData=teamSplit[memNo].split(":");
	
		NameMem=empData[0];
		empDetails=empData[1].split(" ");

		for (var xyz=0; xyz<empDetails.length; xyz++){
			empData[xyz+1]=parseFloat(empDetails[xyz]);
		}
		teamTotal=teamTotal+empData[5];
		teamData[memNo]=empData;
		
	}
	teamTotal=parseFloat(teamTotal/(teamSplit.length)).toFixed(2);
	teamTotal1=parseFloat(teamTotal);
	
	var y=0;
	var memName= [];
	var memID=[];
	var data1 = [];
	var data = [];
	var MAXn = 1;
	var zMaxn = 1;
	var stepn= 1;
	var MAX=1;
	var zMax=1;
	var step=1;
	
	var comments;
	if(teamTotal1<1.25){
	comments='Deficient';
	}
	else if(teamTotal1<2.5){
	comments='Sufficient';
	}
	else if(teamTotal1<3.75){
	comments='Good';
	}
	else{
	comments='Excellent';
	}
	
	data1[0]=google.visualization.arrayToDataTable(teamData);
	data[0] = google.visualization.arrayToDataTable([
                                                     ['Label', 'Value'],
                                                     [comments, teamTotal1]  ]);
	for (y=1; y<z.length; y++){

		var g=z[y];
		var mem=g.split(":");
		var id=mem[0];
		memID[y]=id;
		var name=mem[1];
		memName[y]=name;
		var memData=mem[2];
		var splitted=memData.split(",");
		var len=splitted.length;
		var MAXn = len-1;
		var zMaxn = MAXn/3;
		var stepn= zMaxn/2;
		if(stepn<1){
			stepn=1;
		}
		var temp=splitted[0].split(" ");
		var myData= [];
		var total=0;
		myData[0]=temp;
		for (i=1;i<len;i++){
			var temp= splitted[i].split(" ");
				myData[i]=temp;
				total=total+parseFloat(myData[i][5]);
				for(j=1;j<6;j++){
				myData[i][j]=parseFloat(myData[i][j]);
				}
				
		}
		
		var total=parseFloat(total/MAXn).toFixed(2);
		var start=new Date(myData[1][0]).toDateString();
		var end=new Date(myData[MAXn][0]).toDateString();
		document.getElementById('Ourdetails').innerHTML='<b>From:</b>   '+start+'<br><b>To:</b>   '+end;
		var datatemp= google.visualization.arrayToDataTable(myData);
		data1[y] = datatemp;
		var total1=parseFloat(total);
		var comments;
		if(total1<1.25){
		comments='Deficient';
		}
		else if(total1<2.5){
		comments='Sufficient';
		}
		else if(total1<3.75){
		comments='Good';
		}
		else{
		comments='Excellent';
		}
		
		   data[y] = google.visualization.arrayToDataTable([
		                                                     ['Label', 'Value'],
		                                                     [comments, total1]  ]);
}	
	var chart = new google.visualization.Gauge(document.getElementById('Oursummary'));
	var current=0;
	
	 
	var prevButton = document.getElementById('Ourprev');
	  var nextButton = document.getElementById('Ournext');
	  var changeZoomButton = document.getElementById('Ourzoom');
	  
	  prevButton.onclick = function() {
		  if (current==0){
				MAX=teamLength;
				zMax=teamLength/2;
				step=teamLength/4;
			}
			else{
				MAX=MAXn;
				zMax=zMaxn;
				step=stepn;
			}
	      options1.hAxis.viewWindow.min -= step;
	      options1.hAxis.viewWindow.max -= step;
	      drawChart();
	    }
	    nextButton.onclick = function() {
	    	if (current==0){
	    		MAX=teamLength;
	    		zMax=teamLength/2;
	    		step=teamLength/4;
	    	}
	    	else{
	    		MAX=MAXn;
	    		zMax=zMaxn;
	    		step=stepn;
	    	}
	      options1.hAxis.viewWindow.min += step;
	      options1.hAxis.viewWindow.max += step;
	      drawChart();
	    }
	    var zoomed = true;
	    changeZoomButton.onclick = function() {
	    	if (current==0){
	    		MAX=teamLength;
	    		zMax=teamLength/2;
	    		step=teamLength/4;
	    	}
	    	else{
	    		MAX=MAXn;
	    		zMax=zMaxn;
	    		step=stepn;
	    	}
	    	
	      if (zoomed) {
	        options1.hAxis.viewWindow.min = 0;
	        if (current==0){
	        	options1.hAxis.viewWindow.max = parseInt(teamLength/2); 
	     
	        }
	        else{
	        	options1.hAxis.viewWindow.max = parseInt(zMaxn) ;
	       
	        }
	        
	      } else {
  
	        options1.hAxis.viewWindow.min = 0;
	        if (current==0){
	     
	        }
	        else{
	        	options1.hAxis.viewWindow.max = parseInt(MAXn) ;
	
	        }
	      }
	      zoomed = !zoomed;
	      drawChart();
	    }
	    
	var options1 = {
		    title : 'Day wise Perfometer',
			width: 1000,
		      height: 250,
		    vAxis: {title: "Perfometer"},
		    hAxis: {title: "Days"},
		    seriesType: "bars",
		    series: {4: {type: "line"}},
			animation:{duration: 1000,easing: 'out',startup:true},
			 hAxis: {viewWindow: {min:0, max:MAX }},
			 chartArea: {width: '75%',height:'55%'},
			
		  };
	var optionsEmp = {
		    title : 'ourPerfometer- Performance analysis of your Team',
			width: 1000,
		      height: 250,
		    vAxis: {title: "Perfometer"},
		    hAxis: {title: "Days"},
		    seriesType: "bars",
		    series: {4: {type: "line"}},
			animation:{duration: 1000,easing: 'out',startup:true},
			 hAxis: {viewWindow: {min:0, max:teamLength }}

		  };
	 var options = {
		       width: 100, height: 100,
		       redFrom: 0, redTo: 1.25,
		       yellowFrom:1.25, yellowTo: 2.5,
				greenFrom:3.75, greenTo:5,
		       minorTicks: 5,
				max:5,
				animation:{duration: 1000,easing: 'out',startup:true}
		     };
	 
	
	
	var chart1 = new google.visualization.ComboChart(document.getElementById('Ourchart_div'));
	
	var select = document.getElementById('ourMembers');
	select.onchange = function() {
  	  current = document.getElementById('ourMembers').value;
       
        drawChart();
      }
    
    
	function drawChart() {
	      // Disabling the button while the chart is drawing.
		var select = document.getElementById('ourMembers');
	      select.disabled = true;
	      prevButton.disabled = true;
	      nextButton.disabled = true;
	      changeZoomButton.disabled = true;
	      google.visualization.events.addListener(chart1, 'ready',
	          function() {
	    	  prevButton.disabled = options1.hAxis.viewWindow.min <= 0;
	    	  
	            nextButton.disabled = options1.hAxis.viewWindow.max >= MAX;
	            changeZoomButton.disabled = false;
	    	  select.disabled = false;
	          });
	      
	      if (current==0){
	    	  options1['title'] = 'ourPerfometer- Performance analysis of your Team';
	    	  options1['hAxis']= {viewWindow: {min:0, max:teamLength }};
	    	  var zoomed = false;
	    	 
	      }
	      else{
	    	  options1['title'] = memName[current]+' ('+memID[current]+')' + '- Perfometer';
	    	  options1['hAxis']= {viewWindow: {min:0, max:MAXn }};
	    	  var zoomed = false;
	      }
	      if (current==0){
	  		MAX=teamLength;
	  		zMax=teamLength/2;
	  		step=teamLength/4;
	  	}
	  	else{
	  		MAX=MAXn;
	  		zMax=zMaxn;
	  		step=stepn;
	  	}
	      chart1.draw(data1[current], options1);
	      chart.draw(data[current], options);
	    }
		//document.getElementById('OurzoomDiv').style.display='block';
		document.getElementById('memOur').style.display='block';
	 drawChart();
	        
	
}



function onloadimg(){
	document.getElementById("Ourchart_div").innerHTML= '<img width="200px" src="images/gears-animation.gif"/>'
		  + "\n You are almost there .. .. ..";
}
function myOnloading(){
	document.getElementById("chart_div").innerHTML= '<img width="200px" src="images/gears-animation.gif"/>'
		  + "\n You are almost there .. .. ..";
	
}

function buildTree(str) {

	var empData=str.split(";");
	len=empData.length -1;

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Name');
    data.addColumn('string', 'Manager');
    data.addColumn('string', 'ToolTip');
    
    for(var i=0; i<(len+1); i++){
    	var details=empData[i].split(",");
    	var id=details[0];
    	var name=details[1];
    	var phone=details[2];
    	var man=details[3];
    	if(man==0){
    		man='';
    	}
    	data.addRows([
    	              [{v:id, f:'<b>'+name+'</b><div style="color:black;width: 120px; font-style:italic"><img width="10px" 
    	              src="images/phone.png"/>'+ phone+'</div>'}, man, 'Emp Id:'+id]
    	              ]);
    }

    var chart = new google.visualization.OrgChart(document.getElementById("tree_div"));
    chart.draw(data, {allowHtml:true,width: 1000,height: 250,allowCollapse:true });
    
  }

function buildTeam(str) {
	var empData=str.split(";");
	len=empData.length -1;

    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Name');
    data.addColumn('string', 'Manager');
    data.addColumn('string', 'ToolTip');
    
    for(var i=1; i<(len+1); i++){
    	var details=empData[i].split(",");
    	var id=details[0];
    	var name=details[1];
    	var phone=details[2];
    	var man=details[3];
    	if(man==0){
    		man='';
    	}
    	data.addRows([
    	              [{v:id, f:'<b>'+name+'</b><div style="color:black;width: 120px; font-style:italic"><img width="10px" 
    	              src="images/phone.png"/>'+ phone+'</div>'}, man, 'Emp Id:'+id]
    	              ]);
    }

    var chart = new google.visualization.OrgChart(document.getElementById("txtHint"));
    chart.draw(data, {allowHtml:true,width: 1000,height: 250,allowCollapse:true });
    
  }

function summary(str) {
		
		var empData=str.split(" ");
		for(var i=0;i<11;i++){
			empData[i]=parseInt(empData[i]);
		}
	    var data = google.visualization.arrayToDataTable([
	      ['Status', 'Counts'],
	      ['New Tasks',     empData[0]],
	      ['Accepted', empData[1]],
	      ['In Progress',empData[2]],
	      ['Review',empData[3]],
	      ['Appeal', empData[4]],
	      ['Appeal Accepted', empData[5]],
	      ['Appeal Declined', empData[6]],
	      ['Approve', empData[7]],
	      ['Approve Accepted', empData[8]],
	      ['Approve Declined', empData[9]],
	      ['Rework', empData[10]]
	    ]);
	    
	    var options = {
	      title: 'myTasks Status- Summary (New: '+ empData[0]+',  Open: '+empData[11]+', Archived: '+empData[12]+')' ,
	      pieSliceText: 'value',
	      is3D: true,
	    };
	
	    var chart = new google.visualization.PieChart(document.getElementById('txtHint'));
	    chart.draw(data, options);

  }

function ourSummary(str) {

		var empData=str.split(" ");
		for(var i=0;i<empData.length;i++){
			empData[i]=parseInt(empData[i]);
		}
	    var data = google.visualization.arrayToDataTable([
	      ['Status', 'Counts'],
	      ['For Review',empData[3]],
	      ['Appeal', empData[4]],
	      ['Approve', empData[7]],
	      ['New Tasks',     empData[0]],
	      ['Accepted', empData[1]],
	      ['In Progress',empData[2]],
	      ['Appeal Accepted', empData[5]],
	      ['Appeal Declined', empData[6]],
	      ['Approve Accepted', empData[8]],
	      ['Approve Declined', empData[9]],
	      ['Rework', empData[10]]
	    ]);
	    
	    var options = {
	      title: 'ourTasks Status- Summary (Review: '+ empData[13]+',  Open: '+empData[11]+', Archived: '+empData[12]+')',
	      pieSliceText: 'value',
	      is3D: true,
	    };
	
	    var chart = new google.visualization.PieChart(document.getElementById('txtHint1'));
	    chart.draw(data, options);

  }

