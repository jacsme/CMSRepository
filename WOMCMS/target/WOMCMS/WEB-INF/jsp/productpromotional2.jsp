<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html data-ng-app="productapp">
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
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/input.css" />'/>
   <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/button.css" />'/>
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
	
    var productapp = angular.module('productapp', ['angularUtils.directives.dirPagination', 'ui.bootstrap', 'xeditable' ]);
    
    productapp.controller('ProductController', function ($scope, $http){
    	
    	$scope.currentPage = 1;
    	$scope.pageSize = 10;
    	
    	$scope.getProductList = function() {  
    		$scope.loading = true;
    		
    		if($scope.productcode === ''){ $scope.productcode = '-'}
    		if($scope.brand === ''){ $scope.brand = '-'}
    		if($scope.categorycode === ''){ $scope.categorycode = '-'}
    		
        	$http.get('searchPromoProducts/' + $scope.productcode + '/' + $scope.brand + '/' + $scope.categorycode)
            .success(function(data, status, headers, config) {
            	$scope.productlist = data;
            	$scope.loading = false;
            })
            .error(function(data, status, headers, config) {
            	alert('Error in Parameters')
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
    productapp.controller('PageController', PageController);
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
	<div data-ng-controller="ProductController" class="my-controller"> 
		<table>
			<tr>
				<td colspan = "2" style="text-align:center"> <h3>Product Promotional (All)</h3> </td>
			</tr>
			<tr>
				<td style="text-align:right" width="390px">Category Code : </td>
				<td><input id="categorycode" name="categorycode" type="text" data-ng-model="categorycode" data-ng-init="categorycode='-'" size="70px"/></td>
	        </tr>
	         <tr>
		        <td style="text-align:right" width="390px">Brand Name : </td>
				<td><input id="brand" name="brand" type="text" data-ng-model="brand" data-ng-init="brand='-'" size="70px"/></td>
			</tr>
			 <tr>
		        <td style="text-align:right" width="390px">Product Code : </td>
				<td><input id="productcode" name="productcode" type="text" data-ng-model="productcode"  data-ng-init="productcode='-'" size="70px"/></td>
			</tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		        	<button class="button-blue" data-ng-click="getProductList()">Search</button>
		        	<button class="button-blue" data-ng-click="cancel()">Cancel</button>
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
						<th>Supplier Code</th>
						<th>Supplier Name</th>
						<th>Product Code</th>
						<th>Brand Name</th>
						<th>Product Name</th>
						<th>Buying Price</th>
						<th>RRPrice</th>
						<th>Disc %</th>
						<th>Disc Amount</th>
						<th>Promotional Price</th>
					</tr>
				</thead>
				<tbody>
					<tr data-dir-paginate="products in productlist | filter:q | itemsPerPage: pageSize" data-current-page="currentPage">
					   	<td>{{ $index + 1 }}</td>
					   	
					   	<td><span data-editable-text="products.supplierCode" data-e-name="suppliercode" data-e-form="rowform" data-e-style="width: 85px" data-e-disabled="disabled">
					    {{ products.supplierCode }}</span></td>
					
						<td><span data-editable-text="products.supplierName" data-e-name="suppliername" data-e-form="rowform" data-e-style="width: 85px; height: 60px" data-e-disabled="disabled">
					    {{ products.supplierName }}</span></td>
					
					    <td><span data-editable-text="products.productCode" data-e-name="pcode" data-e-form="rowform" data-e-style="width: 85px" data-e-disabled="disabled">
					    {{ products.productCode }}</span></td>
					    
					    <td><span data-editable-textarea="products.brandName" data-e-name="brandname" data-e-form="rowform" data-e-style="width: 60px;  height: 60px" data-e-disabled="disabled">
					    {{ products.brandName }} </span></td>
					    
					    <td><span data-editable-textarea="products.productName" data-e-name="productname" data-e-form="rowform" data-e-style="width: 165px; height: 60px" data-e-disabled="disabled">
					    {{ products.productName }} </span></td>
					    
					    <td><span data-editable-text="products.buyingPrice" data-e-name="packPrice" data-e-form="rowform" data-e-style="width: 50px">
					    {{ products.buyingPrice }} </span></td>
	
					    <td><span data-editable-text="products.retailPrice" data-e-name="rrprice" data-ng-model="rrprice" data-e-form="rowform" data-e-style="width: 55px">
					    {{ products.retailPrice }} </span></td>
					    
					    <td>{{products.promotionalPrice > 0 ? (products.buyingPrice-products.promotionalPrice)/products.buyingPrice * 100 : 0.00 }} </td>
					    <!--  <td>{{products.promotionalPrice > 0 ? (products.retailPrice- products.promotionalPrice)/products.retailPrice * 100 : 0.00 }} </td> --> 
					    <!--  <td><span data-editable-text="products.discount" data-e-name="discount" data-ng-model="discount" data-e-form="rowform" data-e-style="width: 55px"> -->
					    <!--  {{ products.discount}} </span></td> -->
					    
					    <td><span data-editable-text="products.discountAmount" data-e-name="discountamount" data-ng-model="discountamount" data-e-form="rowform" data-e-style="width: 55px">
					    {{ products.discountAmount}} </span></td>
					    
					    <td><span data-editable-text="products.promotionalPrice" data-e-name="promotionalprice" data-ng-model="promotionalprice" data-e-form="rowform" data-e-style="width: 35px">
					    {{ products.promotionalPrice }} </span></td>
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
