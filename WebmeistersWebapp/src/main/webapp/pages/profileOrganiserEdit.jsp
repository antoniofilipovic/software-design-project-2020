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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

	<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/generalStyle.css">
	<link rel="stylesheet" type="text/css" href="/registrationError.css" type="text/css">
	<title>Moj profil</title>
	
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

<form:form  action="/editInfo/organiser/${id}" method="post" modelAttribute="editedOrganiser">
	<div class="container">
		<div class="row">
		
			<div class="col-md-2" align="center">
				<img src="/resources/user.png" alt="user" style="width:150px;height:150px;">
			</div>
 
			
			<div class="col-md-6 polja">
				<div class="form-group row">
	                <label for="inputKey" class="col-md-4 control-label">Korisničko ime</label>
	                <div class="col-md-6">
	                    <form:input type="text" class="form-control" path="name" id="inputKey" placeholder="" />
	                </div>
            	</div>
            	
            	<div class="form-group row">
					<div style="margin-left:130px;">
						<form:errors path="name" cssClass="error" />
					</div>
				</div>


	            <div class="form-group row">
	                <label for="inputKey" class="col-md-4 control-label">email</label>
	                <div class="col-md-6">
	                    <form:input type="text" class="form-control" path="email" id="inputKey" placeholder="" />
	                </div>
	            </div>
	            
	            <div class="form-group row">
					<div style="margin-left:130px;">
						<form:errors path="email" cssClass="error" />
					</div>
				</div>

				<div class="form-group row">
	                <label for="inputKey" class="col-md-4 control-label">Adresa</label>
	                <div class="col-md-6">
	                    <form:input type="text" class="form-control" path="realAddress" id="inputSt" placeholder="" />
	                </div>
	            </div>
	            
	            <div class="form-group row">
					<div style="margin-left:130px;">
						<form:errors path="realAddress" cssClass="error" />
					</div>
				</div>

	            <div class="form-group row">
	                <label for="inputKey" class="col-md-4 control-label">web adresa</label>
	                <div class="col-md-6">
	                    <form:input type="text" class="form-control" path="webAddress" id="inputSt" placeholder="" />
	                </div>
	                <div class="col-md-9">
							<br>
							<form:errors path="webAddress" cssClass="error" />
						</div>
	            </div>
	            
	            <div class="form-group row">
					<div style="margin-left:130px;">
						<form:errors path="webAddress" cssClass="error" />
					</div>
				</div>
	            
	            <div class="form-group row">
	                <label for="inputKey" class="col-md-4 control-label">Stara lozinka</label>
	                <div class="col-md-6">
	                    <form:input type="password" class="form-control" path="oldPassword" id="inputSt" placeholder="" />
	                </div>
	            </div>
	            
	            <div class="form-group row">
					<div style="margin-left:130px;">
						<form:errors path="oldPassword" cssClass="error" />
					</div>
				</div>
	            
	            <div class="form-group row">
	                <label for="inputKey" class="col-md-4 control-label">Nova lozinka</label>
	                <div class="col-md-6">
	                    <form:input type="password" class="form-control" path="password" id="inputSt" placeholder="" />
	                </div>
	            </div>
	            
	            <div class="form-group row">
					<div style="margin-left:130px;">
						<form:errors path="password" cssClass="error" />
					</div>
				</div>
	            
	            <div class="form-group row">
	                <label for="inputKey" class="col-md-4 control-label">Ponovi novu lozinku</label>
	                <div class="col-md-6">
	                    <form:input type="password" class="form-control" path="repassword" id="inputSt" placeholder="" />
	                </div>
	            </div>
	            
	            <div class="form-group row">
					<div style="margin-left:130px;">
						<form:errors path="repassword" cssClass="error" />
					</div>
				</div>
	            
	            <div class="form-group row">
	            	<div class="col-md-6 offset-md-4">
						<input type="submit" value="Spremi promjene">
					</div>
				</div>
				
			</div>
			
			

			
		</div>


	</div>
</form:form>

</body>
</html>