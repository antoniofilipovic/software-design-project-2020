<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<%
	String error = (String) request.getAttribute("error");
%>
<%
	User loggedInUser = (User) request.getAttribute("loggedInUser");
%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>


	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet"href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
		
	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" href="registrationError.css" type="text/css">
	<link rel="stylesheet" type="text/css" href="/styleLogin.css">
	
	<title>Prijava</title>
	
	
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



<form:form action="login" method="post" modelAttribute="loginForm">

		
	<div class="container">
		<div class="row">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<h1 class="text-center"><span class="glyphicon glyphicon-user"></span> Login</h1>
				
				<div class="form-group row">
					<label for="username" class="col-md-3 label"><span class="glyphicon glyphicon-user"></span> Email</label>
					<div class="col-md-9">
						<form:input type="email" name="name" path="email" placeholder="Email" />
					</div>
				</div>
				
				<div class="form-group row">
					<div class="offset-md-3 col-md-9">
						<form:errors path="email" cssClass="error" />
					</div>
				</div>

				<div class="form-group row">
					<label for="Password" class="label col-md-3 control-label"><span class="glyphicon glyphicon-lock"></span> Lozinka</label>
					<div class="col-md-9">
						<input type="password" name="Password" path="password" placeholder="Password" />
					</div>
				</div>
				
				<div class="form-group row">
					<div class="offset-md-3 col-md-9">
						<form:errors path="password" cssClass="error" />
					</div>
				</div>
				
				<input type="submit" value="Prijavi se">
			</div>
			</div>
			</div>

</form:form>



</body>
</html>