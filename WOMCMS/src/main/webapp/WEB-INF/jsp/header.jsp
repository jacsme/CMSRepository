<header id="banner" class="body">
	<c:set var="user" value="${user}" />
	<h1><a href="#">Hotel Room <strong>Welcome ${user.firstName}</strong></a></h1>
 
	<nav><ul>
		<c:url var="customer" value="/hr/hom/custrec"/>
		<li class="active"><a href="#">home page</a></li>
		<li><a href="${customer}">Customer Records</a></li>
 
		<li><a href="#">Room Reservation</a></li>
		<li><a href="#">Maintenance</a></li>
	</ul></nav>
 
</header><!-- /#banner -->