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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<!DOCTYPE html>
<html>
<head>

<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>


<link rel="stylesheet" type="text/css" href="css/bootstrap.css">
<link rel="stylesheet" type="text/css" href="/generalStyle.css">
<link rel="stylesheet" type="text/css" href="/registrationError.css" type="text/css">
<title>Dodaj dogadaj</title>






<script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.15.1/moment.min.js"></script>
<link rel="stylesheet" type="text/css"	href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/css/bootstrap-datetimepicker.min.css">
<script type="text/javascript"	src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datetimepicker/4.17.43/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"	src="/Resources/plugins/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript"	src="/Resources/plugins/bootstrap-datetimepicker/js/locales/bootstrap-datetimepicker.hr.js"
	charset="UTF-8"></script>
<script type="text/javascript">
	$(function() {
		$('#datetimepicker1').datetimepicker({
			format : 'DD-MM-YYYY HH:mm',
		});
	});
</script>

<script type="text/javascript">
	$(function() {
		$('#datetimepicker2').datetimepicker({
			format : 'DD-MM-YYYY HH:mm',
		});
	});
	
</script>

<style>
body {
	background-image: url(/resources/background.jpg);
	background-position: center center;
	background-repeat: no-repeat;
	background-attachment: fixed;
	background-size: cover;
	background-color: #464646;
}

ul {
	background-color: silver;
}

.vrhLista {
	list-style-type: none;
	margin: 0;
	padding: 0;
	overflow: hidden;
	background-color: #333333;
}
</style>


</head>


<body>
	<ul class="vrhLista">
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


	<form:form action="/addEvent/${id}" method="post"
		modelAttribute="eventForm" enctype="multipart/form-data">


		<div class="container">
			<div class="row">

				<div class="col-md-6 polja">
					<div class="form-group row">
						<label for="inputKey" class="col-md-4 control-label">Naziv
							događaja</label>
						<div class="col-md-6">
							<form:input type="text" class="form-control" path="name" placeholder="Naziv događaj" />
						</div>
					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="name" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputKey" class="col-md-4 control-label">Adresa</label>
						<div class="col-md-6">
							<form:input type="text" class="form-control" path="address"
								placeholder="Adresa" />
						</div>
					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="address" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputKey" class="col-md-4 control-label">Početak</label>
						<div class="col-md-6">
							<div class="form-group">
								<div class='input-group date' id='datetimepicker1'>
									<form:input type='text' class="form-control" path="start"
										name="start" />

									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>

					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="start" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputKey" class="col-md-4 control-label">Zavrsetak</label>
						<div class="col-md-6">
							<div class="form-group">
								<div class='input-group date' id='datetimepicker2'>
									<form:input type='text' class="form-control" path="stop"
										name="stop" />

									<span class="input-group-addon"> <span
										class="glyphicon glyphicon-calendar"></span>
									</span>
								</div>
							</div>
						</div>
					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="stop" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputKey" class="col-md-4 control-label">Kategorija</label>
						<div  class="col-md-6">
							<select id="GFG" name="category">
								<option>Izaberi kategoriju</option>
								<c:forEach items="${categories}" var="category">
									<option value="${category.name}">${category.name}</option>
								</c:forEach>

							</select>

						</div>
					</div>
					
					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="category" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputKey" class="col-md-4 control-label">Područje</label>
						<div class="col-md-6">
							<select id="GFG" name="area">
								<option>Izaberi područje</option>
								<c:forEach items="${areas}" var="area">
									<option value="${area.name}">${area.name}</option>
								</c:forEach>

							</select>

						</div>
					</div>

					<div class="form-group row">
						<div style="margin-left:130px;">
							<form:errors path="area" cssClass="error" />
						</div>
					</div>

					<div class="form-group row">
						<label for="inputKey" class="col-md-4 control-label">Dodaj
							slike i/ili video datoteke</label>

						<div class="col-md-6">
							<input type="file" name="files" accept="image/*,video/*" multiple>
						</div>
					</div>

				</div>

				<div class="col-md-3 polja">
					<label>Dodaj opis događaja</label>
					<form:textarea rows="10" cols="50"
						placeholder="Unesite opis događaja" path="description" />
					<br>
					<form:errors path="description" cssClass="error" />
				</div>

			</div>

			<div class="row">
				<div class="col-md-6 offset-md-4">
					<input type="submit" value="Dodaj događaj">
				</div>
			</div>



		</div>
	</form:form>



</body>
