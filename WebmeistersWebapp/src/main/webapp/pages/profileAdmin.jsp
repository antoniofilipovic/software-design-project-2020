<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>

<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
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

<script type="text/javascript">
    


 	function writing(isWriting) {
 	 	console.log(isWriting);
 	 	if(isWriting=="true"){// moze editirat pa je hasReview true a moze i pisat ga prvi put
 	 		
 	 		document.getElementById("subscriptionFormWriting").style.display = "block";
 	 		document.getElementById("subscriptionFormFinished").style.display = "none";
 	 		
 	 		
 	 	 	}
 	 	
 	 	else {//kad ne pise review a nema ga jos i kad nije visitor
 	 		document.getElementById("subscriptionFormWriting").style.display = "none";
 	 		document.getElementById("subscriptionFormFinished").style.display = "block";
 	 	 	}
		
	}

 	
    </script>




</head>


<body style="background-image: url(/resources/background.jpg)" onLoad="writing('${isWriting}')">
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
					<label class="col-md-4 control-label">Ime:</label>
					<div class="col-md-6">
						<label class="control-label">${name}</label>
					</div>
				</div>

				<div class="form-group row">
					<label class="col-md-4 control-label">Email:</label>
					<div class="col-md-6">
						<label class="control-label">${email}</label>
					</div>
				</div>

			</div>
		</div>






		<div class="row" style="float:left; margin-top:30px; margin-right:400px;">
			<a class="btn gumbi" href="/admin/showUsers">Pregled korisnika</a>
			<button type="button" class="btn" onclick="writing('true')">Promijeni članarinu</button>
		</div>

		<div class="row" id="subscriptionFormWriting">
			<form:form action="/admin/setSubscription/${loggedInUser.id }"
				method="post" modelAttribute="subscriptionForm">


				<div class="row">
					<div class="col-md-3"></div>
					<div class="col-md-6">
						<h1 class="text-center" style="margin-bottom: 5px;">
							<span class="glyphicon glyphicon-pencil"></span> Postavi članarinu
						</h1>

						<div class="form-group row">
							<label for="username" class="col-md-3 control-label"><span
								class="glyphicon glyphicon-bookmark"></span> Članarina</label>
							<div class="col-md-6">
								<form:input type="text" style="width:250px;" path="subscription"
									placeholder="Iznos članarine" />
							</div>
							<div class="col-md-6">
								<br>
								<form:errors path="subscription" cssClass="error" />
							</div>
						</div>


						<input type="submit" value="Spremi promjene">
					</div>
					<div class="col-md-3"></div>
				</div>
			</form:form>
		</div>


	
	</div>

</body>
</html>