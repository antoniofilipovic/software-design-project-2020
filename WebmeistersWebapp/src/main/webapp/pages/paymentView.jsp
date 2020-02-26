<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<% User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");%>
<% String userType = (String) request.getSession().getAttribute("userType"); %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	
	<link rel="stylesheet" type="text/css" href="/generalStyle.css">
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



<div class="container">
	
	<div class="row">
		<a class="btn gumbi" href="/payment/${loggedInUser.id}" style="float: right; margin-right: 0px;">
				Povratak na plaćanje</a>
	</div>
	
	<div class="col-md-6 sredina">
	<h2 style="margin-bottom:20px;">Kartica</h2>
	
	<c:choose>
		<c:when test="${hasCreditCard == 'true'   }">
			<label class="control-label">Broj kartice: ${creditCard.cardNumber }</label><br>
			<label class="control-label">Vlasnik: ${creditCard.cardOwner }</label><br>
			<a class="btn gumbi"
				href="/payment/deleteCreditCard/${organiser.id}/${creditCard.id }">Obriši kreditnu karticu</a>

		</c:when>

		<c:otherwise>
			<label class="control-label">Kartica nedostupna.</label><br>
			<a class="btn gumbi" href="/payment/addCreditCard/${organiser.id}">Dodaj kreditnu karticu</a>
		</c:otherwise>
	</c:choose>

	</div>
	
	<div class="col-md-6">
	<h2 style="margin-bottom:20px;">PayPal</h2>
	<c:choose>

		<c:when test="${hasPaypal == 'true'   }">
			<label class="control-label">Mail: ${paypal.payPalEmail}</label><br>
			<a class="btn gumbi" href="/payment/deletePayPal/${organiser.id}/${paypal.id }">Obriši paypal</a>
		</c:when>

		<c:otherwise>
			<label class="control-label">Paypal nedostupan.</label><br>
			<a class="btn gumbi" href="/payment/addPayPal/${organiser.id}">Dodaj paypal</a>
		</c:otherwise>
	</c:choose>

	</div>

</div>

</body>
</html>