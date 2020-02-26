<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>Insert title here</title>

<link rel="stylesheet" type="text/css" href="/generalStyle.css">
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

	<div class="container">
		<c:choose>
			<c:when test="${hasNotification == 'true'   }">
				<label class="control-label">Dobivat ćete obavijesti za
					sljedeće kategorije: </label>
				<c:forEach items=" ${categories}" var="category">
					<label>${category}&nbsp;</label>
				</c:forEach>

				<br>
				<label class="control-label">Dobivat ćete obavijesti za
					sljedeća područja: </label>
				<c:forEach items=" ${areas}" var="area">
				<label>${area}&nbsp;</label>
					
				</c:forEach>

				<br>
				<a class="btn gumbi"
					href="/notification/delete/${visitor.id}/${notification.id }">Obriši
					postavke obavijesti</a>
			</c:when>

			<c:otherwise>
				<label class="control-label">Postavke obavijesti nedostupne.</label>
				<br>
				<a class="btn gumbi" href="/notification/add/${visitor.id}">Dodaj
					postavke obavijesti</a>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>