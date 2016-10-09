var url = "dev-dot-service-aitam.appspot.com/MobileServices?";
var fingerprint = (new Fingerprint().get()) + "-" +  new Fingerprint({screen_resolution: true}).get();
var token;
document.addEventListener('DOMContentLoaded', function() {
  
});

var app = angular.module("AITAM", []);
app.controller("myCtrl", function($scope,$http,$timeout) {
    //Device Login
    $scope.fingerprint=fingerprint;
    $scope.signed=localStorage.signed;
    $scope.status= function(){
    $http.post("https://dev-dot-service-aitam.appspot.com/MobileServices?action=DeviceLogin&DeviceID="+fingerprint+"&EmpID=0")
        .success(function (response,code) {
            var temp=  response.Login.split(";");
            $scope.name = temp[0];
            $scope.isMan= temp[1];
            if($scope.name != "incorrect"){
                    $scope.signed=true;
                localStorage.signed=true;
                 mySummary=response.mySummary;
                 ourSummary=response.ourSummary;
                 var empData=mySummary.split(" ");
                 $scope.newCount=empData[0];
                 $scope.openCount=empData[11];
                 var ourempData=ourSummary.split(" ");
                 $scope.reviewCount=ourempData[13];
                 $scope.ourNewCount=ourempData[11];
                 var tot=parseInt(empData[0])+parseInt(ourempData[13]);
                 chrome.browserAction.setBadgeText({text: tot.toString()});
            }
        else{
                $scope.signed=false;
                localStorage.signed=false;
        }
        
                })
        .error(function(data,status){
        });
    }
    $scope.status();

        
    $scope.goto =function(){
        chrome.tabs.create({url:"http://aitam.newtonjoshua.com"});
    }


});
