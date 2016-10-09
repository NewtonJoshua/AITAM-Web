var empID=localStorage.empID;
var url = "/MobileServices?EmpID=" + empID + "&";
var name;
var network;
var mySummary;
var myNewTask;
var myOpenTask;
var myArchives;
var reviewTask;
var ourOpenTask;
var ourArchives;
var changedDate;
var ourSummary;
var teamMembers;
var fingerprint = (new Fingerprint().get()) + "-" +  new Fingerprint({screen_resolution: true}).get();
var showLoading=true;

google.load('visualization', '1', {packages:["corechart","gauge","orgchart"]});
google.setOnLoadCallback(function() {
  angular.bootstrap(document.body, ['starter']);
});

angular.module('starter.controllers', ['ionic','liveStatus'])

.config(function($httpProvider) {
  $httpProvider.interceptors.push(function($rootScope) {
    return {
      request: function(config) {
          if(showLoading){
        $rootScope.$broadcast('loading:show')
          }
        return config
      },
      response: function(response) {
        $rootScope.$broadcast('loading:hide')
        return response
      }
    }
  })
})

.run(function($ionicPlatform, $ionicPopup) {
        $ionicPlatform.ready(function() {
        network= navigator.connection.type;
            if(window.Connection) {
                if(navigator.connection.type == Connection.NONE) {
                    $ionicPopup.confirm({
                        title: "Internet Disconnected",
                        content: "The internet is disconnected on your device."
                    })
                    .then(function(result) {
                        if(!result) {
                            ionic.Platform.exitApp();
                        }
                    });
                }
            }
        });
    })

.run(function($rootScope, $ionicLoading) {
    
    if(showLoading){
  $rootScope.$on('loading:show', function() {
    
            $ionicLoading.show({
             // The text to display in the loading indicator
              content: '<i class=icon ion-ios7-refreshing"></i> Loading',

              // The animation to use
              animation: 'fade-in',

              // Will a dark overlay or backdrop cover the entire view
              showBackdrop: false,

              // The maximum width of the loading indicator
              // Text will be wrapped if longer than maxWidth
              maxWidth: 200,

              // The delay in showing the indicator
              showDelay: 10
            })
    
  })

  $rootScope.$on('loading:hide', function() {
    $ionicLoading.hide()
  })
    }
})

.controller('AppCtrl', function($scope, $state, $ionicPopup, $ionicHistory, LiveStatus) {
    
 startTime();
 $scope.manager= false;
    $scope.$on('isMan', function() {
         $scope.manager= true;
    });

   
$scope.sideMenu=false;
    
    $scope.$on('loggedIn', function() {
        $scope.sideMenu=true;
    });
    $scope.$on('SwitchUser', function() {
        $scope.sideMenu=false;
    });  
    
$scope.dispStatus=function(){
     $scope.newCount = LiveStatus.newCount;
    $scope.ourReviewCount = LiveStatus.ourReviewCount;
    $scope.employee= name;
};

  
    
})


.controller('tabsCtrl', function($scope, $window, LiveStatus) {
        $scope.newCount = LiveStatus.newCount;
        $scope.openCount = LiveStatus.openCount;
        $scope.archiveCount = LiveStatus.archiveCount;
      $scope.$on('valuesUpdated', function() {
        $scope.newCount = LiveStatus.newCount;
        $scope.openCount = LiveStatus.openCount;
        $scope.archiveCount = LiveStatus.archiveCount;
    });
})

