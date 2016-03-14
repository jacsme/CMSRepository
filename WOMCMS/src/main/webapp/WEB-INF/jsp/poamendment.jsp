<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html data-ng-app="poamendmentapp">
<head>
<meta charset="utf-8" />
<title>WOM Content Management System</title>
<head>
	<link data-require="bootstrap-css@3.1.1" data-semver="3.1.1" rel="stylesheet" href='<c:url value="/resources/css/bootstrap.min.css" />' />
	<link rel="stylesheet" href='<c:url value="/resources/css/xeditable.css" />' />
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/main.css" />'/>
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/button.css" />'/>
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
	
      var poamendmentapp = angular.module('poamendmentapp', ['angularUtils.directives.dirPagination', 'ui.bootstrap', 'xeditable' ]);
      function POAmendmentController($scope, $http, $modal, $log){
      $scope.open = function (poamendmentselected) {
    		 console.log('Selected' + poamendmentselected);
   		     var modalInstance = $modal.open({
             templateUrl: 'poamendmentModal.html',
             controller: 'ModalInstanceCtrl',
             resolve: {
            	 poamendmentselected: function () {
                    return poamendmentselected;	
                  }
              }
           	});
           
           	modalInstance.result.then(function (selectedItem) {
             	}, function () {
               	$log.info('Modal dismissed at: ' + new Date());
               	$scope.getPOAmendmentList();
             });
        };
      	
        $http.get('getSupplierList/').success(function (data) {
 		   $scope.suppliers = data;
 	    });
        
    	$scope.currentPage = 1;
    	$scope.pageSize = 10;
    	
    	$scope.getPOAmendmentList = function() {  
    		$scope.loading = true;
    		
    		var paramsuppliercode;
    		console.log("Select " + $scope.supplier);
    		if(typeof $scope.supplier === 'undefined'){ 
    			paramsuppliercode = '-'
    		}else{
    			paramsuppliercode= $scope.supplier.selected;
    		}
    		
    		if($scope.productcode === ''){ $scope.productcode = '-'}
    		if($scope.pocode === ''){ $scope.pocode = '-'}
    		
        	$http.get('searchPOAmendmentList/' + encodeURIComponent($scope.productcode) + '/' + encodeURIComponent($scope.pocode) + '/' + encodeURIComponent(paramsuppliercode))
            .success(function(data, status, headers, config) {
            	console.log("search >> " + data);
            	$scope.poamendmentlist = data;
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
    };
    angular.module('poamendmentapp').controller('ModalInstanceCtrl', function ($scope, $http, $modalInstance, poamendmentselected) {
 		
    	angular.forEach(poamendmentselected, function(value, key){
   			if(key === 'supplierCode'){ $scope.suppliercode = value;}
   			if(key === 'poCode'){ $scope.pocode = value;}
   			if(key === 'productCode'){ $scope.productcode = value; }
   			if(key === 'productName'){ $scope.productname = value; }
   			if(key === 'packQuantity'){ $scope.pack = value;}
   			if(key === 'packUnit'){ $scope.packunit = value; }
   			if(key === 'packTotalUnit'){ $scope.totalunit = value; }
   			if(key === 'packPrice'){ $scope.packprice = value;}
   			if(key === 'gst'){ $scope.gst = value; }
   			if(key === 'amount'){ $scope.amount = value;	}
   			if(key === 'totalAmount'){ $scope.totalamount = value;}
   			if(key === 'unitQuantity'){ $scope.unitquantity = value;}
   			if(key === 'invPackQuantity'){ $scope.invpack = value;	}
   			if(key === 'invTotalUnit'){ $scope.invtotalunit = value;}
        });

    	$scope.saveEditedPOAmendment = function(data) {
    		console.log("Start");
     		if(data.pack === '0') {
     			alert('Invalid Pack');
     		}else if (data.packnit === '0') {
     			alert('Invalid Pack Unit');
     		}else if (data.totalunit === '0'){
    			alert('Invalid Total Unit');
     		}else if (data.packprice === '0'){
				alert('Invalid Pack Price');
     		}else if (data.gst === '0'){
     			alert('Invalid GST');
     		}else if (data.amount === '0'){
     			alert('Invalid amount');
     		}else if (data.totalamount === '0' || data.totalamount === ''){
     			alert('Invalid Total Amount');
     		}else if (data.unitquantity === '0' ||data.unitquantity === ''){
     			alert('Invalid unit quantity');
     		}else if (data.invpackquantity === '0' || data.invpackquantity === ''){
    			alert('Invalid inventory pack');
     		}else if (data.invtotalunit === '0' || data.invpackquantity === ''){
    			alert('Invalid inventory Total Unit');
     		}else{
     			console.log("Start2" + $scope.suppliercode + ' ' + $scope.pocode + ' ' + $scope.productcode + ' ' + data.pack + ' ' +data.packunit 
     					+ ' ' +data.totalunit + ' ' +data.packprice + ' ' +data.gst + ' ' +data.amount + ' ' +data.totalamount
     					+ ' ' +data.unitquantity + ' ' +data.unitquantity + ' ' +data.invpack + ' ' +data.invtotalunit);
  	    		$scope.loading2 = true;
  	    		var formdata = {
  	    			"supplierCode" :$scope.suppliercode,
  	    	   		"poCode" : $scope.pocode,
  	    	   		"productCode" : $scope.productcode, 
  	    	   		"packQuantity" :data.pack,
  	    	   		"packUnit" :data.packunit,
  	    	   		"packTotalUnit" :data.totalunit,
  	    	   		"packPrice" :data.packprice,
  	    	   		"gst" :data.gst,
  	    	   		"amount" :data.amount,
  	    	   		"totalAmount" :data.totalamount,
  	    	   		"unitQuantity" :data.unitquantity,
  	    	   		"invPackQuantity" :data.invpack,
  	    	   		"invTotalUnit" :data.invtotalunit
	        	};
  	    		
		        $http.post("saveEditedPOAmendment", formdata)
	  	            .success(function(data, status, headers, config) {
	  	            	jdlog("#dialog-success").dialog("open").html("Successfully saved the PO");
	  	            	$scope.loading2 = false;
	  	            	$modalInstance.dismiss('cancel');
	  	            })
	  	            
	  	            .error(function(data, status, headers, config) {
	  	            	jdlog("#dialog-error").dialog("open").html("Error!");
	  	            	$scope.loading2 = false;
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
    poamendmentapp.controller('POAmendmentController', POAmendmentController);
    poamendmentapp.controller('PageController', PageController);
    
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
	<div data-ng-controller="POAmendmentController" class="my-controller"> 
		<script type="text/ng-template" id="poamendmentModal.html">
			<div class="modal-header">
    			<h3 class="modal-title"> PO Amendment </h3>
    		</div>
   			<div class="modal-body">
			<table>
				<tr>
					<td style="text-align:right" width="170px">Supplier Code : </td>
					<td>{{suppliercode}}</td>
		        	<td style="text-align:right" width="170px">PO Code : </td>
					<td>{{pocode}}</td>
					<td style="text-align:right" width="170px">Product Code : </td>
					<td>{{productcode}}</td>
				</tr>
       	 		<tr>
					<td style="text-align:right" width="170px">Product Name : </td>
					<td colspan = "5">{{productname}}</td>
				</tr>
				<tr>
					<td style="text-align:right" width="170px">Pack</td>
					<td><span data-editable-text="pack" data-e-name="pack" data-e-form="rowform" data-e-style="width: 77px">{{pack}}</span></td>
					<td style="text-align:right" width="170px">Pack Unit </td>
					<td><span data-editable-text="packunit" data-e-name="packunit" data-e-form="rowform" data-e-style="width: 77px">{{packunit}}</span></td>
					<td style="text-align:right" width="170px">Total Unit </td>
					<td><span data-editable-text="totalunit" data-e-name="totalunit" data-e-form="rowform" data-e-style="width: 77px">{{totalunit}}</span></td>
				</tr>
				<tr>
					<td style="text-align:right" width="170px">Pack price</td>
					<td><span data-editable-text="packprice" data-e-name="packprice" data-e-form="rowform" data-e-style="width: 77px">{{packprice}}</span></td>
					<td style="text-align:right" width="170px">Amount(Exc GST) </td>
					<td><span data-editable-text="amount" data-e-name="amount" data-e-form="rowform" data-e-style="width: 77px">{{amount}}</span></td>
					<td style="text-align:right" width="170px">GST </td>
					<td><span data-editable-text="gst" data-e-name="gst" data-e-form="rowform" data-e-style="width: 77px">{{gst}}</span></td>
				</tr>
				<tr>
					<td style="text-align:right" width="170px">Total Amount(Inc GST)</td>
					<td colspan = "5"><span data-editable-text="totalamount" data-e-name="totalamount" data-e-form="rowform" data-e-style="width: 77px">{{totalamount}}</span></td>
				</tr>
				<tr>
					<td style="text-align:right" width="170px">Unit Quantity</td>
					<td><span data-editable-text="unitquantity" data-e-name="unitquantity" data-e-form="rowform" data-e-style="width: 77px">{{unitquantity}}</span></td>
					<td style="text-align:right" width="170px">Inv Pack </td>
					<td><span data-editable-text="invpack" data-e-name="invpack" data-e-form="rowform" data-e-style="width: 77px">{{invpack}}</span></td>
					<td style="text-align:right" width="170px">Inv Total Unit </td>
					<td><span data-editable-text="invtotalunit" data-e-name="invtotalunit" data-e-form="rowform" data-e-style="width: 77px">{{invtotalunit}}</span></td>
				</tr>
				<tr>
 					<td colspan = "6" style="white-space: nowrap; text-align: center">
					<!-- form -->
						<form data-editable-form name="rowform" data-onbeforesave="saveEditedPOAmendment($data)" data-ng-show="rowform.$visible" class="form-buttons form-inline">
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
			<table>
    		</div>
			<div class="processing2" data-ng-show="loading2">
            	<div class="input-group  pull-right">
                	<img class="spinner"  src='<c:url value="/resources/images/loading51.gif" />' />
            	</div>
        	</div>
			<div class="modal-footer">
    			<button class="btn btn-warning" type="button" data-ng-click="cancel()">Close</button>
    		</div>
		</script>
		<table>
			<tr>
		        <td style="text-align:right" width="390px">PO Code : </td>
				<td><input id="pocode" name="pocode" type="text" data-ng-model="pocode"  data-ng-init="pocode='-'" size="70px"/></td>
			</tr>
		 	<tr>
		        <td style="text-align:right" width="390px">Product Code : </td>
				<td><input id="productcode" name="productcode" type="text" data-ng-model="productcode"  data-ng-init="productcode='-'" size="70px"/></td>
			</tr>
	        <tr>
		        <td style="text-align:right" width="390px">Supplier Name : </td>
				<td>
					<select name="repeatsupplier" id="repeatsupplier" data-ng-model="supplier.selected" style="height:30px; width:400px">
	      				<option data-ng-repeat="supplier in suppliers" value="{{supplier.supplierCode}}">{{supplier.supplierName}}</option>
	   	 			</select>
				</td>
			</tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		        	<button data-ng-click="getPOAmendmentList()">Search</button>
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
						<th>SupplierCode</th>
						<th>PO Code</th>
						<th>Product Code</th>
						<th>Product Name</th>
						<th>Pack</th>
						<th>PackUnit</th>
						<th>Total Unit</th>
						<th>PackPrice</th>
						<th>GST</th>
						<th>Amount</th>
						<th>TotalAmount</th>
						<th>Unit Qty</th>
						<th>Inv Pack</th>
						<th>Inv Total Unit</th>
					</tr>
				</thead>
				<tbody>
					<tr data-dir-paginate="poamendment in poamendmentlist | filter:q | itemsPerPage: pageSize" data-current-page="currentPage" data-ng-click="open(poamendment); $event.preventDefault(); ">
					   	<td>{{ $index + 1 }}</td>
					   	<td>{{ poamendment.supplierCode }}</td>
					    <td>{{ poamendment.poCode }}</td>
					    <td>{{ poamendment.productCode }}</td>
					    <td>{{ poamendment.productName }}</td>
    					<td>{{ poamendment.packQuantity }}</td>
					    <td>{{ poamendment.packUnit }}</td>
						<td>{{ poamendment.packTotalUnit }}</td>
					    <td>{{ poamendment.packPrice }}</td>
					    <td>{{ poamendment.gst }}</td>
					    <td>{{ poamendment.amount}}</td>
					    <td>{{ poamendment.totalAmount}}</td>
					    <td>{{ poamendment.unitQuantity}}</td>
					    <td>{{ poamendment.invPackQuantity}}</td>
					    <td>{{ poamendment.invTotalUnit}}</td>
					 </tr>
				</tbody>
			</table>
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
