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
	
</style> 
<script>
	
    var inventoryapp = angular.module('inventoryapp', ['angularUtils.directives.dirPagination', 'ui.bootstrap', 'xeditable' ]);
    
    inventoryapp.controller('SummaryController', function ($scope, $http){
    	
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
  		      {id: '1', level: 'Units on Hand'},
  		      {id: '2', level: 'Unit Finished'},
  		    ],
     	};
        
  		$scope.saveEditedLocation = function(data) {
        	
        	$scope.loading = true;
    		
            $http.post('updateInventoryLocation/' + data.location + '/' + encodeURIComponent(data.pcode))
            .success(function(data, status, headers, config) {
            	alert("Successfully Saved");
            	$scope.loading = false;
            })
            .error(function(data, status, headers, config) {
            	alert("Error");
            	$scope.loading = false;
            });
        };

        
        $scope.cancel = function () {
            window.location.reload(); 
        };
        
        $scope.pageChangeHandler = function(num) {
            console.log('changed to ' + num);
        };
    });
    function PageController($scope) {
   	  $scope.pageChangeHandler = function(num) {
   	    console.log('going to page ' + num);
   	  };
  }
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
						<th>BPrice</th>
						<th>RRPrice</th>
						<th>Units</th>
						<th>Photo</th>
						<th>Edit</th>
					</tr>
				</thead>
				<tbody>
					<tr data-dir-paginate="inventory in inventorylist | filter:q | itemsPerPage: pageSize" data-current-page="currentPage">
					   	<td>{{ $index + 1 }}</td>
					   	
					   	<td><span data-editable-text="inventory.transactionDate" data-e-name="tdate" data-e-form="rowform" data-e-style="width: 67px" data-e-disabled="disabled">
					    {{ inventory.transactionDate }}</span></td>
					    
					    <td><span data-editable-text="inventory.productCode" data-e-name="pcode" data-e-form="rowform" data-e-style="width: 70px" data-e-disabled="disabled">
					    {{ inventory.productCode }}</span></td>
					    
					    <td><span data-editable-textarea="inventory.brand" data-e-name="brandname" data-e-form="rowform" data-e-style="width: 75px;  height: 60px" data-e-disabled="disabled">
					    {{ inventory.brand }} </span></td>
					    
					    <td><span data-editable-textarea="inventory.productName" data-e-name="productname" data-e-form="rowform" data-e-style="width: 130px; height: 60px" data-e-disabled="disabled">
					    {{ inventory.productName }} </span></td>
					    
    					<td><span data-editable-text="inventory.packWeight" data-e-name="packweight" data-e-form="rowform" data-e-style="width: 35px" data-e-disabled="disabled">
					    {{ inventory.packWeight }} </span></td>
					    
					    <td><span data-editable-text="inventory.packMass" data-e-name="packmass" data-e-form="rowform" data-e-style="width: 50px" data-e-disabled="disabled">
					    {{ inventory.packMass }} </span></td>
						
						<td><span data-editable-text="inventory.location" data-e-name="location" data-e-form="rowform" data-e-style="width: 60px">
					    {{ inventory.location }} </span></td>
					
					    <td><span data-editable-text="inventory.buyingPrice" data-e-name="buyingprice" data-e-form="rowform" data-e-style="width: 55px" data-e-disabled="disabled">
					    {{ inventory.buyingPrice }} </span></td>
						
					    <td><span data-editable-text="inventory.rrprice" data-e-name="rrprice" data-e-form="rowform" data-e-style="width: 55px" data-e-disabled="disabled">
					    {{ inventory.rrprice }} </span></td>
					    
					    <td><span data-editable-text="inventory.units" data-e-name="units" data-e-form="rowform" data-e-style="width: 40px" data-e-disabled="disabled">
					    {{ inventory.units }} </span></td>
					    
					    <td><span data-editable-text="inventory.photo" data-e-name="photo" data-e-form="rowform" data-e-style="width: 30px" data-e-disabled="disabled">
					    {{ inventory.photo }} </span></td>
					    
					    <td style="white-space: nowrap">
					    <!-- form -->
					        <form data-editable-form name="rowform" data-onbeforesave="saveEditedLocation($data)" data-ng-show="rowform.$visible" class="form-buttons form-inline">
					          <button type="submit" data-ng-disabled="rowform.$waiting" class="btn btn-primary">
					            save
					          </button>
					          <button type="button" data-ng-disabled="rowform.$waiting" data-ng-click="rowform.$cancel()" class="btn btn-default">
					            cancel
					          </button>
					        </form>
	        				<div class="buttons" data-ng-show="!rowform.$visible">
					          <button class="btn btn-primary" data-ng-click="rowform.$show()">edit</button>
					        </div>  
				        </td>
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
