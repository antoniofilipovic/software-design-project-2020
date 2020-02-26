<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<% User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");%>
<% String userType = (String) request.getSession().getAttribute("userType"); %>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">

function onlyNumberKey(evt) { 
    
    // Only ASCII charactar in that range allowed 
    var ASCIICode = (evt.which) ? evt.which : evt.keyCode 
    if (ASCIICode > 31 && (ASCIICode < 48 || ASCIICode > 57)) 
        return false; 
    var cardLength=document.getElementById("card").value.length;
    document.getElementById("remainingCharacters").innerHTML = 16-Number(cardLength);
    return true; 
} 

</script>
	<meta charset="UTF-8">
	<title>Dodaj karticu</title>

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


<form:form action="/payment/addCreditCard/${organiser.id}" method="post" modelAttribute="creditCardForm">
		
		<div class="container">
			
			<div class="row">
				<div class="col-md-3"></div>
				<div class="col-md-6" style="height:500px;">
					<h1 class="text-center" style="margin-bottom:50px;">
						<span class="glyphicon glyphicon-pencil"></span> Popuni formular
					</h1>

					<div class="form-group row">
						<label for="username" class="col-md-3 control-label"><span class="glyphicon glyphicon-user"></span> Puno ime vlasnika kartice</label>
						<div class="col-md-9">
							<form:input type="text" style="width:250px;" path="cardOwner"  placeholder="Vaše ime" />
						</div>
					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="cardOwner" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label  class="col-md-3 control-label"><span class="glyphicon glyphicon-edit"></span> Broj kartice</label>

						<div class="col-md-9">

							<form:input id="card" style="width:250px;" type="text" path="cardNumber"   onkeyup="return onlyNumberKey(event)"  />

						</div>
						<div id="remainingCharacters"></div>
					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="cardNumber" cssClass="error" />
						</div>
					</div>
				
					<div class="form-group row">
						<label class="col-md-3"></label>
						<div class="col-md-9">
							<input type="submit" value="Dodaj karticu" style="width:250px;">
						</div>
					</div>
					
				</div>
				<div class="col-md-3"></div>
			</div>
		</div>
	</form:form>
</body>
</html>