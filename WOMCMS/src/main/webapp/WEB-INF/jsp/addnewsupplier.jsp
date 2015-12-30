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
    
    var supplierapp = angular.module('supplierapp',  ['ngSanitize', 'ui.select']);
    supplierapp.controller('SupplierController', function ($scope, $http){
    	
    		$scope.supplier = {
     			suppliername : '-',
              	address : '-',
              	phone : 0,
              	fax  : 0,
              	website  : '-',
              	contactperson  : '-',
              	contactnumber  : 0,
              	email  : '-',
              	gstid  : 0,
         	};
    	 
   	    	$scope.cancel = function () {
   	            window.location.reload(); 
   	        };
   	        
   	        $scope.addnextsupplier = function(){
           		
	           	$scope.supplier = {
         			suppliername : '-',
                  	address : '-',
                  	phone : 0,
                  	fax  : 0,
                  	website  : '-',
                  	contactperson  : '-',
                  	contactnumber  : 0,
                  	email  : '-',
                  	gstid  : 0,
	         	  };
		     }
   	     
   	     	$scope.saveNewSupplier = function() {
 	   		    
   	     		$scope.loading = true;
   	            $http.post('submitNewSupplier/' +  $scope.supplier.suppliername + '/'
   	            		+ $scope.supplier.address + '/' + $scope.supplier.phone + '/'
   	            		+ $scope.supplier.fax + '/' + $scope.supplier.website + '/'
   	            		+ $scope.supplier.email + '/' + $scope.supplier.contactperson + '/' + $scope.supplier.gstid + '/' 
   	            		+ $scope.supplier.contactnumber + '/' )
   	            		
   	            .success(function(data, status, headers, config) {
   	            	jdlog("#dialog-success").dialog("open").html("Successfully saved the supplier");
   	            	$scope.loading = false;
   	            	$scope.addnextsupplier();
   	            })
   	            
   	            .error(function(data, status, headers, config) {
   	            	jdlog("#dialog-error").dialog("open").html("Error!");
   	            });
 	    		
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
	<form name="supplierform" id="supplierform" data-ng-controller="SupplierController" class="my-controller"> 
		<table>
			<tr>
				<td colspan = "2" style="text-align:center"> <h3>Add New Supplier</h3> </td>
			</tr>
			<tr>
		        <td style="text-align:right" width="210px">Supplier Name: </td>
				<td width="450px"><input id="suppliername" name="suppliername" type="text" data-ng-model="supplier.suppliername" data-ng-init="suppliername='-'" size="100px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.suppliername.$error.required">Supplier Name is required</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="210px">Address : </td>
				<td width="450px"><input id="address" name="address" type="text" data-ng-model="supplier.address" data-ng-init="address='-'" size="100px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.address.$error.required">Address is required</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="210px">Phone : </td>
				<td width="450px"><input id="phone" name="phone" type="number" data-ng-model="supplier.phone" data-ng-init="phonet='0'" size="30px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.phone.$error.required">Phone is required</span>
					    <span class="error" data-ng-show="supplierform.phone.$error.number">Please enter a valid number!</span>
					</div>
				</td>
			</tr>
			<tr>	
				<td style="text-align:right" width="210px">Fax : </td>
			<td width="450px"><input id="fax" name="fax" type="number" data-ng-model="supplier.fax" data-ng-init="fax='0'" size="30px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.fax.$error.required">Fax is required</span>
					    <span class="error" data-ng-show="supplierform.fax.$error.number">Please enter a valid amount!</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="210px">Website : </td>
				<td width="450px"><input id="website" name="website" type="text" data-ng-model="supplier.website" data-ng-init="website='-'" size="100px"/>
				</td>
			</tr>
	        <tr>
	        	<td style="text-align:right" width="210px">Contact Person : </td>
				<td width="450px"><input id="contactperson" name="contactperson" type="text" data-ng-model="supplier.contactperson" size="100px" required />
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.contactperson.$error.required">Contact Person is required</span>
					</div>
				</td>
	        </tr>
	        <tr>
		        <td style="text-align:right" width="210px">Contact Number : </td>
				<td width="450px"><input id="contactnumber" name="contactnumber" type="number" data-ng-model="supplier.contactnumber" size="30px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.contactnumber.$error.required">Contact Number is required</span>
					    <span class="error" data-ng-show="supplierform.contactnumber.$error.number">Please enter a valid number!</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="210px">Email : </td>
				<td width="450px"><input id="email" name="email" type="text" data-ng-model="supplier.email" size="100px" required/>
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.email.$error.required">Email is required</span>
					</div>
				</td>
			</tr>
			<tr>
				<td style="text-align:right" width="210px">GST ID : </td>
				<td width="450px"><input id="gstid" name="gstid" type="number" data-ng-model="supplier.gstid" data-ng-init="gstid='0'" size="100px"/>
					<div role="alert">
					    <span class="error" data-ng-show="supplierform.gstid.$error.number">Please enter a valid number!</span>
					</div>
				</td>
			<tr>
		        <td colspan="2" style="text-align:center">
		        	<button class="button-blue" data-ng-click="saveNewSupplier()" data-ng-disabled="!supplierform.suppliername.$valid">SAVE & NEXT</button>
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
