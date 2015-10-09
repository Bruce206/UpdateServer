smarttoolapp.factory('TotalTool', function($resource) {
	return $resource('/api/totaltool', {}, {
		'list': {
			url: '/api/totaltool/list',
			method: 'GET',
			isArray: false
		}
	});      
});

smarttoolapp.controller('TotalToolListCtrl', function($scope, TotalTool, $http) {
	$scope.page = 0;
	$scope.pagesize = 10;
	$scope.searchterm = "";
	
	$scope.remove = function(totaltool){

		TotalTool.remove({"id":totaltool.id});
		//"id" = der parametername für den http-request, also das was in java bei der methode erwartet wird
		// customerid ist der wert der id zugewiesen wird und : der zuweiser und key -> value
		$scope.totaltools = _.without($scope.totaltools, totaltool);

	}

	$scope.refresh = function(){
		$scope.totaltools = TotalTool.list({page: $scope.page, size: $scope.pagesize});
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
smarttoolapp.controller('TotalToolDetailCtrl', function($scope, totaltool) {
	$scope.totaltool = totaltool;
}); 

