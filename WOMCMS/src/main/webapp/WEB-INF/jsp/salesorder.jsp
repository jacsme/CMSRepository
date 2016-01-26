<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html data-ng-app="salesorderapp">
<head>
<meta charset="utf-8" />
<title>WOM Content Management System</title>
<head>
	 <link rel="stylesheet" type="text/css" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.1.0/css/font-awesome.min.css">
  	<!-- <link rel="stylesheet" type="text/css" href="http://720kb.github.io/csshelper/assets/ext/src/helper.css"> -->
	
	<link data-require="bootstrap-css@3.1.1" data-semver="3.1.1" rel="stylesheet" href='<c:url value="/resources/css/bootstrap.min.css" />' />
	
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/main.css" />'/>
	<link rel="stylesheet" href='<c:url value="/resources/css/xeditable.css" />' />
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/angular-datepicker.css" />'/>
	
	<script data-require="angular.js@1.3.0" data-semver="1.3.0" src="https://code.angularjs.org/1.3.0/angular.js"></script>
	<script src='<c:url value="/resources/js/xeditable/xeditable.js" />'></script>
	<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.10.0.min.js"></script>
	<script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> 
	<script src='<c:url value="/resources/js/angular/dirPagination.js" />'></script>
	<script data-require="bootstrap@3.1.1" data-semver="3.1.1" src='<c:url value="/resources/js/bootstrap/bootstrap.min.js" />'></script>
	<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-animate.js"></script>
	
	<script type="text/javascript" src='<c:url value="/resources/js/angular/angular.min.js"/>'></script>
	<script type="text/javascript" src='<c:url value="/resources/js/angular/angular-route.min.js"/>'></script>
	<script type="text/javascript" src='<c:url value="/resources/js/angular/angular-datepicker.js" />'></script>
	
</head>
  
  
<style>
	#product table{
		width: 1500px;
	}
	#product table, th , td {
	  border: 1px solid grey;
	  border-collapse: collapse;
	  padding: 5px;
	}
	#product table tr:nth-child(odd) {
	  background-color: #f1f1f1;
	}
	#product table tr:nth-child(even) {
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
	
    var salesorderapp = angular.module('salesorderapp', ['angularUtils.directives.dirPagination', 'ui.bootstrap', '720kb.datepicker']);
    
    salesorderapp.controller('SOController', function ($scope, $http, $window){
    	
    	$scope.currentPage = 1;
    	$scope.pageSize = 10;
    	
    	$scope.getSOList = function() {  
    		$scope.loading = true;
    		
    		if($scope.salesordercode === ''){ $scope.salesordercode = '-'}
    		if($scope.customercode === ''){ $scope.customercode = '-'}
    		if($scope.datedelivered === ''){ $scope.datedelivered = '-'}
    		
        	$http.get('searchSalesOrder/' + $scope.salesordercode + '/' + $scope.customercode + '/' + $scope.datedelivered)
            .success(function(data, status, headers, config) {
            	console.log('Sales Order List' +data);
            	
            	$scope.salesorderlist = data;
            	$scope.loading = false;
            })
            .error(function(data, status, headers, config) {
            	alert('Error in Parameters')
            	$scope.loading = false;
            });
        };
        
      	$scope.openSODocument = function(sonumber) {
      		console.log('Sales Order Code' + sonumber);
      	    $window.open('https://s3-ap-southeast-1.amazonaws.com/salesordersmlqa/' + sonumber + '.pdf', '_blank');
      	};
        
        $scope.cancel = function () {
            window.location.reload(); 
        };
        
        $scope.pageChangeHandler = function(num) {
            console.log('meals page changed to ' + num);
        };
    });
    function PageController($scope) {
   	  $scope.pageChangeHandler = function(num) {
   	    console.log('going to page ' + num);
   	  };
  }
  salesorderapp.controller('PageController', PageController);
  
</script>
</head>

<body id="mainpage" class="mainpage">
<header id="banner" class="body">
	<h1>Content Management System</h1>
	<c:url var="home" value="/wom/cms/home"/>
	<c:url var="password" value="/wom/cms/password"/>
	<c:url var="uploading" value="/wom/cms/uploadimages"/>
	<c:url var="amendproducts" value="/wom/cms/amendproducts"/>
	<c:url var="purchaseorders" value="/wom/cms/purchaseorders"/>
	<c:url var="maindetails" value="/wom/cms/maindetails"/>
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
	<div data-ng-controller="SOController" class="my-controller"> 
		<table>
			<tr>
				<td style="text-align:right" width="390px">Sales Order Code : </td>
				<td><input id="salesordercode" name="salesordercode" type="text" data-ng-model="salesordercode" data-ng-init="salesordercode='-'" size="70px"/></td>
	        </tr>
	         <tr>
		        <td style="text-align:right" width="390px">Customer Code : </td>
				<td><input id="customercode" name="customercode" type="text" data-ng-model="customercode" data-ng-init="customercode='-'" size="70px"/></td>
			</tr>
			<tr>
		        <td style="text-align:right" width="390px">Date Delivered : </td>
				<td>
				<div class="datepicker"
				     data-date-format="yyyy-MM-dd"
				     
				     data-button-prev='<i class="fa fa-arrow-circle-left"></i>'
				     data-button-next='<i class="fa fa-arrow-circle-right"></i>'>
      				 <input class="angular-datepicker-input" id="datedelivered" name="datedelivered" type="text" data-ng-model="datedelivered" data-ng-init="datedelivered='-'" min="{{date | date: 'yyyy-MM-dd'}}" size="40px"/>
    			</div>
			</td>	
			</tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		        	<button data-ng-click="getSOList()">Search</button>
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
						<th>SO Code</th>
						<th>Address</th>
						<th>Customer Code</th>
						<th>Date Ordered</th>
						<th>Delivery Date</th>
						<th>Delivery Time</th>
						<th>WOM Coin</th>
						<th>Purchase Amount</th>
						<th>Processed By</th>
						<th>Status</th>
					</tr>
				</thead>
				<tbody >
					<tr data-dir-paginate="salesorder in salesorderlist | filter:q | itemsPerPage: pageSize" data-current-page="currentPage">
					   	<td>{{ $index + 1 }}</td>
					    <td><a href="#" data-ng-click="openSODocument(salesorder.salesOrderCode)">{{ salesorder.salesOrderCode }}</a></td>
					    <td>{{ salesorder.address }}</td>
					    <td>{{ salesorder.customerCode }}</td>
					    <td>{{ salesorder.dateOrdered }}</td>
					    <td>{{ salesorder.deliveryDate }}</td>
					    <td>{{ salesorder.deliveryTime }}</td>
					    <td>{{ salesorder.womCoin }}</td>
					    <td>{{ salesorder.purchaseAmount }}</td>
					    <td>{{ salesorder.staffCode }}</td>
					    <td>{{ salesorder.status }}</td>
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
