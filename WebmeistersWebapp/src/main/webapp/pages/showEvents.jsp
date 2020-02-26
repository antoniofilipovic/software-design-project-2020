<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
	String userType = (String) request.getSession().getAttribute("userType");
%>
<%
	List<EventView> eventi = (List<EventView>) request.getAttribute("eventi");
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>

<title>ZabavaNET</title>
<link rel="stylesheet" type="text/css" href="styleHome.css">
<link rel="stylesheet" type="text/css" href="/generalStyle.css">

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

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



	<h1 style="margin-boottom: 30px;">Moji događaji</h1>

	<div class="container dogadaj">
		<h2 style="margin-bottom:20px;">Odaberite način filtriranja</h2>
		<form:form action="#filter" method="post" modelAttribute="filter" id="filter">
			<div class="col-md-3">
				<label>Prosli događaji</label> <form:checkbox path="prosli" /><br>
				<label>Buduci događaji</label> <form:checkbox path="buduci" /><br>
			</div>
			<div class="col-md-3">
				<label>Trajanje:</label> <br>
				<label>Manje od 48 sati</label> <form:checkbox path="trajanje0do48" /> <br>
				<label>Od 48 sati do 7 dana</label> <form:checkbox path="trajanje48do7" /> <br>
				<label>Više od 7 dana</label> <form:checkbox path="trajanje7doDalje" /> <br>
				
			</div>
			
			<div class="col-md-3">
	            <label>Sortiraj</label><br>
	            <form:select path="sort">
	                <option value="datumSilazno">Datum pocetka / silazno</option>
	                <option value="datumUzlazno">Datum pocetka / uzlazno</option>
	                <option value="aktualni">Aktualni</option>
	                <option value="abecednoSilazno">Abecedno / A->Z</option>
	                <option value="abecednoUzlazno">Abecedno / Z->A</option>
	                <option value="najpopularniji">Najpopularniji</option>
	                <option value="najnepopularniji">Najnepopularniji</option>
	            </form:select>  <input type="submit" value="Sortiraj" onclick="myFunction()">
	            <br><input type="submit" value="Filtriraj" onclick="myFunction()">
            </div>
		</form:form>

    </div>


	<!--   <form:form  action="/showEvents/${id}" method="post" modelAttribute="eventi">  </form:form> -->



	<div class="container">


		<c:forEach items="${eventi}" var="event">
			<div class="row dogadaj">
				<div class="col-md-3 ">
					<div class="verticalHorizontal">
						<img src="/showPicture/${event.thumbnail}" alt="No picture"
							style="height: 150px; width: 250px;">
					</div>
				</div>

				<div class="col-md-6 polja">

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
					
					<div class="form-group row">
						<label class="col-md-6 control-label">Adresa:</label>
						<div class="col-md-4">
							<label class="control-label">${event.address}</label>
						</div>
					</div>
					
					<div class="form-group row">
						<label class="col-md-6 control-label">Kategorija i područje:</label>
						<div class="col-md-4">
							<label class="control-label">${event.category}, ${event.area}</label>
						</div>
					</div>
				</div>

				
				<div class="col-md-3 polja ">
					<c:choose>
						<c:when test="${loggedInUser.id==user.id  && isOrganiser=='true' && isValid=='true'}">
							<a class="btn gumbi" href="/deleteEvent/${event.eventId}">Obriši
								događaj</a><br>
							<a class="btn gumbi" href="/showEvent/${event.eventId}">Više
								o događaju</a>
						</c:when>

						<c:when test="${loggedInUser.id==user.id  && isVisitor}">
							<a class="btn gumbi" href="/showEvent/${event.eventId}">Uredi
								svoj odabir</a>
						</c:when>

						<c:otherwise>
							<a class="btn gumbi" href="/showEvent/${event.eventId}">Više
								o događaju</a>
						</c:otherwise>



					</c:choose>

				</div>
				
				<div class="row" style="margin-top:30px;float:left;">
						<label class="desnoRub">Sigurno dolazim&nbsp;<br>${event.counterDolazim}</label>
						<label class="desnoRub">Možda dolazim&nbsp;<br>${event.counterMozdaDolazim}</label>
						<label>Ne dolazim<br>${event.counterNeDolazim}</label>
				</div>
				
				

			</div>
			
			
			
		</c:forEach>




	</div>

	<script>
		function myFunction() {
            var x = document.URL;
            document.getElementById("filter").action = window.location.href;


        }

        function mySortFunction() {
            var x = document.URL;
            document.getElementById("sort").action = window.location.href;
            document.getElementById("filter").click();

        }
	</script>


</body>
</html>