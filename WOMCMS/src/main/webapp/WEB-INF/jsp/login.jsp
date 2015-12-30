<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
<head><title>World on9 Mall</title></head>
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/main.css" />'/>
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/input.css" />'/>
	<link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/button.css" />'/>

<body class="body">
	<header id="banner" class="body">
 
	</header><!-- /#banner -->
	<section id="pcontent" class="body" >
		<div style="background-color:#D1D0CE">
		<c:url var="loginUrl" value="submitlogin" />
		<form:form id="loginform" modelAttribute="LoginForm" action="${loginUrl}" method="post">
		<table border="0">
			<tr>
				<td colspan="2" style="text-align:center"><h1>${message}</h1></td>
			</tr>
			<tr>
				<td style="text-align:right" width="280px">Staff Code : </td>
				<td><input id="userid" name="userid" type="text" size="30px"/></td>
	        </tr>
	        <tr>
		        <td style="text-align:right" width="280px">Password : </td>
				<td><input id="password" name="password" type="password" size="30px"/></td>
			</tr>
			<tr>
		        <td colspan="2" style="text-align:center">
		            <input  type="submit" value="Login" class="button-blue"/>
		            <input  type="button" value="Cancel" class="button-blue"/>
		        </td>
	        </tr>
		</table>
		</form:form>
		</div>
	</section>
</body>
</html>
