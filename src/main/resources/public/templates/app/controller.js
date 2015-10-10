updservapp.factory('App', function($resource) {
    return $resource('/api/app/:id', {
        id: '@id'
    }, {});
});

// controller fuer detail.html 
updservapp.controller('AppDetailCtrl', function($scope, $rootScope, app, App, $state, Upload) {
    $scope.app = app;
    $scope.updater = {};

    $scope.save = function() {
        App.save($scope.app, function(data) {
            $rootScope.message = "Erfolgreich gespeichert!";
            $scope.$emit("appChanged");
            $state.transitionTo($state.current, {id: data.id}, { 
              reload: true, inherit: true, notify: true 
            });
        });
    }

    $scope.addVersion = function() {
        $scope.newVersion = {};
    }

    $scope.commitVersion = function() {
        if ($scope.newVersion.file && !$scope.newVersion.file.$error) {
            $scope.progress = 0;
            Upload.upload({
                url: '/api/app/' + $scope.app.id + '/newversion',
                fields: {
                    'versionNumber': $scope.newVersion.versionNumber
                },
                file: $scope.newVersion.file,
                method: 'POST'
            }).progress(function(evt) {
                $scope.progress =  parseInt(100.0 * evt.loaded / evt.total);
            }).success(function(data, status, headers, config) {
                delete $scope.newVersion;
                delete $scope.progress;
                $rootScope.message = "Version erfolgreich hochgeladen";
                $scope.$emit("appChanged");
                $scope.app = App.get({id: app.id});
            });
        }
    }

     $scope.uploadUpdater = function (file) {
        $scope.progress = 0;
        Upload.upload({
            url: '/api/app/' + $scope.app.id + '/updater',
            method: 'POST',
            data: {file: file}
        }).then(function (resp) {
            delete $scope.newVersion;
            delete $scope.progress;
            $rootScope.message = "Updater erfolgreich hochgeladen";
            $scope.$emit("appChanged");
            $scope.app = App.get({id: app.id});
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        }, function (evt) {
            $scope.progress =  parseInt(100.0 * evt.loaded / evt.total);
        });
    };
});