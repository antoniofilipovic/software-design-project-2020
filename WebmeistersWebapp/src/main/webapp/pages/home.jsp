<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.Event"%>
<%@ page import="hr.fer.opp.webmeisters.data.view.EventView"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%
	User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
%>



<%
	List<EventView> events = (List<EventView>) request.getAttribute("events");
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<title>ZabavaNET</title>
<link rel="stylesheet" type="text/css" href="/generalStyle.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>



<meta charset="UTF-8">

<style type="text/css">
body {
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

	<c:if test="${isOrganiser == 'true' && isValid=='false' }">

		<div
			style="border: 3px solid red; height: 50px; width: 700px; margin-left: 400px;">
			<label>Članarina je istekla. Za nastavak korištenja
				aplikacije uplatite članarinu.</label> <a class="btn gumbi btnAlert"
				href="/payment/${loggedInUser.id}">Plati članarinu</a>
		</div>

	</c:if>



	<div class="container" style="top: 0px;">
		<c:if test="${isVisitor == 'true'  }">

			<div class="row">
				<a class="btn gumbi" href="/home/showOrganisers"
					style="float: right; margin-right: 0px;">Popis organizatora</a>
			</div>

		</c:if>


		<c:forEach items="${events}" var="event">
			<div class="row dogadaj">
				<div class="col-md-3 ">
					<div class="verticalHorizontal">
						<img src="/showPicture/${event.thumbnail}" alt="No picture"
							style="height: 150px; width: 250px;">
					</div>
				</div>

				<div class="col-md-7 polja">

					<div class="form-group row">
						<label class="col-md-6 control-label">Naziv događaja:</label>
						<div class="col-md-4">
							<label class="control-label">${event.eventName}</label>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-md-6 control-label">Organizator:</label>
						<div class="col-md-4">
							<label class="control-label">${event.organiserName}</label>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-md-6 control-label">Datum i vrijeme
							početka:</label>
						<div class="col-md-4">
							<label class="control-label">${event.dateStart}</label>
						</div>
					</div>

					<div class="form-group row">
						<label class="col-md-6 control-label">Datum i vrijeme
							kraja:</label>
						<div class="col-md-4">
							<label class="control-label">${event.dateStop}</label>
						</div>
					</div>
				</div>

				<div class="col-md-2 polja ">
					<a class="btn gumbi" href="/showEvent/${event.eventId}"><span
						class="glyphicon glyphicon-plus"></span>Više o događaju</a>

				</div>

			</div>
		</c:forEach>

	</div>

</body>
</html>