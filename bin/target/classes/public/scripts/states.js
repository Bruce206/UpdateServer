smarttoolapp.config(function($stateProvider, $urlRouterProvider) {

    $stateProvider

    .state('tool', {
        url: '/tool',
        template: "<div ui-view />"
    })

    .state('tool.list', {
        url: '/list',
        templateUrl: '/templates/tool/list.html',
        controller: 'ToolListCtrl'
    })

    .state('tool.detail', {
        url: '/detail/:id',
        templateUrl: '/templates/tool/detail.html',
        controller: 'ToolDetailCtrl',
        resolve: {
            tool: function($stateParams, $q, $state, Tool) {
                return Tool.get({
                    id: $stateParams.id
                }, function(success) {
                    console.log(success);
                }, function(error) {
                    alert("Werkzeug konnte nicht geladen werden!");
                }).$promise;
            }
        }
    })

    .state('component', {
        url: '/component',
        template: "<div ui-view />"
    })

    .state('component.list', {
        url: '/list',
        templateUrl: '/templates/component/list.html',
        controller: 'ComponentListCtrl'
    })

    .state('component.detail', {
        url: '/detail/:id',
        templateUrl: '/templates/component/detail.html',
        controller: 'ComponentDetailCtrl',
        resolve: {
            component: function($stateParams, $q, $state, Component) {
                return Component.get({
                    id: $stateParams.id
                }, function(success) {
                    console.log(success);
                }, function(error) {
                    alert("Komponente konnte nicht geladen werden!");
                }).$promise;
            }
        }
    })

});
