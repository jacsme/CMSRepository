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
  
  <script data-require="angular.js@1.3.0" data-semver="1.3.0" src="https://code.angularjs.org/1.3.0/angular.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.18/angular-sanitize.js"></script>
  <link rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.css">

  <!-- ui-select files -->
  <link href='<c:url value="/resources/css/select.css" />' />
  <script src='<c:url value="/resources/js/angular/select.js" />'></script>
  
  <!-- Select2 theme -->
  <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/select2/3.4.5/select2.css">
  <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/main.css" />'/>
  <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/input.css" />'/>
  <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/button.css" />'/>
  <script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script> 
  <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.4.0/angular-messages.js"></script>
  <script src="//ajax.googleapis.com/ajax/libs/angularjs/1.5.0-beta.2/angular.min.js"></script>
  
  <link rel="stylesheet" href='<c:url value="/resources/css/jquery-ui.css" />' />
	
  <script charset="utf-8" src='<c:url value="/resources/js/jquery-1.9.1.js" />'></script>
  <script charset="utf-8" src='<c:url value="/resources/js/jquery-ui.js" />'></script>
  <style>
    body {
      padding: 15px;
    }

    .select2 > .select2-choice.ui-select-match {
      /* Because of the inclusion of Bootstrap */
      height: 29px;
    }

    .selectize-control > .selectize-dropdown {
      top: 36px;
    }
    
    .processing {  
	    position:absolute;
	    top:500px;
	    left:620px;
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
    
    var productapp = angular.module('productapp',  ['ngSanitize', 'ui.select']);
    productapp.filter('propsFilter', function() {
    	  return function(items, props) {
    		//console.log(JSON.stringify(items) + " " + JSON.stringify(props));
    		
    	    var out = [];

    	    if (angular.isArray(items)) {
    	      items.forEach(function(item) {
    	        var itemMatches = false;

    	        var keys = Object.keys(props);
    	        for (var i = 0; i < keys.length; i++) {
    	          var prop = keys[i];
    	          var text = props[prop].toLowerCase();
    	          
    	          if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
    	            itemMatches = true;
    	            break;
    	          }
    	        }

    	        if (itemMatches) {
    	          out.push(item);
    	        }
    	      });
    	    } else {
    	      // Let the output be the input untouched
    	      out = items;
    	    }

    	    return out;
    	  }
    	});
    
    productapp.controller('ProductController', function ($scope, $http){
    	
    	  $scope.product = {
  			supplierpackqty : 0,
           	packunit : '',
           	packprice : '',
           	paymentterms  : '',
           	storecode  : 'MW01',
           	brandname  : '',
           	productname  : '',
           	barcode  : '',
           	unitquantity  : 1,
           	packquantity  : 0,
           	rrprice  : '',
           	packweight  : '',
           	compareweight  : '',
           	checkoutweight : '',
           	discount : 0,
           	inventorylevel : '10000',
           	stockleveldays : '100',
           	keepfresh : 0,
           	description : '-'
    	  };
    	  
    	  $http.get('getSupplierList/').success(function (data) {
    		   //$scope.supplier = {};
    		   $scope.suppliers = data;
    	  });
    	  
    	  $http.get('getCategoryList/').success(function (data) {
	   		   //$scope.category = {};
	   		   $scope.categories = data;
	   	  });
    	 
    	  $scope.gstlist = {
	  		    availableOptions: [
	  		      {id: '1', name: 'S'},
	  		      {id: '2', name: 'Z'},
	  		    ],
	     	};
    	    	
   	    	$scope.masslist = {
   	   		    availableOptions: [
   	   		      {id: '1', name: 'GRAM'},
   	   		      {id: '2', name: 'MG'},
   	   		   	  {id: '3', name: 'ML'},
   			      {id: '4', name: 'KG'},
   			      {id: '5', name: 'LITRE'},
   	   		      {id: '6', name: 'PACK'},
   	   		    ],
   	      	};
   	    	
   	    	$scope.comparemasslist = {
  	   		    availableOptions: [
  	   		      {id: '1', name: 'GRAM'},
  	   		      {id: '2', name: 'MG'},
  	   		   	  {id: '3', name: 'ML'},
  			      {id: '4', name: 'KG'},
  			      {id: '5', name: 'LITRE'},
  	   		      {id: '6', name: 'PACK'},
  	   		    ],
  	      	};
   	    	
   	    	$scope.cancel = function () {
   	            window.location.reload(); 
   	        };
   	        
   	        $scope.addnextsupplier = function(){
   	        	$scope.supplier.selected = undefined;
           		$scope.category.selected  = undefined;
           		$scope.masslist.selected  = undefined;
           		$scope.gstlist.selected  = undefined;
           		$scope.comparemasslist.selected  = undefined;
           		
	           	$scope.product = {
         			supplierpackqty : 0,
                  	packunit : '',
                  	packprice : '',
                  	paymentterms  : '',
                  	storecode  : 'MW01',
                  	brandname  : '',
                  	productname  : '',
                  	barcode  : '',
                  	unitquantity  : 1,
                  	packquantity  : 0,
                  	rrprice  : '',
                  	packweight  : '',
                  	compareweight  : '',
                  	checkoutweight : '',
                  	discount : 0,
                  	inventorylevel : '10000',
                  	stockleveldays : '100',
                  	keepfresh : 0,
                  	description : '-'
	         	 };
   	        }
   	        
	   	     $scope.addnextproduct = function(){
	   	    	 
	   	    	$scope.category.selected  = undefined;
           		$scope.masslist.selected  = undefined;
           		$scope.gstlist.selected  = undefined;
           		$scope.comparemasslist.selected  = undefined;
           		
	           	$scope.product = {
         			supplierpackqty : 0,
                  	packunit : '',
                  	packprice : '',
                  	paymentterms  : '',
                  	storecode  : 'MW01',
                  	brandname  : '',
                  	productname  : '',
                  	barcode  : '',
                  	unitquantity  : 1,
                  	packquantity  : 0,
                  	rrprice  : '',
                  	packweight  : '',
                  	compareweight  : '',
                  	checkoutweight : '',
                  	discount : 0,
                  	inventorylevel : '10000',
                  	stockleveldays : '100',
                  	keepfresh : 0,
                  	description : '-'
	         	  };
		     }
   	     
   	     	$scope.saveNewProduct = function() {
   	    		console.log('rrprice' + $scope.product.rrprice);
   	     		if(typeof $scope.supplier === 'undefined') {
   	     			alert('Please select the supplier');
   	     		}else if (typeof $scope.category === 'undefined') {
   	     			alert('Please select the category');
   	     		}else if (typeof $scope.masslist.selected === 'undefined'){
	     			alert('Please select the pack mass type');
   	     		}else if (typeof $scope.comparemasslist.selected === 'undefined'){
     				alert('Please select the compare mass type');
   	     		}else if (typeof $scope.gstlist.selected === 'undefined'){
   	     			alert('Please select the gst type');
   	     		}else if ($scope.product.packweight === '0'){
   	     			alert('Please input pack weight, 0 or empty is not acceptable');
   	     		}else if ($scope.product.rrprice === '0' || $scope.product.rrprice === ''){
   	     			alert('Please RRPrice is not valid, 0 or empty is not acceptable');
   	     		}else if ($scope.product.compareweight === '0' || $scope.product.compareweight === ''){
   	     			alert('Please input compare weight, 0 or empty is not acceptable');
   	     		}else if ($scope.product.checkoutweight === '0' || $scope.product.checkoutweight === ''){
	     			alert('Please input checkout weight, 0 or empty is not acceptable');
   	     		}else{
   	    			var paramsuppliercode= $scope.supplier.selected;
	   	    		var paramcategorycode = $scope.category.selected; 
   	    		
	   	    		//angular.forEach($scope.supplier.selected.supplierCode, function(value, key){
	   	    		//	if(key === 'supplierCode'){ paramsuppliercode = value;	}
	   	            //});
	   	            
	   	    		//angular.forEach($scope.category.selected.categoryCode, function(value, key){
	   	    		//	if(key === 'categoryCode'){ paramcategorycode = value;	}
	   	            //});
	   	    		
	   	    		$scope.loading = true;
	   	    		var formdata = {
	   	    			"supplierCode": paramsuppliercode,
	   	    			"supplierPackqty" : $scope.product.supplierpackqty,
	   	    			"packUnit" : $scope.product.packunit,
	   	    			"packPrice" : $scope.product.packprice,
	   	    			"paymentTerms" : $scope.product.paymentterms,
	   	    			"storeCode": $scope.product.storecode,
	   	    			"categoryCode": paramcategorycode,
	   	    			"brandName": $scope.product.brandname,
	   	    			"productName": $scope.product.productname,
	   	    			"barcode": $scope.product.barcode,
	   	    			"unitQuantity": $scope.product.unitquantity,
	   	    			"packQuantity" : $scope.product.packquantity,
	   	    			"rRPrice": $scope.product.rrprice,
	   	    			"packWeight" : $scope.product.packweight,
	   	    			"packMass": $scope.masslist.selected,
	   	    			"gst": $scope.gstlist.selected,
	   	    			"compareWeight": $scope.product.compareweight,
	   	    			"compareMass": $scope.comparemasslist.selected,
	   	    			"checkoutWeight": $scope.product.checkoutweight,
	   	    			"discount": $scope.product.discount,
	   	    			"inventoryLevel": $scope.product.inventorylevel,
	   	    			"stockLevelDays": $scope.product.stockleveldays,
	   	    			"keepFresh": $scope.product.keepfresh,
	   	    			"description": $scope.product.description
			        };
	   	    		
			        $http.post("submitNewProduct", formdata)
	   	            .success(function(data, status, headers, config) {
	   	            	jdlog("#dialog-success").dialog("open").html("Successfully saved the product");
	   	            	$scope.loading = false;
	   	          	 	$scope.addnextproduct();
	   	            })
	   	            
	   	            .error(function(data, status, headers, config) {
	   	            	jdlog("#dialog-error").dialog("open").html("Error!");
	   	            	$scope.loading = false;
	   	            });
 	    	    }
   	        };
    });
  
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
	<c:url var="salesorder" value="/wom/cms/salesorder"/>
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
	<form name="productform" id="productform" data-ng-controller="ProductController" class="my-controller"> 
		<table>
			<tr>
				<td colspan = "6" style="text-align:center"> <h3>Add New Product</h3> </td>
			</tr>
			<tr>
				<td colspan = "6" style="text-align:left"><h3>Supplier Details</h3> 
				</td>
			</tr>
			<tr>
		        <td style="text-align:right" width="310px">Supplier: </td>
				<td colspan="5">
					<select name="repeatsupplier" id="repeatsupplier" data-ng-model="supplier.selected" style="height:30px; width:500px">
	      				<option data-ng-repeat="supplier in suppliers" value="{{supplier.supplierCode}}">{{supplier.supplierName}}</option>
	   	 			</select>
				
				</td>
				<!--  
				<td colspan="5">
				  <ui-select id="suppliercode" name="suppliercode" data-ng-model="supplier.selected.supplierCode" theme="select2" data-ng-disabled="disabled" style="min-width: 500px;">
				    <ui-select-match placeholder="Select supplier...">{{$select.selected.supplierName}}</ui-select-match>
				    <ui-select-choices repeat="supplier in suppliers | propsFilter: {supplierName: $select.search, supplierCode: $select.search}">
				      <div data-ng-bind-html="supplier.supplierName | highlight: $select.search"></div>
				      <small>
				        SupplierCode: {{supplier.supplierCode}}
				      </small>
				    </ui-select-choices>
				  </ui-select>
				</td>
				-->
				
			</tr>
			<tr>
				<td style="text-align:right" width="310px">Pack Quantity : </td>
				<td width="100px"><input id="supplierpackqty" name="supplierpackqty" type="text" data-ng-model="product.supplierpackqty" data-ng-init="supplierpackqty='0'" size="10px" min="0" max="1000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.supplierpackqty.$error.required">Pack Quantity is required</span>
					    <!-- <span class="error" data-ng-show="productform.supplierpackqty.$error.number">Please enter a valid number!</span> -->
					</div>
				</td>
				<td style="text-align:right" width="105px">Pack Unit : </td>
				<td colspan="3"  width="100px"><input id="packunit" name="packunit" type="text" data-ng-model="product.packunit" data-ng-init="packunit='0'" size="10px" min="0" max="1000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.packunit.$error.required">Pack Unit is required</span>
					    <!--  <span class="error" data-ng-show="productform.packunit.$error.number">Please enter a valid number!</span> -->
					</div>
				</td>
			</tr>
			<tr>	
				<td style="text-align:right" width="310px">Pack Price : </td>
				<td width="100px"><input id="packprice" name="packprice" type="text" data-ng-model="product.packprice" data-ng-init="packprice='0.00'" size="10px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.packprice.$error.required">Pack Price is required</span>
					</div>
				</td>
				<td style="text-align:right" width="105px">Payment Terms : </td>
				<td colspan="3"><input id="paymentterms" name="paymentterms" type="text" data-ng-model="product.paymentterms" data-ng-init="paymentterms='1'" size="10px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.paymentterms.$error.required">Payment Terms is required</span>
					    <!-- <span class="error" data-ng-show="productform.paymentterms.$error.number">Please enter a valid number!</span>  -->
					</div>
				</td>
			</tr>
			<tr>
				<td colspan = "6" style="text-align:left"><h3>Product Details</h3> </td>
			</tr>
	        <tr>
	        	<td style="text-align:right" width="310px">Store Code : </td>
				<td colspan="5" ><input id="storecode" name="storecode" type="text" data-ng-model="storecode" data-ng-init="storecode='MW01'" size="10px"/></td>
	        </tr>
			<tr>	
				<td style="text-align:right" width="310px">Category Name : </td>
				<td colspan="5" width="100px">
				  <select name="repeatcategory" id="repeatcategory" data-ng-model="category.selected" style="height:30px; width:500px">
	      				<option data-ng-repeat="category in categories" value="{{category.categoryCode}}">{{category.categoryName}} - {{category.subCategory}}</option>
	   	 			</select>
				  <!--  
				  <ui-select id="category" name="category" data-ng-model="category.selected.categoryCode" theme="select2" data-ng-disabled="disabled" style="min-width: 500px;">
				    <ui-select-match placeholder="Select Category...">{{$select.selected.subCategory}}</ui-select-match>
				    <ui-select-choices repeat="category in categories | propsFilter: {categoryName: $select.search, categoryCode: $select.search}">
				      <div data-ng-bind-html="category.subCategory | highlight: $select.search"></div>
				      <small>
				        Category Name: {{category.categoryName}}
				        Category Code: {{category.categoryCode}}
				      </small>
				    </ui-select-choices>
				  </ui-select>
				 -->
				</td>
	        </tr>
	        <tr>
	        	<td style="text-align:right" width="310px">Brand Name : </td>
				<td colspan="5"><input id="brandname" name="brandname" type="text" data-ng-model="product.brandname" size="93px" required />
					<div role="alert">
					    <span class="error" data-ng-show="productform.brandname.$error.required">Brand Name is required</span>
					</div>
				</td>
	        </tr>
	        <tr>
		        <td style="text-align:right" width="310px">Product Name : </td>
				<td colspan="5"><input id="productname" name="productname" type="text" data-ng-model="product.productname" size="93px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.productname.$error.required">Product Name is required</span>
					</div>
				</td>
				
			</tr>
			<tr>
				<td style="text-align:right" width="310px">Bar Code : </td>
				<td colspan="5"><input id="barcode" name="barcode" type="text" data-ng-model="product.barcode" size="93px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.barcode.$error.required">Bar code is required</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="310px">Unit Quantity : </td>
				<td width="100px"><input id="unitquantity" name="unitquantity" type="text" data-ng-model="product.unitquantity" data-ng-init="unitquantity='1'" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.unitquantity.$error.required">Unit Quantity is required</span>
					    <!-- <span class="error" data-ng-show="productform.unitquantity.$error.number">Please enter a valid number!</span>  -->
					</div>
				</td>
				<td style="text-align:right" width="105px">Pack Quantity : </td>
				<td width="100px"><input id="packquantity" name="packquantity" type="text" data-ng-model="product.packquantity" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.packquantity.$error.required">Pack Quantity is required / put 0</span>
					    <!-- <span class="error" data-ng-show="productform.packquantity.$error.number">Please enter a valid number!</span>  -->
					</div>
				</td>
				<td style="text-align:right" width="110px">RRPrice : </td>
				<td><input id="rrprice" name="rrprice" type="text" data-ng-model="product.rrprice" size="10px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.rrprice.$error.required">Retail Price is required</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="310px">Pack Weight : </td>
				<td width="100px"><input id="packweight" name="packweight" type="text" data-ng-model="product.packweight" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.packweight.$error.required">Pack Weight is required</span>
					    <!-- <span class="error" data-ng-show="productform.packweight.$error.number">Please enter a valid number!</span>  -->
					</div>
				</td>
				<td style="text-align:right" width="105px">Pack Mass : </td>
				<td width="100px">
					<select name="repeatmass" id="repeatmass" data-ng-model="masslist.selected">
	      				<option data-ng-repeat="mass in masslist.availableOptions" value="{{mass.name}}">{{mass.name}}</option>
	   	 			</select>
				</td>
				<td style="text-align:right" width="110px">GST : </td>
				<td>
					<select name="repeatgst" id="repeatgst" data-ng-model="gstlist.selected">
	      				<option data-ng-repeat="gst in gstlist.availableOptions" value="{{gst.name}}">{{gst.name}}</option>
	   	 			</select>
				
				</td>
			</tr>
			
			<tr>
				<td style="text-align:right" width="310px">Compare Weight : </td>
				<td width="100px"><input id="compareweight" name="compareweight" type="text" data-ng-model="product.compareweight" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.compareweight.$error.required">Compare Weight is required</span>
					    <!--  <span class="error" data-ng-show="productform.compareweight.$error.number">Please enter a valid number!</span> -->
					</div>
				</td>
				<td style="text-align:right" width="105px">Compare Mass : </td>
				<td width="100px">
					<select name="repeatcomparemass" id="repeatcomparemass" data-ng-model="comparemasslist.selected">
	      				<option data-ng-repeat="comparemass in comparemasslist.availableOptions" value="{{comparemass.name}}">{{comparemass.name}}</option>
	   	 			</select>
				</td>
				<td style="text-align:right" width="110px">Checkout Weight : </td>
				<td><input id="checkoutweight" name="checkoutweight" type="text" data-ng-model="product.checkoutweight" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.checkoutweight.$error.required">Checkout Weight is required</span>
					    <!-- <span class="error" data-ng-show="productform.checkoutweight.$error.number">Please enter a valid number!</span>  -->
					</div>
				</td>
				
			</tr>
			<tr>
				<td style="text-align:right" width="310px">Discount : </td>
				<td width="100px"><input id="discount" name="discount" type="text" data-ng-model="product.discount" data-ng-init="discount='0'" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.discount.$error.required">Discount is required or put 0</span>
					    <!-- <span class="error" data-ng-show="productform.discount.$error.number">Please enter a valid number!</span>  -->
					</div>
				</td>
				<td style="text-align:right" width="105px">Inventory Level  : </td>
				<td width="100px"><input id="inventorylevel" name="inventorylevel" type="text" data-ng-model="product.inventorylevel" data-ng-init="inventorylevel='10000'" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.inventorylevel.$error.required">Inventory Level is required or put 350</span>
					    <!-- <span class="error" data-ng-show="productform.inventorylevel.$error.number">Please enter a valid number!</span> -->
					</div>
				</td>
				<td style="text-align:right" width="110px">Stock Level Days : </td>
				<td><input id="stockleveldays" name="stockleveldays" type="text" data-ng-model="product.stockleveldays" data-ng-init="stockleveldays='100'" size="10px" min="0" max="5000" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.stockleveldays.$error.required">Stock Level Days is required or put 100</span>
					    <!-- <span class="error" data-ng-show="productform.stockleveldays.$error.number">Please enter a valid number!</span>  -->
					</div>
				</td>
			</tr>
			<tr>
		        <td style="text-align:right" width="310px">Keep Fresh: </td>
				<td colspan="5"><input id="keepfresh" name="keepfresh" type="text" data-ng-model="product.keepfresh" data-ng-init="keepfresh='0'" size="10px" min="0" max="100" required/>
					<div role="alert">
					    <span class="error" data-ng-show="productform.keepfresh.$error.required">Keep Fresh is required or put 0</span>
					    <!-- <span class="error" data-ng-show="productform.keepfresh.$error.number">Please enter a valid number!</span> -->
					</div>
				</td>
			<tr>
			    <td style="text-align:right" width="310px">Description: </td>
				<td colspan="5" ><textarea id="description" name="description" data-ng-model="product.description" data-ng-init="description='-'" style="width:500px; height:200px; font-family:Arial, MS Sans Serif, Verdana; font-size:12px;" ></textarea></td>
			</tr>
			<tr>
			</tr>
			<tr>
		        <td colspan="6" style="text-align:center">
		        	<button class="button-blue" data-ng-click="addnextsupplier()">NEXT SUPPLIER</button>
		        	<button class="button-blue" data-ng-click="saveNewProduct()" data-ng-disabled="!productform.supplierpackqty.$valid">SAVE & NEXT</button>
		        	<!-- <button class="button-blue" data-ng-click="saveNewProduct()">SAVE & NEXT</button> -->
		        	<button class="button-blue" data-ng-click="cancel()" >CANCEL</button>
		        </td>
	        </tr>
		</table>
		
		<div class="processing" data-ng-show="loading">
            <div class="input-group  pull-right">
                <img class="spinner"  src='<c:url value="/resources/images/loading51.gif" />' />
            </div>
        </div>
        <div id="dialog-success" title="Message">
			<p>	
			</p>
		</div>
		<div id="dialog-error" title="Message">
			<p>	
			</p>
		</div>  
	</form>
</div>
</section><!-- /#content -->
<%@ include file="productfooter.jsp" %>

</body>
