// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 
                           'ngCordova',
                           'ionic.service.core',
                           'ionic.service.push',
                           'starter.controllers'])

.run(function($ionicPlatform) {
  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
})

//Push

 .config(['$ionicAppProvider', function($ionicAppProvider) {
  // Identify app
  $ionicAppProvider.identify({
    // The App ID (from apps.ionic.io) for the server
    app_id: 'e554545b',
    // The public API key all services will use for this app
    api_key: '3435aa8d074aa7d80a57731172c5d293c051288d8b00cc00',
    // Set the app to use development pushes
    gcm_id: '355260648083',
    dev_push: true
  });
 }])

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

  .state('app', {
    url: "/app",
    abstract: true,
    templateUrl: "templates/menu.html",
    controller: 'AppCtrl'
  })

  

  .state('app.tabs', {
    url: "/tabs",
    views: {
      'menuContent': {
        templateUrl: "templates/tabs.html",
        controller: 'tabsCtrl'
      }
    }
  })
  
    .state('app.ourtabs', {
    url: "/ourtabs",
    views: {
      'menuContent': {
        templateUrl: "templates/ourtabs.html",
        controller: 'ourtabsCtrl'
      }
    }
  })
  
  
    .state('app.welcome', {
      cache: false,
      url: "/welcome",
      views: {
        'menuContent': {
          templateUrl: "templates/welcome.html",
          controller: 'WelcomeCtrl'
        }
      }
    })

   .state('app.login', { 
      url: "/login",
      views: {
        'menuContent': {
          templateUrl: "templates/login.html",
          controller: 'LoginCtrl'
        }
      }
    })
  
  //myTask
  
    .state('app.tabs.mysummary', {
    url: '/mysummary',
      views: {
        'tab-mysummary': {
          templateUrl: 'templates/tab-mysummary.html',
          controller: 'mysummaryCtrl'
        }
      }
    })
  
   .state('app.tabs.mynew', {
      url: '/mynew',
      views: {
        'tab-mynew': {
          templateUrl: 'templates/tab-mynew.html',
          controller: 'mynewCtrl'
        }
      }
    })
  
   .state('app.tabs.mynewdetails', {
      url: '/mynew/:taskID',
      views: {
        'tab-mynew': {
          templateUrl: 'templates/tab-mynew-details.html',
          controller: 'mynewdetailsCtrl'
        }
      }
    })
  
  .state('app.tabs.myopen', {
      url: '/myopen',
      views: {
        'tab-myopen': {
          templateUrl: 'templates/tab-myopen.html',
          controller: 'myopenCtrl'
        }
      }
    })
  
  .state('app.tabs.myopendetails', {
      url: '/myopen/:taskID',
      views: {
        'tab-myopen': {
          templateUrl: 'templates/tab-myopen-details.html',
          controller: 'myopendetailsCtrl'
        }
      }
    })

  .state('app.tabs.myarchive', {
      url: '/myarchive',
      views: {
        'tab-myarchive': {
          templateUrl: 'templates/tab-myarchive.html',
          controller: 'myarchiveCtrl'
        }
      }
    })
  
  .state('app.tabs.myarchivedetails', {
      url: '/myarchive/:taskID',
      views: {
        'tab-myarchive': {
          templateUrl: 'templates/tab-myarchive-details.html',
          controller: 'myarchivedetailsCtrl'
        }
      }
    })
  
    .state('app.tabs.mycreate', {
      url: '/mycreate',
      cache: false,
      views: {
        'tab-mycreate': {
          templateUrl: 'templates/tab-mycreate.html',
          controller: 'mycreateCtrl'
        }
      }
    })
  
      .state('app.tabs.myperformeter', {
      url: '/myperformeter',
      views: {
        'tab-myperformeter': {
          templateUrl: 'templates/tab-myperformeter.html',
          controller: 'myperformeterCtrl'
        }
      }
    })
  
  //ourTask
  
    .state('app.ourtabs.summary', {
      url: '/summary',
      views: {
        'ourtabs-summary': {
          templateUrl: 'templates/ourtab-summary.html',
          controller: 'summaryCtrl'
        }
      }
    })
  
  .state('app.ourtabs.review', {
      url: '/review',
      views: {
        'ourtabs-review': {
          templateUrl: 'templates/ourtab-review.html',
          controller: 'reviewCtrl'
        }
      }
    })
  
  .state('app.ourtabs.reviewdetails', {
      url: '/review/:taskID',
      views: {
        'ourtabs-review': {
          templateUrl: 'templates/ourtab-review-details.html',
          controller: 'reviewdetailsCtrl'
        }
      }
    })
  
  .state('app.ourtabs.open', {
      url: '/open',
      views: {
        'ourtabs-open': {
          templateUrl: 'templates/ourtab-open.html',
          controller: 'openCtrl'
        }
      }
    })
  
  .state('app.ourtabs.opendetails', {
      url: '/open/:taskID',
      views: {
        'ourtabs-open': {
          templateUrl: 'templates/ourtab-open-details.html',
          controller: 'opendetailsCtrl'
        }
      }
    })
  
  .state('app.ourtabs.archive', {
      url: '/archive',
      views: {
        'ourtabs-archive': {
          templateUrl: 'templates/ourtab-archive.html',
          controller: 'archiveCtrl'
        }
      }
    })
  
    .state('app.ourtabs.archivedetails', {
      url: '/archive/:taskID',
      views: {
        'ourtabs-archive': {
          templateUrl: 'templates/ourtab-archive-details.html',
          controller: 'archivedetailsCtrl'
        }
      }
    })
  
    .state('app.ourtabs.create', {
      url: '/create',
      cache: false,
      views: {
        'ourtabs-create': {
          templateUrl: 'templates/ourtab-create.html',
          controller: 'createCtrl'
        }
      }
    })
      .state('app.ourtabs.performeter', {
      url: '/performeter',
      views: {
        'ourtabs-performeter': {
          templateUrl: 'templates/ourtab-performeter.html',
          controller: 'performeterCtrl'
        }
      }
    })
  
  ;
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/welcome');
});
