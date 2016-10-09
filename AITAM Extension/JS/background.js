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
    //GCM
        
        function registerCallback(registrationId) {
        token=registrationId;
          if (chrome.runtime.lastError) {
                // When the registration fails, handle the error and retry the
                // registration later.
               $scope.msg="registration failed";
            return;
          }

          // Send the registration token to your application server.
          sendRegistrationId(function(succeed) {
            // Once the registration token is received by your server,
            // set the flag such that register will not be invoked
            // next time when the app starts up.
            if (succeed)
              chrome.storage.local.set({registered: true});
          });
        }

        function sendRegistrationId(callback) {
          // Send the registration token to your application server
          // in a secure way.
            $http.post("https://dev-dot-service-aitam.appspot.com/MobileServices?action=DeviceRegister&EmpID="+0+"&DeviceID="+fingerprint+"&Token="+ token+"&Platform="+getBrowser()+"&Model="+navigator.platform)
                .success(function (response) {
                    if(response.DeviceRegister== "Success"){
                        $scope.msg=token;
                    }
                })
                .error(function(data,status){
                })
        }

          chrome.storage.local.get("registered", function(result) {
            // If already registered, bail out.
            if (result["registered"])
              return;

            // Up to 100 senders are allowed.
            var senderIds = ["500095551588"];
            chrome.gcm.register(senderIds, registerCallback);
          });

                                     
    chrome.gcm.onMessage.addListener(function(message) {
  // A message is an object with a data property that
  // consists of key-value pairs.
        $scope.status();
        var msg=message.data.msg.split(":");
        var id=Math.floor((Math.random() * 99999) + 10000);
        var options= {
              type: "basic",
              title: msg[0],
              message: msg[1],
              iconUrl: "Images/icon.png",
              //imageUrl: "url_to_preview_image"
            }
        chrome.notifications.create(id.toString(), options,  function() {} );

        
        
    });
        
    $scope.goto =function(){
        chrome.tabs.create({url:"http://aitam.newtonjoshua.com"});
    }


});

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