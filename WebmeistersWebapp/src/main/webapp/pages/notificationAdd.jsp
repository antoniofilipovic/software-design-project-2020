<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<%
	User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
%>
<%
	String userType = (String) request.getSession().getAttribute("userType");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Dodaj postave notifikacije</title>
	
	<link rel="stylesheet" type="text/css" href="/generalStyle.css">
	<link rel="stylesheet" href="/registrationError.css" type="text/css">
	
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
	
	<style type="text/css">
	body {
		background-image: url(/resources/background.jpg);
		background-position: center center;
		background-repeat: no-repeat;
		background-attachment: fixed;
		background-size: cover;
		background-color: #464646;
	}
	</style>
</head>

<body>

	<ul>
		<li><a href="/home">Naslovnica</a></li>

		<%
			if (loggedInUser == null) {
		%>
		<li><a href="/registerAsVisitor">Registriraj se kao
				Posjetitelj</a></li>
		<li><a href="/registerAsOrganiser">Registriraj se kao
				Organizator</a></li>
		<li style="float: right;"><a href="/login">Prijavi se</a></li>
		<%
			}
		%>

		<%
			if (loggedInUser != null) {
		%>
		<li style="float: right;"><a href="/logout" style="float: right">Odjavi
				se</a></li>
		<li style="float: right;"><a
			href="/manageAccount/${loggedInUser.userType }/${loggedInUser.id}"
			style="float: right">${loggedInUser.fullName }</a></li>
		<%
			}
		%>
	</ul>


	<form:form action="/notification/add/${visitor.id}" method="post"
		modelAttribute="notificationForm">
		<div class="container">
			<label>Odaberi kategoriju iz koje želiš dobivati obavijesti</label><br>
			<c:forEach items="${categories}" var="category">
				<form:checkbox path="categories" 
					value="${category.name }" />${category.name }&nbsp;
			</c:forEach>

			<br>
			
			<div style="margin-top:10px;">
				<form:errors path="categories" cssClass="error" />
			</div>
			

			<br> <label style="margin-top: 30px;">Odaberi područje
				iz kojeg želiš dobivati obavijesti</label><br>
			<c:forEach items="${areas}" var="area">
				<form:checkbox path="areas" 
					value="${area.name }" />${area.name }&nbsp;
			</c:forEach>
			<br>
			
			
			<div style="margin-top:10px;">
				<form:errors path="areas" cssClass="error" />
			</div>
			


			<input style="margin-top: 50px;" type="submit"
				value="Postavi obavijesti">

		</div>
	</form:form>




</body>
</html>