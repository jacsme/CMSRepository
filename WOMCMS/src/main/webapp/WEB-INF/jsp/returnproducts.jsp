<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html data-ng-app="inventoryapp">
<head>
<meta charset="utf-8" />
<title>WOM Content Management System</title>
<head>
	<link data-require="bootstrap-css@3.1.1" data-semver="3.1.1" rel="stylesheet" href='<c:url value="/resources/css/bootstrap.min.css" />' />
	<link rel="stylesheet" href='<c:url value="/resources/css/xeditable.css" />' />
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/main.css" />'/>
	
	<script data-require="angular.js@1.3.0" data-semver="1.3.0" src="https://code.angularjs.org/1.3.0/angular.js"></script>
	<script src='<c:url value="/resources/js/xeditable/xeditable.js" />' ></script>
	<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.10.0.min.js"></script>
	<script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> 
	<script src= '<c:url value="/resources/js/angular/dirPagination.js" />'></script>
	<script data-require="bootstrap@3.1.1" data-semver="3.1.1" src='<c:url value="/resources/js/bootstrap/bootstrap.min.js" />'></script>
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-animate.js"></script>
	<!-- Styles -->
	<link rel="stylesheet" href="components/toastr/toastr.min.css">
	<script src='<c:url value="/resources/toastr/toastr.min.js" />'></script>    
</head>
  
  
<style>
	#inventory table{
		width: 1500px;
	}
	#inventory table, th , td {
	  border: 1px solid grey;
	  border-collapse: collapse;
	  padding: 5px;
	}
	#inventory table tr:nth-child(odd) {
	  background-color: #f1f1f1;
	}
	#inventory table tr:nth-child(even) {
	  background-color: #ffffff;
	}
	
	.processing {  
	    position:absolute;
	    top:200px;
	    left:520px;
	    width:100px;
	    height:100px;
	    z-index:1000;
	    
	    opacity: .6;
	}
	
	.processing2 {  
	    position:absolute;
	    top:100px;
	    left:370px;
	    width:100px;
	    height:100px;
	    z-index:1000;
	    
	    opacity: .6;
	}
	
</style> 
<script>
	
    	var inventoryapp = angular.module('inventoryapp', ['angularUtils.directives.dirPagination', 'ui.bootstrap', 'xeditable' ]);
    	function SummaryController($scope, $http, $modal, $log){
    	$scope.open = function (productcode, location, balance) {
    		if(balance === 0){
    			alert('Stock is Empty nothing to return');
    		}else{
	            var modalInstance = $modal.open({
	              templateUrl: 'inventoryModal.html',
	              controller: 'ModalInstanceCtrl',
	              resolve: {
	            	  productcode: function () {
	                    return productcode;	
	                  },
	                  location : function () {
	                	  return location;
	                  },
	                  balance : function (){
	                	  return balance;
	                  }
	              }
            	});
            
            	modalInstance.result.then(function (selectedItem) {
                //$scope.selected = selectedItem;
              	}, function () {
                	$log.info('Modal dismissed at: ' + new Date());
                	$scope.getInventoryList();
              	});
    		}
        };
        
    	$scope.currentPage = 1;
    	$scope.pageSize = 10;
    	
    	$scope.getInventoryList = function() {  
    		$scope.loading = true;
    		var paramstocklevel;
    		console.log("Select " + $scope.stocklevellist.selected);
    		if(typeof $scope.stocklevellist.selected === 'undefined'){ 
    			paramstocklevel = '-'
    		}else{
    			paramstocklevel= $scope.stocklevellist.selected;
    		}
    		
    		if($scope.productcode === ''){ $scope.productcode = '-'}
    		if($scope.stocklocation === ''){ $scope.stocklocation = '-'}
    		
        	$http.get('searchInventoryList/' + encodeURIComponent($scope.productcode) + '/' + encodeURIComponent(paramstocklevel) + '/' + encodeURIComponent($scope.stocklocation))
            .success(function(data, status, headers, config) {
            	console.log("search >> " + data);
            	$scope.inventorylist = data;
            	$scope.loading = false;
            })
            .error(function(data, status, headers, config) {
            	alert('Error in Parameters')
            	$scope.loading = false;
            });
        };
        
        $scope.stocklevellist = {
  		    availableOptions: [
  		      {id: '1', level: 'Stocks on Hand'},
  		      {id: '2', level: 'Stocks Finished'},
  		    ],
     	};

        $scope.cancel = function () {
            window.location.reload(); 
        };
        
        $scope.pageChangeHandler = function(num) {
            console.log('changed to ' + num);
        };
    };
    angular.module('inventoryapp').controller('ModalInstanceCtrl', function ($scope, $http, $modalInstance, productcode, location, balance) {
 		
 	  	$scope.productcode = productcode;
 	  	$scope.location = location;
		
 	  	$scope.returnunits = {
	         stocks : 0,
   	         comments  : '',
   	    };
 	  	
		$scope.addReturnUnits = function() {
	      	if($scope.returnunits.stocks > balance ){
				alert('Return Items is more than the available stocks');
				$scope.returnunits.stocks = 0;
	      	}else if ($scope.returnunits.stocks === 0){
				alert('Please enter stocks to return');	      		
	      	}else if ($scope.returnunits.comments === ''){
				alert('Please enter a comments');
			}else{
				$scope.loading2 = true;
	            $http.post('addReturnUnits/' + $scope.location + '/' + $scope.productcode + '/' + $scope.returnunits.stocks + '/' + $scope.returnunits.comments)
	            .success(function(data, status, headers, config) {
	            	alert("Successfully Saved");
	            	$scope.loading = false;
	            	$modalInstance.dismiss('cancel');
	            })
	            .error(function(data, status, headers, config) {
	            	alert("Error");
	            	$scope.loading = false;
	            });
			}
      	};
      
 	  	$scope.cancel = function () {
 	  	  $modalInstance.dismiss('cancel');
 	  	};
 	}); 	
    
    function PageController($scope) {
     	  $scope.pageChangeHandler = function(num) {
     	    console.log('going to page ' + num);
     	  };
    }
    inventoryapp.controller('SummaryController', SummaryController);
    inventoryapp.controller('PageController', PageController);
    
