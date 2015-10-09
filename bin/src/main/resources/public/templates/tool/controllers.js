smarttoolapp.factory('Tool', function($resource) {
	return $resource('/api/tool/:id', {id:'@id'}, {
		'list': {
			url: '/api/tool',
			method: 'GET',
			isArray: false
		}
	});      
});

smarttoolapp.controller('ToolListCtrl', function($scope, Tool, $http) {
	$scope.page = 0;
	$scope.pagesize = 10;
	$scope.searchterm = "";
	
	$scope.remove = function(tool){
		Tool.remove({"id":tool.id});
		$scope.tools = _.without($scope.tools, tool);

	}

	$scope.refresh = function(){
		$scope.tools = Tool.list({page: $scope.page, size: $scope.pagesize});
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
smarttoolapp.controller('ToolDetailCtrl', function($scope, tool) {
	$scope.tool = tool;
}); 

