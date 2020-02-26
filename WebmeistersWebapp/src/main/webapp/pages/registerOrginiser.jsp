<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<% User loggedInUser = (User)request.getSession().getAttribute("loggedInUser"); %>
<% String userType = (String) request.getSession().getAttribute("userType"); %>


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
<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="styleLogin.css">
<link rel="stylesheet" href="registrationError.css" type="text/css">



<meta charset="UTF-8">
<link rel="stylesheet" href="registrationError.css" type="text/css">
<title>Registriraj se kao organizator</title>

	<style type="text/css">
		body{
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
	
	
	
	
	<form:form action="registerAsOrganiser" method="post" modelAttribute="organiserForm">
		<div class="container">
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6" style="height:620px;">
					<h1 class="text-center">
						<span class="glyphicon glyphicon-pencil"></span> Popuni formular <br> da postaneš član
					</h1>

					<div class="form-group row">
						<label for="name" class="col-md-3 label"><span class="glyphicon glyphicon-user"></span> Vaše ime</label>
						<div class="col-md-9">
							<form:input type="text" path="name" placeholder="Vaše ime" />
						</div>
						<div class="col-md-9">
							<br>
							<form:errors path="name" cssClass="error" />
						</div>
					</div>
					
					<div class="form-group row">
						<label for="email" class="label col-md-3 control-label"><span class="glyphicon glyphicon-envelope"></span> Email</label>
						<div class="col-md-9">
							<form:input type="email" path="email" placeholder="Email" />
						</div>

						<div class="col-md-9">
							<br>
							<form:errors path="email" cssClass="error" />
						</div>
					</div>
					

					<div class="form-group row">
						<label for="realAddress" class="col-md-3 label"><span class="	glyphicon glyphicon-home"></span> Fizička<br>adresa</label>
						<div class="col-md-9">
							<form:input type="text" path="realAddress" placeholder="Fizička adresa" />
						</div>

						<div class="col-md-9">
							<br>
							<form:errors path="realAddress" cssClass="error" />
						</div>
					</div>
					
					
					<div class="form-group row">
						<label for=webAddress class="label col-md-3 control-label"><span class="glyphicon glyphicon-link"></span> Poveznica na<br>stranicu</label>
						<div class="col-md-9">
							<form:input type="text" path="webAddress" placeholder="Webadresa" />
						</div>

						<div class="col-md-9">
							<br>
							<form:errors path="webAddress" cssClass="error" />
						</div>
					</div>

					

					<div class="form-group row">
						<label for="password" class="label col-md-3 control-label"><span class="glyphicon glyphicon-lock"></span> Lozinka</label>
						<div class="col-md-9">
							<form:input type="password" path="password" placeholder="Lozinka" />
						</div>

						<div class="col-md-9">
							<br>
							<form:errors path="password" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label for="repassword" class="label col-md-3 control-label"><span class="glyphicon glyphicon-lock"></span> Ponovi <br> lozinku</label>
						<div class="col-md-9">
							<form:input type="password" path="repassword" placeholder="Ponovi lozinku" />
						</div>

						<div class="col-md-9">
							<br>
							<form:errors path="repassword" cssClass="error" />
						</div>
					</div>

					<input type="submit" value="Postani član">
				</div>
				<div class="col-md-3"></div>
			</div>
		</div>
	</form:form>
	

</body>