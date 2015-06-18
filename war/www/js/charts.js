

function mySummaryChart(str){
    
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
	      pieSliceText: 'value',
	      is3D: true,

            chartArea: {width: '100%',height:'100%'},
        legend: {position: 'top', textStyle: {color: 'blue', fontSize: 8},maxLines: 6,alignment: 'start'},
        titleTextStyle: {fontSize: 14},    
	    };
	    var chart = new google.visualization.PieChart(document.getElementById('mySummaryChart'));
	    chart.draw(data, options);
}

function myPerfometer(str){
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
            title : 'Performance Analysis',

            vAxis: {title: "Perfometer"},
            hAxis: {title: "Days"},
            seriesType: "bars",
            series: {4: {type: "line"}},
            animation:{duration: 1000,easing: 'out',startup:true},
             hAxis: {viewWindow: {min:0, max:MAX }},
             chartArea: {width: '95%',height:'35%'},
                legend: {position: 'top', textStyle: {color: 'blue', fontSize: 12},maxLines: 6},
                titleTextStyle: {fontSize: 14},

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
               width: 90, height: 90,
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

function ourSummaryChart(str) {

		var empData=str.split(" ");
		for(var i=0;i<11;i++){
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
        chartArea: {width: '100%',height:'100%'},
        legend: {position: 'top', textStyle: {color: 'blue', fontSize: 8},maxLines: 6,alignment: 'start'},
        titleTextStyle: {fontSize: 14},    
	    
	    };
	
	    var chart = new google.visualization.PieChart(document.getElementById('ourSummaryChart'));
	    chart.draw(data, options);
  }

//our Perfometer

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
		    vAxis: {title: "Perfometer"},
		    hAxis: {title: "Days"},
		    seriesType: "bars",
		    series: {4: {type: "line"}},
			animation:{duration: 1000,easing: 'out',startup:true},
			 hAxis: {viewWindow: {min:0, max:MAX }},
			 chartArea: {width: '95%',height:'35%'},
                legend: {position: 'top', textStyle: {color: 'blue', fontSize: 12},maxLines: 6},
                titleTextStyle: {fontSize: 14},
			
		  };
	var optionsEmp = {
		    title : 'Performance analysis of your Team',
		    vAxis: {title: "Perfometer"},
		    hAxis: {title: "Days"},
		    seriesType: "bars",
		    series: {4: {type: "line"}},
			animation:{duration: 1000,easing: 'out',startup:true},
			 hAxis: {viewWindow: {min:0, max:teamLength }},
            chartArea: {width: '95%',height:'35%'},
                legend: {position: 'top', textStyle: {color: 'blue', fontSize: 12},maxLines: 6},
                titleTextStyle: {fontSize: 14},
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
	    	  options1['title'] = 'Performance analysis of your Team';
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
		document.getElementById('OurzoomDiv').style.display='block';
		document.getElementById('memOur').style.display='block';
	 drawChart();
}
