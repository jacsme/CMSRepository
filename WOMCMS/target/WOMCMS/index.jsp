<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
	<head>
	<title>WOM Content Management System</title>
	<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">
	<meta http-equiv=Content-Type content="text/html; charset=iso-8859-1">
	<link href='<c:url value="/resources/css/mainstyle.css" />' rel="stylesheet" type="text/css">		
	<script language="JavaScript" src= '<c:url value="/resources/js/xp_progress.js" />'></script>
	<script>
		function onSubmit(){
			document.getElementById("customerForm").submit();
		}
	</script>
	</head>
	<body onload="onSubmit();">
		<form id="customerForm" action="wom/cms/login" method="post">
			<table align="center" width="100%">
					<tr align="center">
						<td align="center" class="Loading">
							<script language="JavaScript">
								var bar1= createBar(300,15,'white',1,'black','blue',85,7,3,"");
							</script>
							<br>
							Loading...	
						</td>
					</tr>
			</table>
		</form>
	
	</body>
</html>