.controller('LoginCtrl', function($scope, $window, $http, $state, $ionicHistory, $ionicPopup, LiveStatus) {
    $scope.loginForm=true;
    $scope.doLogin = function() {
         empID=document.getElementById("username").value;
        pw=document.getElementById("password").value;
        showLoading=true;
        $http.post("/MobileServices?action=Login&EmpID=" + empID+"&pw="+pw)
            .success(function (response,code) {
            showLoading=false;
            var temp=  response.Login.split(";");
            name = temp[0];
            mySummary=response.mySummary;
            ourSummary=response.ourSummary;
            if(name != "incorrect"){
                url = "/MobileServices?EmpID=" + empID + "&";
                localStorage.empID= empID;
                
                //Add Device
                var device=ionic.Platform.device();
                showLoading=true;
                $http.post("/MobileServices?action=DeviceAdd&EmpID=" + empID+"&DeviceID="+fingerprint
                          +"&platform="+navigator.platform)
                .success(function (response) {
                    showLoading=false;
                    if(response.DeviceAdd== "Success"){
                        var empData=mySummary.split(" ");
                        LiveStatus.updatenewCount(empData[0]);
                        LiveStatus.updateopenCount(empData[11]);
                        LiveStatus.updatearchiveCount(empData[12]);

                        var ourempData=ourSummary.split(" ");
                        LiveStatus.updateourReviewCount(ourempData[13]);
                        LiveStatus.updateourOpenCount(ourempData[11]);
                        LiveStatus.updateourArchiveCount(ourempData[12]);

                        LiveStatus.sayLogIn();
                        $ionicHistory.nextViewOptions({
                            disableBack: true
                        });
                        $ionicHistory.clearHistory();
                        $ionicHistory.clearCache();
                        document.getElementById("password").value="";
                        $state.go('app.welcome'); 
                    }
                })
                .error(function(data,status){
                    showLoading=false;
                    alert(status);
                })
                         
                
            }
            else{
                 
                   var alertPopup = $ionicPopup.alert({
                     title: 'Invalid Credentials',
                     template: 'The username or password is inorrect. \n Kindly enter valid credentials.'
                   });
                   alertPopup.then(function(res) {
                     document.getElementById("password").value="";
                   });
                 
            }
        })
        .error(function(data,status){
            showLoading=false;
                $scope.conShow=true;
                $scope.conn=status;
        
        });
     
  };

})

.controller('WelcomeCtrl', function($scope, $window, $state, $ionicHistory, $http, $rootScope, $ionicPopup, LiveStatus) {
    if(localStorage.empID == null){
        $ionicHistory.nextViewOptions({
                        disableBack: true
            });
        $state.go('app.login'); 
    }
    else{
        empID=localStorage.empID ;
        showLoading=true;
        $http.post("/MobileServices?action=DeviceLogin&EmpID=" + empID+"&DeviceID="+fingerprint
                  +"&platform="+navigator.platform)
            .success(function (response,code) {
            showLoading=false;
            var temp=  response.Login.split(";");
            name = temp[0];
            var isMan= temp[1];
            if(isMan== "Yes"){
                $rootScope.$broadcast("isMan");
            }
            mySummary=response.mySummary;
            ourSummary=response.ourSummary;
            if(name != "incorrect"){
                url = "/MobileServices?EmpID=" + empID + "&";
                
                var empData=mySummary.split(" ");
                LiveStatus.updatenewCount(empData[0]);
                LiveStatus.updateopenCount(empData[11]);
                LiveStatus.updatearchiveCount(empData[12]);
                
                var ourempData=ourSummary.split(" ");
                LiveStatus.updateourReviewCount(ourempData[13]);
                LiveStatus.updateourOpenCount(ourempData[11]);
                LiveStatus.updateourArchiveCount(ourempData[12]);
                LiveStatus.sayLogIn();
                
            }
            else{
                   var alertPopup = $ionicPopup.alert({
                     title: 'Invalid Credentials',
                     template: 'Your Password has been changed. \n Kindly enter new credentials.'
                   });
                   alertPopup.then(function(res) {
                       $ionicHistory.nextViewOptions({
                        disableBack: true
                        });
                     $state.go('app.login');
                   });
                 
            }
        })
        .error(function(data,status){
            showLoading=false;
                alert(status);
        });
        
            $scope. employee= name;
            $scope.emp= empID;
                if(navigator.userAgent.indexOf("BB10")>-1){
                    $scope.platform="BlackBerry";
                }
                if(navigator.userAgent.indexOf("Windows")>-1){
                    $scope.platform="Windows";
                }
                if(navigator.userAgent.indexOf("Android")>-1){
                    $scope.platform="Android";
                }
                if(navigator.userAgent.indexOf("iOS")>-1){
                    $scope.platform="iOS";
                }
            $scope.fingerprint=  fingerprint;

       
        
    }
  
    $scope.logoff = function() {
       var confirmPopup = $ionicPopup.confirm({
         title: 'Switch User',
         subTitle: 'I am not '+ name,
         template: 'Are you sure you want to Login to AITAM as another user?',
        buttons: [
          { text: 'Not Really' },
          {
            text: '<b>I am sure</b>',
            type: 'button-navy',
            onTap: function(e) {
                LiveStatus.saySwitch();
                 $ionicHistory.nextViewOptions({
                        disableBack: true
                    });
                    $ionicHistory.clearHistory();
                    $ionicHistory.clearCache();
                localStorage.removeItem("empID");
                $state.go('app.login');  

            }
          }
        ]

       });
     };
    
    $scope.download= function(){
        var ext;
        
        if(navigator.userAgent.indexOf("BB10")>-1){
            ext="apk";
        }
        if(navigator.userAgent.indexOf("Windows")>-1){
            ext="zap";
        }
        if(navigator.userAgent.indexOf("Android")>-1){
            ext="apk";
        }
        if(navigator.userAgent.indexOf("iOS")>-1){
            ext="ipa";
        }
        var dlink="https://github.com/NewtonJoshua/AITAM/blob/master/AITAM."+ext+"?raw=true";
        location.href =dlink;
    }
})

