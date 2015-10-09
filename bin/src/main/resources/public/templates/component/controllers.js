smarttoolapp.factory('Component', function($resource) {
	return $resource('/api/component/:id', {id:'@id'}, {
		'list': {
			url: '/api/component',
			method: 'GET',
			isArray: false
		}
	});      
});

smarttoolapp.controller('ComponentListCtrl', function($scope, Component, $http) {
	$scope.page = 0;
	$scope.pagesize = 10;
	$scope.searchterm = "";
	
	$scope.remove = function(component){
		Component.remove({"id":component.id});
		$scope.components = _.without($scope.components, component);

	}

	$scope.refresh = function(){
		$scope.components = Component.list({page: $scope.page, size: $scope.pagesize});
	}

	$scope.refresh();

	$scope.nextPage = function(){
		$scope.page++;
		$scope.refresh();
	}

	$scope.previousPage = function(){
		$scope.page--;
		$scope.refresh();
	}

	$scope.search = function(){
		$scope.refresh();
	}

	$scope.clearSearch = function(){
		$scope.searchterm = "";
		$scope.refresh();
	}

});

// controller fuer detail.html 
smarttoolapp.controller('ComponentDetailCtrl', function($scope, component) {
	$scope.component = component;
}); 

