<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<%
	User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>



	<link rel="stylesheet" type="text/css" href="url(/css/bootstrap.css)">
	<link rel="stylesheet" type="text/css" href="/generalStyle.css">
	<title>Moj profil</title>







</head>


<body style="background-image: url(/resources/background.jpg)">
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
		<div class="row">

			<div class="col-md-2" align="center">
				<img src="/resources/user.png" alt="user"
					style="width: 150px; height: 150px;">
			</div>


			<div class="col-md-6 polja">
				<div class="form-group row">
					<label class="col-md-4 control-label">Korisničko ime:</label>
					<div class="col-md-6">
						<label class="control-label">${visitor.fullName}</label>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-md-4 control-label">Nadimak:</label>
					<div class="col-md-6">
						<label class="control-label">${visitor.nick}</label>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-md-4 control-label">Email:</label>
					<div class="col-md-6">
						<label class="control-label">${visitor.email}</label>
					</div>
				</div>


			</div>
		</div>





		<div class="row" style="float:left;margin-top:30px">

			<c:if test="${loggedInUser.id==visitor.id }">
				<a class="btn gumbi"
					href="/editInfo/visitor/<%=loggedInUser.getId()%>">Uredi
					podatke</a>
				<a class="btn gumbi" href="/notification/<%=loggedInUser.getId()%>">Postavke obavijesti</a>
			</c:if>
				<a class="btn gumbi" href="/showEvents/interest/${visitor.id}">Moji interesi</a> 
				<a class="btn gumbi" href="/showEvents/review/${visitor.id}">Moje recenzije</a>




	</div>



</body>
</html>