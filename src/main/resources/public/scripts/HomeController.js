var updservapp = angular.module('updservapp', ["ngResource", "ui.router", "ngSanitize", "ui.bootstrap", 'ngFileUpload']);

updservapp.controller('MainCtrl', function($scope, $rootScope, $timeout, $state, App) {
	$scope.apps = App.query();

	$scope.removeApp = function(app) {
		if (confirm("Sicher, dass die App:" + app.name + " und alle zugehörigen Versionen und Dateien entfernt werden sollen?")) {
			App.remove({id: app.id}, function() {
				$state.go("app");
				$scope.apps = App.query();
			});
		}
	}

	$scope.$on("appChanged", function() {
		console.log("CHANGED")
		$scope.apps = App.query();
	});

	$rootScope.$watch("message", function() {
		$timeout(function(){
			delete $rootScope.message;	
		}, 5000);
	});

	$rootScope.moment = moment;
});


