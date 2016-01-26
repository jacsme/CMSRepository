<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html data-ng-app="supplierapp">
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
	  
	<link rel="stylesheet" href='<c:url value="/resources/css/jquery-ui.css" />' />
		
	<script charset="utf-8" src='<c:url value="/resources/js/jquery-1.9.1.js" />'></script>
	<script charset="utf-8" src='<c:url value="/resources/js/jquery-ui.js" />'></script>
</head>
  
  
<style>
	#supplier table{
		width: 1500px;
	}
	#supplier table, th , td {
	  border: 1px solid grey;
	  border-collapse: collapse;
	  padding: 5px;
	}
	#supplier table tr:nth-child(odd) {
	  background-color: #f1f1f1;
	}
	#supplier table tr:nth-child(even) {
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
<script type="text/javascript">
	
	jdlog = jQuery.noConflict( true );
	
	jdlog(document).ready(function(){
		
		console.log( "1st loaded jQuery version ($): " + jdlog.fn.jquery + "<br>" );
		
		jdlog(function () {
	    	jdlog("#dialog-success").dialog({
				autoOpen: false,
				modal: true,
				show: {
					effect: "blind",
					duration: 1000
				},
				hide: {
					effect: "explode",
					duration: 1000
				},
				buttons: {
				Ok: function() {
					jdlog("#dialog-success").dialog("close");
					}
				}
			});
		});
		
	    jdlog(function () {
			jdlog("#dialog-error").dialog({
				autoOpen: false,
				modal: true,
				show: {
					effect: "blind",
					duration: 1000
				},
				hide: {
					effect: "explode",
					duration: 1000
				},
				buttons: {
				Ok: function() {
					jdlog("#dialog-error").dialog("close");
					}
				}
			});
		});	
	});
	
    </script>
