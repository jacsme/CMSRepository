var app = angular.module("app", ["xeditable", "ngMockE2E", "ui.bootstrap"]);

app.run(function(editableOptions) {
  editableOptions.theme = 'bs3';
});

app.controller('Ctrl', function($scope, $filter, $http) {
  $scope.users = [{
    id: 1,
    name: 'awesome user1',
    status: 2,
    group: 4,
    groupName: 'admin'
  }, {
    id: 2,
    name: 'awesome user2',
    status: undefined,
    group: 3,
    groupName: 'vip'
  }, {
    id: 3,
    name: 'awesome user3',
    status: 2,
    group: null
  }];

  $scope.statuses = [{
    value: 1,
    text: 'status1'
  }, {
    value: 2,
    text: 'status2'
  }, {
    value: 3,
    text: 'status3'
  }, {
    value: 4,
    text: 'status4'
  }];

  $scope.groups = [];
  $scope.loadGroups = function() {
    return $scope.groups.length ? null : $http.get('/groups').success(function(data) {
      $scope.groups = data;
    });
  };

  $scope.showGroup = function(user) {
    if (user.group && $scope.groups.length) {
      var selected = $filter('filter')($scope.groups, {
        id: user.group
      });
      return selected.length ? selected[0].text : 'Not set';
    } else {
      return user.groupName || 'Not set';
    }
  };

  $scope.showStatus = function(user) {
    var selected = [];
    if (user.status) {
      selected = $filter('filter')($scope.statuses, {
        value: user.status
      });
    }
    return selected.length ? selected[0].text : 'Not set';
  };

  $scope.checkName = function(data, id) {
    if (id === 2 && data !== 'awesome') {
      return "Username 2 should be `awesome`";
    }
  };

  $scope.saveUser = function(data, id) {
    //$scope.user not updated yet
    angular.extend(data, {
      id: id
    });
    return $http.post('/saveUser', data);
  };

  // remove user
  $scope.removeUser = function(index) {
    $scope.users.splice(index, 1);
    $scope.totalItems = $scope.users.length;
  };

  // add user
  $scope.addUser = function() {
    $scope.inserted = {
      id: $scope.users.length + 1,
      name: '',
      status: null,
      group: null
    };
    // counting modulo of users.length with pagesize which is 4
    var pagecount = $scope.users.length % 4;
    if (pagecount >= 3) // if 3 which is before new page add new page
      $scope.noOfPages++;
    $scope.users.push($scope.inserted);
    $scope.totalItems = $scope.users.length;
  };
  //pagination at initialization
  $scope.totalItems = $scope.users.length;
  $scope.currentPage = 1;
  // set page click
  $scope.setPage = function(pageNo) {
    $scope.currentPage = pageNo;
  };
});

// filtering shown units
app.filter('startFrom', function() {
  return function(input, start) {
    start = +start; //parse to int
    return input.slice(start);
  }
});

// --------------- mock $http requests ----------------------
app.run(function($httpBackend) {
  $httpBackend.whenGET('/groups').respond([{
    id: 1,
    text: 'user'
  }, {
    id: 2,
    text: 'customer'
  }, {
    id: 3,
    text: 'vip'
  }, {
    id: 4,
    text: 'admin'
  }]);

  $httpBackend.whenPOST(/\/saveUser/).respond(function(method, url, data) {
    data = angular.fromJson(data);
    return [200, {
      status: 'ok'
    }];
  });
});