</script>
</head>

<body id="mainpage" class="mainpage">
<header id="banner" class="body">
	<h1>Content Management System</h1>
	<c:url var="home" value="/wom/cms/home"/>
	<c:url var="password" value="/wom/cms/password"/>
	<c:url var="uploading" value="/wom/cms/uploadimages"/>
	<c:url var="purchaseorders" value="/wom/cms/purchaseorders"/>
	<c:url var="salesorder" value="/wom/cms/salesorder"/>
	<c:url var="otherdetails" value="/wom/cms/otherdetails"/>
	<c:url var="addnewproduct" value="/wom/cms/addnewproductpage"/>
	<c:url var="logout" value="/wom/cms/login"/>
	<nav>
		<ul>
			<li><a href="${home}">Home</a></li>
			<li><a href="${logout}">Log Out</a></li>
		</ul>
	</nav>
 
</header><!-- /#banner -->

<section id="uicontent" class="body">
<div class="container">
    <div class="row">
    <div class="col-lg-8">
	<div data-ng-controller="SummaryController" class="my-controller"> 
		<script type="text/ng-template" id="inventoryModal.html">
			<div class="modal-header">
    			<h3 class="modal-title"> Return Product </h3>
    		</div>
   			<div class="modal-body">
			<table>
				<tr>
					<td style="text-align:right" width="150px">Product Code : </td>
					<td colspan = "3">{{productcode}}</td>
				</tr>
				<tr>
		        	<td style="text-align:right" width="150px">Stock Location : </td>
					<td colspan = "3">{{location}}</td>
				</tr>
       	 		<tr>
					<td style="text-align:right" width="150px">Return Stocks : </td>
					<td><input id="stocks" name="stocks" type="text" data-ng-model="returnunits.stocks" data-ng-init="stocks='0'" /></td>
				</tr>
				<tr>	
					<td style="text-align:right" width="150px">Comments : </td>
					<td><textarea id="comments" name="comments" data-ng-model="returnunits.comments" style="width:500px; height:100px; font-family:Arial, MS Sans Serif, Verdana; font-size:12px;"  /></textarea></td>
				</tr>
			<table>
    		</div>
			<div class="processing2" data-ng-show="loading2">
            	<div class="input-group  pull-right">
                	<img class="spinner"  src='<c:url value="/resources/images/loading51.gif" />' />
            	</div>
        	</div>
    		<div class="modal-footer">
   				<button class="btn btn-primary" type="button" data-ng-click="addReturnUnits()">Save</button>
    			<button class="btn btn-warning" type="button" data-ng-click="cancel()">Cancel</button>
    		</div>
		</script>
		<table>
		 	<tr>
		        <td style="text-align:right" width="390px">Product Code : </td>
				<td><input id="productcode" name="productcode" type="text" data-ng-model="productcode"  data-ng-init="productcode='-'" size="70px"/></td>
			</tr>
	        <tr>
		        <td style="text-align:right" width="390px">Stock Level : </td>
				<td>
					<select name="repeatstocklevellist" id="repeatstocklevellist" data-ng-model="stocklevellist.selected" style="height:30px; width:425px">
	      				<option data-ng-repeat="stocklevel in stocklevellist.availableOptions" value="{{stocklevel.level}}">{{stocklevel.level}}</option>
	   	 			</select>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="390px">Stock Location : </td>
				<td><input id="stocklocation" name="stocklocation" type="text" data-ng-model="stocklocation" data-ng-init="stocklocation='-'" size="70px"/></td>
	        </tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		        	<button data-ng-click="getInventoryList()">Search</button>
		        	<button data-ng-click="cancel()">Cancel</button>
		        </td>
	        </tr>
		</table>
		
		<div class="processing" data-ng-show="loading">
            <div class="input-group  pull-right">
                <img class="spinner"  src='<c:url value="/resources/images/loading51.gif" />' />
            </div>
        </div>
        
		<div class="row">
           <div class="col-xs-4">
             <h3>Page: {{ currentPage }}</h3>
           </div>
           
           <div class="col-xs-4">
             <label for="search">Filter Result:</label>
             <input data-ng-model="q" id="search" class="form-control" placeholder="Filter text">
           </div>
           
           <div class="col-xs-4">
             <label for="search">items per page:</label>
             <input type="number" min="1" max="100" class="form-control" data-ng-model="pageSize">
           </div>
        </div>
        <div class="panel panel-default">
        	<div class="panel-body">
			<table id="product">
				<thead>
					<tr>
						<th>No</th>
						<th>Date</th>
						<th>Product Code</th>
						<th>Brand</th>
						<th>Product Name</th>
						<th>Weight</th>
						<th>Mass</th>
						<th>Location</th>
						<th>Stocks</th>
						<th>Return</th>
						<th>Bal</th>
						<th>Comments</th>
					</tr>
				</thead>
				<tbody>
					<tr data-dir-paginate="inventory in inventorylist | filter:q | itemsPerPage: pageSize" data-current-page="currentPage" data-ng-click="open(inventory.productCode, inventory.location, inventory.units-inventory.returnUnits); $event.preventDefault(); ">
					   	<td>{{ $index + 1 }}</td>
					   	<td>{{ inventory.transactionDate }}</td>
					    <td>{{ inventory.productCode }}</td>
					    <td>{{ inventory.brand }}</td>
					    <td>{{ inventory.productName }}</td>
    					<td>{{ inventory.packWeight }}</td>
					    <td>{{ inventory.packMass }}</td>
						<td>{{ inventory.location }}</td>
					    <td>{{ inventory.units }}</td>
					    <td>{{ inventory.returnUnits }}</td>
					    <td>{{inventory.units-inventory.returnUnits}}</td>
					    <td>{{ inventory.comments}}</td>
					 </tr>
				</tbody>
			</table>
			</div>
		</div>
    </div>	
	<div data-ng-controller="PageController" class="other-controller">
		 <small></small>
         <div class="text-center">
         	<dir-pagination-controls boundary-links="true" on-page-change="pageChangeHandler(newPageNumber)" template-url='<c:url value="/resources/js/angular/dirPagination.tpl.html" />'></dir-pagination-controls>
       	</div>
	</div>
	</div>
	</div>
</div>
</section><!-- /#content -->
<%@ include file="productfooter.jsp" %>

</body>
