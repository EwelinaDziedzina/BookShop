<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Shopping Cart</title>
<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
	<h2>View Shopping Cart</h2>

	<table border = "1">
		<tr>
			<th>Id</th>
			<th>Title</th>
			<th>Author</th>
			<th>Desctription</th>
			<th>Price</th>
			<th>Quantity</th>
			<th>Add/Remove Item</th>
			<th>Total</th>
		</tr>
		<c:forEach var="item" items="${cart}">
			<tr>
			<c:set var="isFound" value="true" scope="page"/> 
				<td>${item.key.id}</td>
				<td>${item.key.title}</td>
				<td>${item.key.author}</td>
				<td>${item.key.description}</td>
				<td>&euro; <fmt:formatNumber type="number" maxFractionDigits="2" value="${item.key.price}"/></td>
				<td>${item.value}</td>
				<td><a href = "BookServlet?action=addItemToCart&bookId=${item.key.id}">Add</a><br>
				<a href = "BookServlet?action=removeItemFromCart&bookId=${item.key.id}">Remove</a></td>
				<td>${item.key.price * item.value}</td>
				<c:set var="total" value="${total + item.key.price * item.value }"/>
			</tr>
	</c:forEach>
	</table>
	<p>
	Total cost : &euro; ${total}
	</p>
	<p>		
	<a href="BookServlet?action=viewAll">View All Books</a> <br><br>
	<a href="BookServlet?action=clearCart">Clear Shopping Cart</a> 
	</p>
</body>
</html>