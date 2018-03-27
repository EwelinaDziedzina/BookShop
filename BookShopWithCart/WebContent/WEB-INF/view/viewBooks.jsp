<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View All Books</title>
<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
	<h2>View All Books</h2>
	
	<c:if test="${cart != null}">
	<a href="BookServlet?action=viewCart">View Shopping Cart</a>
	</c:if>
	<br><br>
	
	<table border = "1">
		<tr>
			<th>Id</th>
			<th>Title</th>
			<th>Author</th>
			<th>Desctription</th>
			<th>Price</th>
			<th>Update</th>
			<th>Delete</th>
			<th>Buy</th>
		</tr>
		<c:set var="isFound" value="false" scope="page"/> 
		<c:forEach var="book" items = "${listOfBooks}" varStatus="status">
			<tr>
			<c:set var="isFound" value="true" scope="page"/> 
				<td>${book.id}</td>
				<td>${book.title}</td>
				<td>${book.author}</td>
				<td>${book.description}</td>
				<td>&euro; <fmt:formatNumber type="number" maxFractionDigits="2" value="${book.price}"/></td>
				<td><a href="BookServlet?action=showUpdateForm&bookId=${book.id}">Update</a></td>
				<td><a href="BookServlet?action=delete&bookId=${book.id}">Delete</a></td>
				<td><a href="BookServlet?action=addToCart&bookId=${book.id}">Buy</a></td>
				<!--  <td><a href="BookServlet?action=delete&bookId=${status.index}">Delete</a></td> -->
			</tr>
	</c:forEach>
	</table>
	<c:if test="${isFound == 'false'}">
				<h2>Book ${searchType} not found, please search again...</h2>
			</c:if>
	<p>		
	<a href="BookServlet?action=showInsertForm">Insert New Book</a> <br>
	<a href="BookServlet?action=showSearchForm">Search for a Book</a>
	</p>
</body>
</html>