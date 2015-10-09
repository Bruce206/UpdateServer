var smarttoolapp = angular.module('smarttoolapp', ["ngResource", "ui.router", "ngSanitize", "ui.bootstrap"]);

smarttoolapp.controller('MainCtrl', function($scope, $rootScope) {
	    $scope.showDate = function(timestamp) {
        if (timestamp) {
            return moment(timestamp).format("DD.MM.YY mm:hh");
        }
    }
});

smarttoolapp.filter('formatTimestamp', function() {
	return function(input) {
		return moment(input).format("DD.MM.YYYY hh:mm");
	}
});


