<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<% User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");%>
<% String userType = (String) request.getSession().getAttribute("userType"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dodaj PayPal</title>
	<link rel="stylesheet" type="text/css" href="/generalStyle.css">
	<link rel="stylesheet" type="text/css" href="/registrationError.css" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

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


<form:form action="/payment/addPayPal/${organiser.id}" method="post" modelAttribute="payPalForm">
		
		<div class="container">
			
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6" style="height:500px;">
					<h1 class="text-center" style="margin-bottom:50px;">
						<span class="glyphicon glyphicon-pencil"></span> Popuni formular
					</h1>

					<div class="form-group row">
						<label  class="col-md-3 control-label"><span class="glyphicon glyphicon-user"></span> Email PayPal računa</label>
						<div class="col-md-9">
							<form:input type="text" style="width:250px;" path="payPalEmail" placeholder="Email PayPal" />
						</div>
					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="payPalEmail" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label class="col-md-3"></label>
						<div class="col-md-9">
							<input type="submit" value="Dodaj PayPal" style="width:250px;">
						</div>
					</div>
 
				</div>
				<div class="col-md-3"></div>
			</div>
		</div>
	</form:form>
</body>
</html>