<script>
	
    var supplierapp = angular.module('supplierapp', ['angularUtils.directives.dirPagination', 'ui.bootstrap', 'xeditable' ]);
    
    supplierapp.controller('SupplierController', function ($scope, $http){
    	
    	$scope.currentPage = 1;
    	$scope.pageSize = 10;
    	
    	$http.get('getSupplierList/').success(function (data) {
  		   $scope.suppliers = data;
  	    });
    	
    	$scope.getSupplierProductList = function() {  
    		console.log("Supplier" + $scope.supplier.selected);
    		if($scope.supplier.selected !== ''){
	   			var paramsuppliercode= $scope.supplier.selected;
		    	var prambrandname = $scope.brandname; 
		    	var paramphotocode = $scope.photocode; 
	    		
		    	$scope.loading = true;
	    		
	    		if(paramphotocode === ''){ paramphotocode = '-'}
	    		if(prambrandname === ''){ prambrandname = '-'}
	    		
	        	$http.get('getSupplierProductList/' + encodeURIComponent(paramsuppliercode) + '/' + encodeURIComponent(prambrandname) + '/' + encodeURIComponent(paramphotocode))
	            .success(function(data, status, headers, config) {
	            	console.log("search >> " + data);
	            	$scope.supplierproductlist = data;
	            	$scope.loading = false;
	            })
	            .error(function(data, status, headers, config) {
	            	$scope.loading = false;
	            });
    		}else{
    			alert('Please select Supplier');
    		}
        };
        
        $scope.saveEditedSupplierProduct = function(data) {
        	
        	$scope.loading = true;
        	var paramsuppliercode= $scope.supplier.selected;
        	
            $http.post('updateSupplierProduct/' + paramsuppliercode + '/' + encodeURIComponent(data.productcode) + '/' 
            		+ encodeURIComponent(data.packunit) + '/' + data.packprice + '/' + data.paymentterms)
            .success(function(data, status, headers, config) {
            	jdlog("#dialog-success").dialog("open").html("Successfully editing the values");
	            	$scope.loading = false;
            })
            .error(function(data, status, headers, config) {
            	jdlog("#dialog-error").dialog("open").html("Error!");
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
    supplierapp.controller('PageController', PageController);
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
	<div data-ng-controller="SupplierController" class="my-controller"> 
		<table>
	        <tr>
		        <td style="text-align:right" width="390px">Supplier Name : </td>
				<td>
					<select name="repeatsupplier" id="repeatsupplier" data-ng-model="supplier.selected" style="height:30px; width:425px">
		      			<option data-ng-repeat="supplier in suppliers" value="{{supplier.supplierCode}}">{{supplier.supplierName}}</option>
		   	 		</select>
	   	 		</td>
			</tr>
			<tr>
		        <td style="text-align:right" width="390px">Brand Name : </td>
				<td><input id="brandname" name="brandname" type="text" data-ng-model="brandname"  data-ng-init="brandname='-'" size="70px"/></td>
			</tr>
			<tr>
		        <td style="text-align:right" width="390px">Product Code : </td>
				<td><input id="photocode" name="photocode" type="text" data-ng-model="photocode"  data-ng-init="photocode='-'" size="70px"/></td>
			</tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		        	<button data-ng-click="getSupplierProductList()">Search</button>
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
			<table id="supplier">
				<thead>
					<tr>
						<th>No</th>
						<th>Supplier Name</th>
						<th>Brand Name</th>
						<th>Product Code</th>
						<th>Product Name</th>
						<th>Bar Code</th>
						<th>Weight</th>
						<th>Mass</th>
						<th>Pack Unit</th>
						<th>Pack Price</th>
						<th>Terms</th>
						<th>GST</th>
						<th>Edit</th>
					</tr>
				</thead>
				<tbody>
					<tr data-dir-paginate="supplierproducts in supplierproductlist | filter:q | itemsPerPage: pageSize" data-current-page="currentPage">
					   	<td>{{ $index + 1 }}</td>
					    
					    <td><span data-editable-textarea="supplierproducts.supplierName" data-e-name="suppliername" data-e-form="rowform" data-e-style="width: 140px;  height: 60px" data-e-disabled="disabled">
					    {{ supplierproducts.supplierName }} </span></td>
					    
					    <td><span data-editable-textarea="supplierproducts.brandName" data-e-name="brandname" data-e-form="rowform" data-e-style="width: 73px; height: 60px" data-e-disabled="disabled">
					    {{ supplierproducts.brandName }}</span></td>
					    
					    <td><span data-editable-text="supplierproducts.productCode" data-e-name="productcode" data-e-form="rowform" data-e-style="width: 73px" data-e-disabled="disabled">
					    {{ supplierproducts.productCode }} </span></td>
					    
					    <td><span data-editable-textarea="supplierproducts.productName" data-e-name="productname" data-e-form="rowform" data-e-style="width: 150px; height: 60px" data-e-disabled="disabled">
					    {{ supplierproducts.productName }} </span></td>
					    
					    <td><span data-editable-text="supplierproducts.photoCode" data-e-name="photocode" data-e-form="rowform" data-e-style="width: 89px" data-e-disabled="disabled">
					    {{ supplierproducts.photoCode }}</span></td>
					    
					    <td><span data-editable-text="supplierproducts.packWeight" data-e-name="packweight" data-e-form="rowform" data-e-style="width: 50px" data-e-disabled="disabled">
					    {{ supplierproducts.packWeight }} </span></td>
					    
					   	<td><span data-editable-text="supplierproducts.packMass" data-e-name="packmass" data-e-form="rowform" data-e-style="width: 40px" data-e-disabled="disabled">
					    {{ supplierproducts.packMass }} </span></td>
					    
					    <td><span data-editable-text="supplierproducts.packUnit" data-e-name="packunit" data-e-form="rowform" data-e-style="width: 30px">
					    {{ supplierproducts.packUnit }} </span></td>
	
					    <td><span data-editable-text="supplierproducts.packPrice" data-e-name="packprice" data-e-form="rowform" data-e-style="width: 55px">
					    {{ supplierproducts.packPrice }} </span></td>
					    
					    <td><span data-editable-text="supplierproducts.paymentTerms" data-e-name="paymentterms" data-e-form="rowform" data-e-style="width: 25px">
					    {{ supplierproducts.paymentTerms }} </span></td>
					    
					    <td><span data-editable-text="supplierproducts.gst" data-e-name="gst" data-e-form="rowform" data-e-style="width: 15px" data-e-disabled="disabled">
					    {{ supplierproducts.gst }} </span></td>
	
					    <td style="white-space: nowrap">
					    <!-- form -->
					        <form data-editable-form name="rowform" data-onbeforesave="saveEditedSupplierProduct($data)" data-ng-show="rowform.$visible" class="form-buttons form-inline">
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
