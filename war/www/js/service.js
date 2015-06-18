angular.module('liveStatus', []).factory('LiveStatus', function($rootScope){
    var service = {};
    service.newCount;
    service.openCount;
    service.archiveCount;
    service.ourReviewCount;
    service.ourOpenCount;
    service.ourArchiveCount;
    
// Update My Task count
    
    service.updatenewCount = function(value){
        this.newCount = checkNull(value);
        $rootScope.$broadcast("valuesUpdated");
    }
    service.updateopenCount = function(value){
        this.openCount = checkNull(value);
        $rootScope.$broadcast("valuesUpdated");
    }
    service.updatearchiveCount = function(value){
        this.archiveCount = checkNull(value);
        $rootScope.$broadcast("valuesUpdated");
    }
    
// update OurTask count
    
    service.updateourReviewCount = function(value){
        this.ourReviewCount = checkNull(value);
        $rootScope.$broadcast("ourvaluesUpdated");
    }
    service.updateourOpenCount = function(value){
        this.ourOpenCount = checkNull(value);
        $rootScope.$broadcast("ourvaluesUpdated");
    }
    service.updateourArchiveCount = function(value){
        this.ourArchiveCount = checkNull(value);
        $rootScope.$broadcast("ourvaluesUpdated");
    }
    
  // Misc
    
    service.sayLogIn = function(){
        $rootScope.$broadcast("loggedIn");
    }
    service.saySwitch = function(){
        $rootScope.$broadcast("SwitchUser");
    }
    service.refreshOpen = function(){
        $rootScope.$broadcast("refreshOpen");
    }
    return service;
});

function checkNull(value){
    if (value=="0"){
            value="";
    }
    return value;
}

function change(animOut,animIn){
    var animOut=document.getElementById(animOut);
    var animIn=document.getElementById(animIn);
    animOut.className=animOut.className + " animOut"; 
    var goAway=setInterval(function () { animOut.style.display = 'none';}, 900);
    var comeIn=setInterval(function () { animIn.style.display = ''; 
                                        animIn.className=animIn.className + " animIn"; 
                                        clearInterval(goAway);
                                        
                                       }, 900);
     var clearAnim=setInterval(function () { 
                                        animOut.classList.remove("animOut");
                                        animOut.classList.remove("animIn");
                                        animIn.classList.remove("animOut");
                                        animIn.classList.remove("animIn");
                                        clearInterval(clearAnim);
                                        clearInterval(comeIn);
                                        }, 1800);
    
}

function defaultDates(dur){
    var d = new Date(),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;
    toDate=year + '-' + month + '-' + day;
    
    
    if(month > dur){
        month= month-dur;
        month=month.toString();
    }
    else{
         month=month-dur+12;
         year =year -1;
    }
    if (month.length < 2) {month = '0' + month};
    fromDate=year + '-' + month + '-' + day;
    return (fromDate+";"+toDate);
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