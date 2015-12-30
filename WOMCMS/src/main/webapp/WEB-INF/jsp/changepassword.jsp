<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<title>WOM Content Management System</title>
<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/main.css" />'/>
<link rel="stylesheet" href='<c:url value="/resources/css/jquery-ui.css" />' />
	
	<script charset="utf-8" src='<c:url value="/resources/js/jquery-1.9.1.js" />'></script>
	<script charset="utf-8" src='<c:url value="/resources/js/jquery-ui.js" />'></script>
	
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
					jdlog("#userId").val("");
					jdlog("#oldpassword").val("");
					jdlog("#newpassword").val("");
					jdlog("#confirmpassword").val("");
					location.reload();
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

<script charset="utf-8" src='<c:url value="/resources/js/jquery-1.10.2.min.js" />'></script>
<script charset="utf-8" src='<c:url value="/resources/js/json2.js" />'></script>
<script charset="utf-8" src='<c:url value="/resources/js/jquery.min.js"/>'></script>
<script charset="utf-8" src='<c:url value="/resources/js/jquery.h5validate.js" />'></script>
<script type="text/javascript">
	jval = jQuery.noConflict( true );
	
	jval(document).ready(function(){
		console.log( "3rd loaded jQuery version ($): " + jval.fn.jquery + "<br>" );
		
	    jval('#save').click(function(){
	    	userId = jval("#userId").val();
			oldpassword = jval("#oldpassword").val();
			newpassword = jval("#newpassword").val();
			confirmpassword = jval("#confirmpassword").val();
			if (newpassword == confirmpassword) {
				jval.ajax({
					type: "POST",
					url: "savepassword",
					data: "userId="+ userId +"&newpassword="+ newpassword,
					success: function() {
						jdlog("#dialog-success").dialog("open").html("Successfully changed your password");
			        },
				  	error: function(){
				  		jdlog("#dialog-error").dialog("open").html("Error!");
				  	}
				});
	    	}else{
	    		jdlog("#dialog-error").dialog("open").html("New Password and Confirm Password is not match");
	    		//alert("New Password and Confirm Password is not match");
	    	}
		});
	});
	
</script>
</head>

<body id="mainpage" class="mainpage">
<header id="banner" class="body">
	<h1>Content Management System</h1>
	<c:url var="home" value="/wom/cms/home"/>
	<c:url var="password" value="/wom/cms/password"/>
	<c:url var="uploading" value="/wom/cms/uploadimages"/>
	<c:set var="savepassword" value="/wom/cms/savepassword"/>
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

<section id="cpcontent" class="body">
	<form:form  id="changePasswordForm" modelAttribute="ChangePasswordForm" action="${savepassword}" method="post">
		<table border="0">
			<tr>
				<td colspan="2" style="text-align:center"><h1>Change Password</h1></td>
			</tr>
			<tr>
				<td style="text-align:right" width="400px">Staff Id : </td>
				<td><input id="userId" name="userId" type="text" size="70px"/></td>
	        </tr>
	         <tr>
		        <td style="text-align:right" width="400px">New Password : </td>
				<td><input id="newpassword" name="newpassword" type="password" size="70px"/></td>
			</tr>
	        <tr>
		        <td style="text-align:right" width="400px">Confirm Password : </td>
				<td><input id="confirmpassword" name="confirmpassword" type="password" size="70px"/></td>
			</tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		            <input  id="save" type="button" value="Save" class="button-blue"/>
		            <input  id="cancel" type="button" value="Cancel" class="button-blue"/>
		        </td>
	        </tr>
	        <tr>
				<td colspan="2" style="text-align:center"><h1>${message}</h1></td>
			</tr>
		</table>
		<div id="dialog-success" title="Message">
			<p>	
			</p>
		</div>
		<div id="dialog-error" title="Message">
			<p>	
			</p>
		</div>  
	</form:form>
</section><!-- /#content -->

<%@ include file="footer.jsp" %>

</body>
</html>