.controller('ourtabsCtrl', function($scope, $window, LiveStatus) {
    $scope.ourReviewCount = LiveStatus.ourReviewCount;
        $scope.ourOpenCount = LiveStatus.ourOpenCount;
        $scope.ourArchiveCount = LiveStatus.ourArchiveCount;
      $scope.$on('ourvaluesUpdated', function() {
        $scope.ourReviewCount = LiveStatus.ourReviewCount;
        $scope.ourOpenCount = LiveStatus.ourOpenCount;
        $scope.ourArchiveCount = LiveStatus.ourArchiveCount;
    });
  
})

//myTask- Summary

.controller('mysummaryCtrl', function($scope, $http) {
    mySummaryChart(mySummary);
    $scope.doRefresh = function() {
        showLoading=true;
    $http.post(url+"action=mySummary")
    .success(function (response) {var str = response.mySummary;
        mySummaryChart(str);
        showLoading=false;                          
    })
    .error(function(data,status){
        showLoading=false;
        alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
})


//myTask- New


.controller('mynewCtrl', function($scope, $http, LiveStatus) {
    showLoading=true;
  $http.post(url+"action=MyNewTask")
  .success(function (response) {
      showLoading=false;
      $scope.tasks = response.MyNewTask;
                               myNewTask=$scope.tasks; 
                                 LiveStatus.updatenewCount(myNewTask.length);
                               })
  .error(function(data,status){
      showLoading=false;
      alert(status)});

    $scope.doRefresh = function() {
        showLoading=true;
    $http.post(url+"action=MyNewTask")
   .success(function (response) {
        showLoading=false;
        $scope.tasks = response.MyNewTask;
                                myNewTask=$scope.tasks; 
                                 LiveStatus.updatenewCount(myNewTask.length);
                                })
   .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
})

.controller('mynewdetailsCtrl', function($scope, $stateParams, $http, LiveStatus) {
    if(myNewTask==null){
            showLoading=true;
          $http.post(url+"action=MyNewTask")
          .success(function (response) {
              showLoading=false;
              $scope.tasks = response.MyNewTask;
                                       myNewTask=$scope.tasks; 
                                         LiveStatus.updatenewCount(myNewTask.length);
                                       })
          .error(function(data,status){
              showLoading=false;
              alert(status)});
    }
    var i;
    for (i=0; i<=myNewTask.length; i++){
        if(myNewTask[i].TaskID==$stateParams.taskID){
            $scope.acp=true;
            document.getElementById("myDate").value=myNewTask[i].DueDate;
            //$scope.myDate=myNewTask[i].DueDate;
            $scope.x = myNewTask[i];
            break;
        }
    }
    
    $scope.accept=function(){
        showLoading=true;
         $http.post(url+"action=Accept&id="+$stateParams.taskID)
        .success(function (response) {
             showLoading=false;
              if(response.Accept == "Succcess"){
                    myNewTask.splice(i, 1);
                    LiveStatus.updateopenCount(parseInt(LiveStatus.openCount)+1);
                    LiveStatus.updatenewCount(parseInt(LiveStatus.newCount)-1);
                    change("hideAccept","showAccepted");
                    LiveStatus.refreshOpen();                                  
                                              }
             else{
                 alert("Something went wrong");
             }
                               })
        .error(function(data,status){alert(status)});
        
    };
    
    $scope.appeal=function(){
        var appealDate= document.getElementById("myDate").value;
        showLoading=true;
      $http.post(url+"action=Appeal&id="+$stateParams.taskID+"&appealDate="+appealDate)
        .success(function (response) {
          showLoading=false;
              if(response.Appeal == "Succcess"){
                    myNewTask.splice(i, 1);
                    LiveStatus.updateopenCount(parseInt(LiveStatus.openCount)+1);
                    LiveStatus.updatenewCount(parseInt(LiveStatus.newCount)-1);
                    change("showAppeal","hideAppeal");
                    LiveStatus.refreshOpen();                    
              }
                else{
                    alert("Something went wrong");
                }
          
                               })
        .error(function(data,status){alert(status)});
        
    };
    
})

//myTask- Open
.controller('myopenCtrl', function($scope, $http, LiveStatus) {
    showLoading=true;
  $http.post(url+"action=MyOpenTask")
  .success(function (response) {
      showLoading=false;
      $scope.tasks = response.MyOpenTask;
                               myOpenTask= $scope.tasks;
                                 LiveStatus.updateopenCount(myOpenTask.length);
                               })
  .error(function(data,status){alert(status)});

    $scope.doRefresh = function() {
        showLoading=true;
    $http.post(url+"action=MyOpenTask")
   .success(function (response) {
        showLoading=false;
        $scope.tasks = response.MyOpenTask;
                                myOpenTask= $scope.tasks;
                                LiveStatus.updateopenCount(myOpenTask.length);
                                })
   .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
    
    $scope.$on('refreshOpen', function() {
        
        $scope.doRefresh();
    });
    
})

.controller('myopendetailsCtrl', function($scope, $stateParams, $http) {
    if(myOpenTask== null){
            showLoading=true;
          $http.post(url+"action=MyOpenTask")
          .success(function (response) {
              showLoading=false;
              $scope.tasks = response.MyOpenTask;
                                       myOpenTask= $scope.tasks;
                                         LiveStatus.updateopenCount(myOpenTask.length);
                                       })
          .error(function(data,status){alert(status)});
    }
    var i
    for (i=0; i<=myOpenTask.length; i++){
        if(myOpenTask[i].TaskID==$stateParams.taskID){
            $scope.x = myOpenTask[i];
            break;
        }
    }
    
    $scope.inProgress= function(){
        $scope.changeStatus("Progress");
    };
    
    $scope.review = function(){
        $scope.changeStatus("Review");
    };
    
    
    $scope.changeStatus= function(sta){
        showLoading=true;
        $http.post(url+"action=Change&toChange="+$stateParams.taskID+"&status="+sta)
            .success(function (response) {
            showLoading=false;        
            if(response.Change == "Succcess"){
                         change(sta,sta);
                        myOpenTask[i].Status=  sta; 
                    }
                                         
                               })
            .error(function(data,status){alert(status)});
    };
})






//myTask-Archive
.controller('myarchiveCtrl', function($scope, $http, LiveStatus) {
    showLoading=true;
  $http.post(url+"action=MyArchive")
  .success(function (response) {
      showLoading=false;
      $scope.tasks = response.MyArchive;
                               myArchives=$scope.tasks;
                               LiveStatus.updatearchiveCount(myArchives.length);
                               })
  .error(function(data,status){alert(status)});
    
  $scope.doRefresh = function() {
      showLoading=true;
    $http.post(url+"action=MyArchive")
   .success(function (response) {
        showLoading=false;
        $scope.tasks = response.MyArchive;
                                myArchives=$scope.tasks;
                                LiveStatus.updatearchiveCount(myArchives.length);
                                })
   .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
})

.controller('myarchivedetailsCtrl', function($scope, $stateParams) {
    if(myArchives==null){
        showLoading=true;
          $http.post(url+"action=MyArchive")
          .success(function (response) {
              showLoading=false;
              $scope.tasks = response.MyArchive;
                                       myArchives=$scope.tasks;
                                       LiveStatus.updatearchiveCount(myArchives.length);
                                       })
          .error(function(data,status){alert(status)});
    }
    var i;
    for (i=0; i<=myArchives.length; i++){
        if(myArchives[i].TaskID==$stateParams.taskID){
            $scope.x = myArchives[i];
            break;
        }
    }
    document.getElementById("Rating").value=myArchives[i].Rating;
})

//my Create

.controller('mycreateCtrl', function($scope, $http, $state,LiveStatus) {
    $scope.priority="Low";
    $scope.create=function(){ 
            $scope.mytitle=document.getElementById("mytitle").value;
            $scope.desc=document.getElementById("desc").value;
            $scope.eta=document.getElementById("eta").value;
            $scope.priority=document.getElementById("priority").value;
            $scope.Approve=document.getElementById("Approve").value;
        showLoading=true;
        $http.post(url+"action=CreateTask&title="+$scope.mytitle+"&desc="+$scope.desc+"&eta="+$scope.eta+"&priority="+
                   $scope.priority+"&Approve="+$scope.Approve)
          .success(function (response) {
            showLoading=false;
            if(!isNaN(response.CreateTask)){
                $scope.TaskID = response.CreateTask;
                change("createButton","created");
                var createAnim=setInterval(function () {
                    change("created","createButton");
                    clearInterval(createAnim);
                    $state.go($state.current, {}, {reload: true});
                }, 3000);
                LiveStatus.updateopenCount(parseInt(LiveStatus.openCount)+1);
                
            }
            else{
                alert("Something went wrong");
            }
        })
          .error(function(data,status){alert(status)});
    };
  
    
  
})

//myTask- Perfometer

.controller('myperformeterCtrl', function($scope, $http) {
    $scope.dur=1;
    $scope.showPerfometer= false;
    dt=defaultDates($scope.dur).split(";");
    $scope.fromDate=dt[0];
    $scope.toDate=dt[1];
    showLoading=true;
  $http.post(url+"action=MyPerfometer&from="+$scope.fromDate+"&to="+$scope.toDate)
  .success(function (response) {
      showLoading=false;
      var str = response.MyPerfometer; 
        myPerfometer(str);                               })
  .error(function(data,status){alert(status)});
    $scope.showPerfometer= true;
  $scope.doRefresh = function() {
    $scope.dur=document.getElementById("Duration").value;
    dt=defaultDates($scope.dur).split(";");
    $scope.fromDate=dt[0];
    $scope.toDate=dt[1];
      showLoading=true;
    $http.post(url+"action=MyPerfometer&from="+$scope.fromDate+"&to="+$scope.toDate)
  .success(function (response) {
        showLoading=false;
        var str = response.MyPerfometer;                             
        myPerfometer(str);                               })
  .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
    
})

//ourTask

.controller('summaryCtrl', function($scope, $http) {
   ourSummaryChart(ourSummary);
    $scope.doRefresh = function() {
        showLoading=true;
    $http.post(url+"action=ourSummary")
    .success(function (response) {
        showLoading=false;
        var str = response.ourSummary;
        ourSummaryChart(str);
    })
    .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
})

// our Task- Review

.controller('reviewCtrl', function($scope, $http, LiveStatus) {
    showLoading=true;
  $http.post(url+"action=ReviewTask")
  .success(function (response) {
      showLoading=false;
      $scope.tasks = response.ReviewTask;
                                reviewTask=$scope.tasks; 
                                 LiveStatus.updateourReviewCount(reviewTask.length);
                               })
  .error(function(data,status){alert(status)});

    $scope.doRefresh = function() {
        showLoading=true;
    $http.post(url+"action=ReviewTask")
    .success(function (response) {
        showLoading=false;
        $scope.tasks = response.ReviewTask;
                                reviewTask=$scope.tasks; 
                                 LiveStatus.updateourReviewCount(reviewTask.length);
                               })
    .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
})

.controller('reviewdetailsCtrl', function($scope, $stateParams, $http, LiveStatus) {
    if(reviewTask==null){
         showLoading=true;
        $http.post(url+"action=ReviewTask")
        .success(function (response) {
            showLoading=false;
            $scope.tasks = response.ReviewTask;
                                    reviewTask=$scope.tasks; 
                                     LiveStatus.updateourReviewCount(reviewTask.length);
                                   })
        .error(function(data,status){alert(status)});
    }
    var i;
    var t;
    var p;
    for (i=0; i<=reviewTask.length; i++){
        if(reviewTask[i].TaskID==$stateParams.taskID){
            $scope.x = reviewTask[i];
            t=reviewTask[i];
            switch(t.Priority){
            case "High":
                p = 3;
                break;
            case "Normal":
                p = 2;
                break;
            case "Low":
                p = 1;
                break;
            }
            break;
        }
    }
    $scope.rt=3;
    $scope.approve=function(){
        showLoading=true;
         $http.post(url+"action=ReviewAccept&taskID="+$stateParams.taskID+"&status="+t.Status+"&AppealDate="+t.AppealedDate+
                   "&MainTitle="+t.MainTitle+"&DueDate="+t.DueDate+"&Disc="+t.Description+"&Priority="+p+"&Rating="+$scope.rt+
                    "&assigned="+t.Assigned)
        .success(function (response) {
             showLoading=false;
                        if(response.ReviewAccept == "Succcess"){
                                reviewTask.splice(i, 1);
                                LiveStatus.updateourReviewCount(parseInt(LiveStatus.ourReviewCount)-1);
                                if(t.Status=="Review"){
                                    LiveStatus.updateourArchiveCount(parseInt(LiveStatus.ourArchiveCount)+1);
                                }
                                else{
                                    LiveStatus.updateourOpenCount(parseInt(LiveStatus.ourOpenCount)+1);
                                }
                                change("hideButton","showApproved");
                        }
                        else{
                            alert("Something went wrong");
                        }
                        
                               })
        .error(function(data,status){alert(status)});
        
    };
    
    $scope.reject=function(){
        showLoading=true;
       $http.post(url+"action=ReviewReject&taskID="+$stateParams.taskID+"&status="+t.Status+"&Priority="+p+"&assigned="+t.Assigned)
        .success(function (response) {
           showLoading=false;
                        if(response.ReviewReject == "Succcess"){
                                LiveStatus.updateourReviewCount(parseInt(LiveStatus.ourReviewCount)-1);
                                LiveStatus.updateourOpenCount(parseInt(LiveStatus.ourOpenCount)+1);
                                change("hideButton","showRejected");
                        }
                        else{
                                alert("Something went wrong");
                        }
                               })
        .error(function(data,status){alert(status)});
    };
    
})

// ourTask- Open

.controller('openCtrl', function($scope, $http, LiveStatus) {
    showLoading=true;
  $http.post(url+"action=OurOpenTask")
  .success(function (response) {
      showLoading=false;
      $scope.tasks = response.OurOpenTask;
                                ourOpenTask=$scope.tasks; 
                                 LiveStatus.updateourOpenCount(ourOpenTask.length);
                               })
  .error(function(data,status){alert(status)});

    $scope.doRefresh = function() {
        showLoading=true;
    $http.post(url+"action=OurOpenTask")
    .success(function (response) {
        showLoading=false;
        $scope.tasks = response.OurOpenTask;
                                ourOpenTask=$scope.tasks; 
                                 LiveStatus.updateourOpenCount(ourOpenTask.length);
                               })
    .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
})

.controller('opendetailsCtrl', function($scope, $http, LiveStatus, $stateParams) {
    if(ourOpenTask==null){
        showLoading=true;
            $http.post(url+"action=OurOpenTask")
            .success(function (response) {
                showLoading=false;
                $scope.tasks = response.OurOpenTask;
                                        ourOpenTask=$scope.tasks; 
                                         LiveStatus.updateourOpenCount(ourOpenTask.length);
                                       })
            .error(function(data,status){alert(status)});
    }
      var i
    for (i=0; i<=ourOpenTask.length; i++){
        if(ourOpenTask[i].TaskID==$stateParams.taskID){
            $scope.x = ourOpenTask[i];
            document.getElementById("mainDate").value=ourOpenTask[i].MainDate;
            document.getElementById("myDate").value=ourOpenTask[i].DueDate;
            break;
        }
    }
    
    $scope.changeMainDate= function(){
        var mainDate= document.getElementById("mainDate").value;
        showLoading=true;
         $http.post(url+"action=changeDate&id="+$stateParams.taskID+"&MainDate="+mainDate)
        .success(function (response) {
             showLoading=false;
                        if(response.changeDate == "Success"){
                                ourOpenTask[i].MainDate=mainDate;
                                change("changeMainDate","MainDateChanged");
                        }
                        else{
                                alert("Something went wrong");
                        }
                        
                               })
        .error(function(data,status){alert(status)});
    
    }
    
    $scope.changeTaskDate= function(){
        var taskDate= document.getElementById("myDate").value;
        showLoading=true;
         $http.post(url+"action=changeSubDate&id="+$stateParams.taskID+"&subDate="+taskDate)
        .success(function (response) {
             showLoading=false;
                        if(response.changeSubDate == "Success"){
                                ourOpenTask[i].DueDate=taskDate;
                                change("changeTaskDate","TaskDateChanged");
                        }
                        else{
                                alert("Something went wrong");
                        }
                        
                               })
        .error(function(data,status){alert(status)});   
    }
})

//our Archive

.controller('archiveCtrl', function($scope, $http, LiveStatus) {
    showLoading=true;
  $http.post(url+"action=OurArchive")
  .success(function (response) {
      showLoading=false;
      $scope.tasks = response.OurArchive;
                               ourArchives=$scope.tasks;
                               LiveStatus.updateourArchiveCount(ourArchives.length);
                               })
  .error(function(data,status){alert(status)});
    
  $scope.doRefresh = function() {
      showLoading=true;
    $http.post(url+"action=OurArchive")
   .success(function (response) {
        showLoading=false;
        $scope.tasks = response.OurArchive;
                                ourArchives=$scope.tasks;
                                LiveStatus.updateourArchiveCount(ourArchives.length);
                                })
   .error(function(data,status){alert(status)})
    .finally(function() {
       $scope.$broadcast('scroll.refreshComplete');
     });
  };
})

.controller('archivedetailsCtrl', function($scope, $stateParams) {
    if(ourArchives==null){
        showLoading=true;
    $http.post(url+"action=OurArchive")
   .success(function (response) {
        showLoading=false;
        $scope.tasks = response.OurArchive;
                                ourArchives=$scope.tasks;
                                LiveStatus.updateourArchiveCount(ourArchives.length);
                                })
   .error(function(data,status){alert(status)});
    }
    var i;
    for (i=0; i<=ourArchives.length; i++){
        if(ourArchives[i].TaskID==$stateParams.taskID){
            if (ourArchives[i].MainCompleted == ""){
                ourArchives[i].MainCompleted = "Not Completed";
            }
            $scope.x = ourArchives[i];
            break;
        }
    }
    document.getElementById("Rating").value=ourArchives[i].Rating;
})

// our Create

.controller('createCtrl', function($scope, $http, LiveStatus,$state) {
    var empty= true;
     $scope.assignees = [
      {name:'Select'},
    ];
    
    $scope.addAssignee = function (){
        for (var i=0; i< $scope.assignees.length; i++){
            if($scope.assignees[i].name == "Select"){
                empty= false;
            }
        }
        if(empty){
            $scope.assignees.push({name:'Select'})
        }
        empty= true;
    }
    showLoading=true;
    $http.post(url+"action=TeamMembers")
    .success(function (response) {
        showLoading=false;
        $scope.members = response.TeamMembers;
        $scope.members.splice(0,1);
                               })
    .error(function(data,status){alert(status)});
    
    $scope.create=function(){ 
        var assignees="assignees";
            for (var i=0; i< $scope.assignees.length; i++){
            if($scope.assignees[i].name != "Select"){
                assignees= assignees+" "+$scope.assignees[i].name.ID;
            }
            }
            $scope.ourtitle=document.getElementById("ourtitle").value;
            $scope.desc=document.getElementById("desc").value;
            $scope.eta=document.getElementById("eta").value;
            $scope.priority=document.getElementById("priority").value;
        showLoading=true;
        $http.post(url+"action=CreateOurTask&title="+$scope.ourtitle+"&desc="+$scope.desc+"&eta="+$scope.eta+"&priority="+
                   $scope.priority+"&assignee="+assignees)
          .success(function (response) {
            showLoading=false;
            if(response.CreateOurTask != ""){
                $scope.TaskName= response.CreateOurTask;
                change("createButton","created");
                var createAnim=setInterval(function () {
                    change("created","createButton");
                    clearInterval(createAnim);
                    $state.go($state.current, {}, {reload: true});
                }, 3000);
                LiveStatus.updateourOpenCount(parseInt(LiveStatus.ourOpenCount)+($scope.assignees.length-1));
                
            }
            else{
                alert("Something went wrong");
            }
        })
          .error(function(data,status){alert(status)});
    };  
})

//ourTask- Perfometer

.controller('performeterCtrl', function($scope, $http) {
    
    $http.post(url+"action=TeamMembers")
    .success(function (response) {$scope.members = response.TeamMembers;
                               })
    .error(function(data,status){alert(status)});
    $scope.dur=1;
    $scope.showPerfometer= false;
    dt=defaultDates($scope.dur).split(";");
    $scope.fromDate=dt[0];
    $scope.toDate=dt[1];
    showLoading=true;
  $http.post(url+"action=OurPerformeter&Ourfrom="+$scope.fromDate+"&Ourto="+$scope.toDate)
  .success(function (response) {
      showLoading=false;
      var str = response.OurPerformeter; 
        getOurChart(str);  
         $scope.mem=0;
                               })
  .error(function(data,status){alert(status)});
    $scope.showPerfometer= true;
    
  $scope.doRefresh = function() {
        $scope.dur=document.getElementById("Duration").value;
        dt=defaultDates($scope.dur).split(";");
        $scope.fromDate=dt[0];
        $scope.toDate=dt[1];
      showLoading=true;
        $http.post(url+"action=OurPerformeter&Ourfrom="+$scope.fromDate+"&Ourto="+$scope.toDate)
      .success(function (response) {
            showLoading=false;
            var str = response.OurPerformeter;                             
            getOurChart(str); 
            $scope.mem=0;
                                   })
      .error(function(data,status){alert(status)})
        .finally(function() {
           $scope.$broadcast('scroll.refreshComplete');
         });
  };
    
})