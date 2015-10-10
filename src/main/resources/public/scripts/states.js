updservapp.config(function($stateProvider, $urlRouterProvider) {

    $stateProvider
        .state('app', {
        url: '/app',
        template: "<div ui-view />"
    })

    .state('app.detail', {
        url: '/detail/:id',
        templateUrl: '/templates/app/detail.html',
        controller: 'AppDetailCtrl',
        resolve: {
            app: function($stateParams, $q, $state, App) {
                if ($stateParams.id && $stateParams.id.length > 0) {
                    return App.get({
                        id: $stateParams.id
                    }, function(success) {
                        console.log(success);
                    }, function(error) {
                        alert("App konnte nicht geladen werden!");
                    }).$promise;
                } else {
                    return {};
                }


            }
        }
    })

});
