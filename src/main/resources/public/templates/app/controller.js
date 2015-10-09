updservapp.factory('App', function($resource) {
    return $resource('/api/app/:id', {
        id: '@id'
    }, {});
});

// controller fuer detail.html 
updservapp.controller('AppDetailCtrl', function($scope, $rootScope, app, App, $state, Upload) {
    $scope.app = app;

    $scope.save = function() {
        App.save($scope.app, function() {
            $rootScope.message = "Erfolgreich gespeichert!";
            $scope.$emit("appChanged");
            $state.go("app");
        });
    }

    $scope.addVersion = function() {
        $scope.newVersion = {};
    }

    $scope.commitVersion = function() {
        if ($scope.newVersion.file && !$scope.newVersion.file.$error) {
            $scope.progress = 0;
            Upload.upload({
                url: '/api/newversion/' + $scope.app.id,
                fields: {
                    'versionNumber': $scope.newVersion.versionNumber
                },
                file: $scope.newVersion.file
            }).progress(function(evt) {
                $scope.progress =  parseInt(100.0 * evt.loaded / evt.total);
            }).success(function(data, status, headers, config) {
                console.log('file ' + config.file.name + 'uploaded. Response: ' + data);
                $rootScope.message = "Erfolgreich hochgeladen";
                $scope.$emit("appChanged");
                $scope.app = App.get({id: app.id});
                delete $scope.newVersion;
                delete $scope.progress;
            });
        }
    }
});
