<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html data-ng-app="productapp" >
<head>
<meta charset="utf-8" />
<title>WOM Content Management System</title>
<link data-require="bootstrap-css@3.1.1" data-semver="3.1.1" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" />

<script data-require="angular.js@1.3.0" data-semver="1.3.0" src="https://code.angularjs.org/1.3.0/angular.js"></script>
<script data-require="jquery@*" data-semver="2.0.3" src="http://code.jquery.com/jquery-2.0.3.min.js"></script>
<script data-require="bootstrap@3.1.1" data-semver="3.1.1" src="//netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

<script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.4.5/angular-animate.js"></script>
<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.13.4.js"></script>
    
<script src= '<c:url value="/resources/js/angular/dirPagination.js" />'></script>
<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/main.css" />'/>


<!-- Styles -->
<link rel="stylesheet" href="components/toastr/toastr.min.css">

<!-- JavaScript Libs -->
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>   
<script src='<c:url value="/resources/toastr/toastr.min.js" />'></script>    
<script src='<c:url value="/resources/aws/aws-sdk.min.js" />'></script> 

<style>
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
</style> 
<script>
	
    var productapp = angular.module('productapp', ['angularUtils.directives.dirPagination', 'ui.bootstrap']);
    
    function ProductController($scope, $http, $modal, $log){
    	
    	$scope.currentPage = 1;
    	$scope.pageSize = 10;
    	    	
        $scope.getProductList = function() {  
        	
        	$http.get('searchProductCode/' + $scope.productcode + '/' + $scope.brand + '/' + $scope.categorycode)
            .success(function(data, status, headers, config) {
                $scope.productlist = data;
            })
            .error(function(data, status, headers, config) {
               // called asynchronously if an error occurs
              // or server returns response with an error status.
            });
        };
        
        $scope.open = function (productcode) {

            var modalInstance = $modal.open({
              templateUrl: 'productModal.html',
              controller: 'ModalInstanceCtrl',
              resolve: {
            	  productcode: function () {
                    return productcode;	
                  }
              }
            });
            
            modalInstance.result.then(function (selectedItem) {
                $scope.selected = selectedItem;
              }, function () {
                $log.info('Modal dismissed at: ' + new Date());
              });
        };
        
        $scope.pageChangeHandler = function(num) {
            console.log('meals page changed to ' + num);
        };
    };
    
    angular.module('productapp').controller('ModalInstanceCtrl', function ($scope, $http, $modalInstance, productcode) {

  	  $scope.productcode = productcode;
  	  $scope.selected = {
  			productcode: $scope.productcode
  	  };

  	  $scope.sizeLimit      = 5292880; // 5MB in Bytes
      $scope.uploadProgress = 0;
      $scope.creds          = {};
    
  	  $scope.upload = function () {
  		
  		AWS.config.update({ accessKeyId: 'AKIAI3WIUI2FDW4JPPJQ', secretAccessKey: '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'});
  	    //AWS.config.region = 'us-east-1';
  	    var bucket = new AWS.S3({ params: { Bucket: 'womimagesprod' } });
  	    
  	    alert('Hi ' + $scope.file.name);
  	    
  	    //alert('Hi');
  	    if($scope.imageA) {
  	        // Perform File Size Check First
  	        
  	        var fileSize = Math.round(parseInt($scope.imageA.size));
  	        if (fileSize > $scope.sizeLimit) {
  	          toastr.error('Sorry, your attachment is too big. <br/> Maximum 5MB file attachment allowed','File Too Large');
  	          return false;
  	        }
  	        // Prepend Unique String To Prevent Overwrites
  	        var uniqueFileName = $scope.pcode + 'A.' + $scope.imageA.type;

  	        var params = { Key: uniqueFileName, ContentType: $scope.imageA.type, Body: $scope.imageA, ServerSideEncryption: 'AES256' };

  	        bucket.putObject(params, function(err, data) {
  	          if(err) {
  	            toastr.error(err.message,err.code);
  	            return false;
  	          }
  	          else {
  	            // Upload Successfully Finished
  	            toastr.success('File Uploaded Successfully', 'Done');

  	            // Reset The Progress Bar
  	            setTimeout(function() {
  	              $scope.uploadProgress = 0;
  	              $scope.$digest();
  	            }, 4000);
  	          }
  	        })
  	        .on('httpUploadProgress',function(progress) {
  	          $scope.uploadProgress = Math.round(progress.loaded / progress.total * 100);
  	          $scope.$digest();
  	        });
  	      }
  	      else {
  	        // No File Selected
  	        toastr.error('Please select a file to upload');
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
    
    productapp.directive('fileInput', ['$parse', function($parse){
        return {
            restrict: 'A',
            link: function(scope, elm, attrs){
                if(typeof(scope.test) == undefined){
                  scope.images = { "files": []}
                }
                if(typeof(scope.test.files) !== undefined){
                  scope.images["files"] =[]
                }
                elm.bind('change', function(){

                    $parse(attrs.fileInput)
                    .assign(scope,elm[0].files)
                    scope.$apply()
                })
            }
        }
    }]);
    
    productapp.controller('ProductController', ProductController);
    productapp.controller('PageController', PageController);
    
</script>
</head>

<body id="mainpage" class="mainpage">
<header id="banner" class="body">
	<h1>Content Management System</h1>
	<c:url var="home" value="/wom/cms/home"/>
	<c:url var="password" value="/wom/cms/password"/>
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
    <div class="row">
    <div class="col-lg-8">
	<div data-ng-controller="ProductController" class="my-controller"> 
	<script type="text/ng-template" id="productModal.html">
			<div class="modal-header">
    			<h3 class="modal-title"> Product Code : {{ selected.productcode }}</h3>
				<input id="pcode" name="pcode" type="text" data-ng-model="selected.productcode"/></td>
    		</div>
   			<div class="modal-body">
       	 	<table>
				<tr>
					<input type="file" file-input="image.files" multiple />
					<li ng-repeat="file in image.files">{{file.name}}</li>
 
					<td><input id="imageA" name="imageA" type="file" /></td>
				</tr>
				<tr>
					<td><input id="imageB" name="imageB" type="file" data-ng-model="imageB"/></td>
				</tr>
				<tr>
					<td><input id="imageC" name="imageC" type="file" data-ng-model="imageC"/></td>
				</tr>
				<tr>
					<td><input id="imageD" name="imageD" type="file" data-ng-model="imageD"/></td>
				</tr>
				<tr>
					<td><input id="imageE" name="imageE" type="file" data-ng-model="imageE"/></td>
				</tr>
			</table>
    		</div>
    		<div class="modal-footer">
   				<button class="btn btn-primary" type="button" data-ng-click="upload()">Upload</button>
    			<button class="btn btn-warning" type="button" data-ng-click="cancel()">Cancel</button>
    		</div>
			
			<div class="panel-body">
            	
            	<!-- Progress Bar -->
           		<div class="progress">
              		<div class="progress-bar" role="progressbar" aria-valuenow="{{ uploadProgress }}" aria-valuemin="0" aria-valuemax="100" style="width: {{ uploadProgress }}%;">
                		{{ uploadProgress == 0 ? '' : uploadProgress + '%' }}
              		</div>
            	</div>            
          	</div>
		</script>
		
		<table>
			<tr>
				<td style="text-align:right" width="280px">Category Code : </td>
				<td><input id="categorycode" name="categorycode" type="text" data-ng-model="categorycode" data-ng-init="categorycode='-'" size="30px"/></td>
	        </tr>
	         <tr>
		        <td style="text-align:right" width="280px">Brand Name : </td>
				<td><input id="brand" name="brand" type="text" data-ng-model="brand" data-ng-init="brand='-'" size="30px"/></td>
			</tr>
			 <tr>
		        <td style="text-align:right" width="280px">Product Code : </td>
				<td><input id="productcode" name="productcode" type="text" data-ng-model="productcode"  data-ng-init="productcode='-'" size="30px"/></td>
			</tr>
			<tr>
				<td><input id="image" name="image" type="file"/></td>
			</tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		        	<button data-ng-click="getProductList()">Search</button>
		            <input  id="save" type="submit" value="Save" class="button-blue"/>
		            <input  id="cancel" type="button" value="Cancel" class="button-blue"/>
		        </td>
	        </tr>
		</table>
		
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
						<th>Product Code</th>
						<th>Brand</th>
						<th>Product Name</th>
						<th>Category Code</th>
						<th>Photo Code</th>
						<th>Pack Weight</th>
						<th>Pack Mass</th>
					</tr>
				</thead>
				<tbody>
					<tr data-dir-paginate="products in productlist | filter:q | itemsPerPage: pageSize" data-current-page="currentPage" data-ng-click="open(products.productCode); $event.preventDefault(); selected.products.productCode = products.productCode">
					   <td>{{ $index + 1 }}</td>
					    <td>{{ products.productCode }}</td>
					    <td>{{ products.brand }}</td>
					    <td>{{ products.productName }}</td>
					    <td>{{ products.categoryCode }}</td>
					    <td>{{ products.photoCode }}</td>
					    <td>{{ products.packWeight }}</td>
					    <td>{{ products.packMass }}</td>
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
