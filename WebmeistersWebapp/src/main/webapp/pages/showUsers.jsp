<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<% User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");%>
<% String userType = (String) request.getSession().getAttribute("userType"); %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Pregled korisnika</title>

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
		<c:forEach items="${users}" var="user">
			<div class="row dogadaj">
				
				
				<div class="col-md-6 polja">
				
					<div class="form-group row">
		                <label  class="col-md-6 control-label">Ime korisnika:</label>
				        <div class="col-md-4">
				        	<label  class="control-label">${user.fullName}</label>
				        </div>
	            	</div>
	            	
	            	<div class="form-group row">
		                <label  class="col-md-6 control-label">Vrsta korisnika:</label>
				        <div class="col-md-4">
				        	<label  class="control-label">${user.userType}</label>
				        </div>
	            	</div>
	            	
	            	<div class="form-group row">
		                <label  class="col-md-6 control-label">Email</label>
				        <div class="col-md-4">
				        	<label  class="control-label">${user.email}</label>
				        </div>
	            	</div>
	            	
	            	
				</div>
				<div class="col-md-2 polja ">
					<a class="btn gumbi" href="/manageAccount/${user.userType}/${user.id}">Više o korisniku</a>
					<a class="btn gumbi" href="/deleteUser/${user.id}">Obriši korisnika</a>
				
				</div>
				
			</div>
		</c:forEach>
		
	</div>

</body>
</html>