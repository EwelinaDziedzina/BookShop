<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Search for Book</title>
<style><%@include file="/WEB-INF/css/style.css"%></style>
</head>
<body>
<h2>Search for the title or author</h2>
<p>
	Enter the search Text
</p>

<form action="BookServlet?action=search" method="POST">
<p><input type="text" name="search" placeholder="Type here" required>
<select name = "searchType">
  <option value="title">Title</option>
  <option value="author">Author</option>
</select>
 <input type="submit" value="Search"></p>
</form>
</body>
</html>