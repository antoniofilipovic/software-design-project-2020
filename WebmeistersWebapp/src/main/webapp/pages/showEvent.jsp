<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.Event"%>
<%@ page import="hr.fer.opp.webmeisters.data.view.EventView"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.InterestType"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%
	User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");
%>
<%
	String userType = (String) request.getSession().getAttribute("userType");
%>
<%
	EventView event = (EventView) request.getAttribute("event");
%>
<%
	List<InterestType> interestTypes = (List<InterestType>) request.getAttribute("interestTypes");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<title>ZabavaNET</title>
<link rel="stylesheet" type="text/css" href="/generalStyle.css">
<link rel="stylesheet" type="text/css" href="/registrationError.css" type="text/css">
<!--  <link rel="stylesheet" type="text/css" href="/css/bootstrap.css">   -->
<link rel="stylesheet" type="text/css" href="/styleGallery.css">
<link rel="stylesheet" type="text/css" href="/lightbox.min.css">
<script src="/lightbox-plus-jquery.min.js"></script>

<meta name="viewport" content="width=device-width, initial-scale=1">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
    
     function sendInteresChange(eventId,userId,interestId) {
        
		var xmlhttp;

		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {
			// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp.onreadystatechange = function() {
				console.log()
				var link="/deleteInterest/"+userId+"/"+eventId;
				var interestType = xmlhttp.responseText;

				var html = "<label>Moj interes: </label>"+ " " +
				"<label>"+interestType+"</label>"+
				"</div><a class=\"btn gumbi\" href=\""+link+"\">Obriši interes</a>";
		      
				console.log(html);
		        document.getElementById("interes").innerHTML = html;
			
		}
		console.log(eventId+" "+userId+" "+interestId);
		xmlhttp.open("POST", "/saveInterest/"+eventId+"/"+userId+"/"+interestId+"?dummy=" + Math.random(), true);
		console.log("šaljem zahtjev");
		xmlhttp.send();
	} 


 	function writingReview(isVisitor,hasReview,isWriting) {
 	 	console.log(isVisitor,hasReview,isWriting);
 	 	if(isVisitor=="true" && isWriting=="true"){// moze editirat pa je hasReview true a moze i pisat ga prvi put
 	 		console.log(isVisitor,hasReview,isWriting);
 	 		document.getElementById("reviewFormWriting").style.display = "block";
 	 		document.getElementById("finished").style.display = "none";
 	 		document.getElementById("kreniPisat").style.display = "none";
 	 		
 	 	 	}
 	 	else if(isVisitor=="true" && isWriting=="false" && hasReview=="true"){//ovdje je bitno da ima review jer kad ga nema onda ne prikazujemo nista od ovih
 	 		document.getElementById("reviewFormWriting").style.display = "none";
 	 		document.getElementById("finished").style.display = "block";
 	 		document.getElementById("kreniPisat").style.display = "none";
 	 		
 	 	 	}
 	 	else {//kad ne pise review a nema ga jos i kad nije visitor
 	 		document.getElementById("reviewFormWriting").style.display = "none";
 	 		document.getElementById("finished").style.display = "none";
 	 	 	}
		
	}

 	
    </script>

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

<body
	onLoad="writingReview('${isVisitor}','${hasReview}','${isWriting}')">
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




	<h1 style="margin-bottom: 10px;">${event.eventName}</h1>

	<!--   <form:form  action="/showEvents/${id}" method="post" modelAttribute="eventi">  </form:form> -->



	<div class="container">



		<div class="row">

			<div class="col-md-6 ">

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
					<label class="col-md-6 control-label">Adresa:</label>
					<div class="col-md-4">
						<label class="control-label">${event.address}</label>
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

			<div class="col-md-4">
				<div class="form-group row">
					<label class="control-label" style="margin-bottom: 5px;">Opis
						događaja:<br> ${event.description}
					</label>
				</div>

			</div>

		</div>

		<div class="row dogadaj">
			<div class="gallery">
				<c:forEach items="${event.pictures}" var="path">
					<a href="/showPicture/${path}" data-lightbox="mygallery"><img
						src="/showPicture/${path}" alt="No picture"></a>
				</c:forEach>
			</div>
			<br style="clear: both;">
			<div class="row" style="float: left;">
				<c:forEach items="${event.videos}" var="path">
					<video width="320" height="220" controls>
						<source src="../resources/static/videos/${path}" type="video/mp4">
					</video>
					

					<!-- <a href="/showVideo/${path}" data-lightbox="mygallery"><img src="/showVideo/${path}" alt="No picture"></a> -->
				</c:forEach>
			</div>

		</div>

		<div class="row dogadaj">
			<c:choose>
				<c:when test="${isVisitor == 'true' && hasInterest=='false' }">
					<div id="interes" class="form-group row">
						<label class="control-label">Moj interes: nije izrazen</label>
					</div>
				</c:when>

				<c:when test="${isVisitor == 'true' && hasInterest=='true' }">
					<div id="interes" class="form-group row">

						<label class="control-label">Moj interes:
							${myInterest.interest.name }.</label>
						<c:if test="${hasStarted=='false' }">
							<a class="btn gumbi"
								href="/deleteInterest/${myInterest.visitorEventId.visitor.id}/${myInterest.visitorEventId.event.id}">Obriši
								interes</a>
						</c:if>
						<c:if test="${hasStarted=='true' }">
							Interes više nije moguće promijeniti.
						</c:if>

					</div>
				</c:when>

				<c:otherwise>
					<div>
						<label class="control-label">Interes mogu izraziti samo
							prijavljeni Posjetitelji</label>
					</div>
				</c:otherwise>



			</c:choose>


			<%
				if (loggedInUser != null && loggedInUser.getUserType().equals("visitor")) {
			%>
			<c:if test="${hasStarted=='false' }">
				<div class="btn-group" style="margin-bottom: 5px;">

					<c:forEach items="${interestTypes}" var="type">
						<button type="button" class="btn gumbi"
							onclick="sendInteresChange(${event.eventId},<%=loggedInUser.getId() %>,${type.id})">
							${type.name}</button>
					</c:forEach>

				</div>
			</c:if>
			<%
				}
			%>


		</div>

		<div class="row">


			<!--Ovo je forma koja se aktivira kad ide editirat recenziju ili pise prvi puta-->
			<!--  <div id="reviewForm" style="display: ${buttonReview};">-->

			<div id="reviewFormWriting">
				<form:form action="/addReview/${loggedInUser.id}/${event.eventId}"
					method="post" modelAttribute="reviewForm">


					<div class="row">
						<div class="col-md-3"></div>
						<div class="col-md-6">
							<h1 class="text-center" style="margin-bottom: 5px;">
								<span class="glyphicon glyphicon-pencil"></span> Popuni
								recenziju
							</h1>

							<div class="form-group row">
								<label for="username" class="col-md-3 control-label"><span
									class="glyphicon glyphicon-bookmark"></span> Naslov</label>
								<div class="col-md-6">
									<form:input type="text" style="width:250px;" path="title"
										placeholder="Naslov" />
								</div>
							</div>
							
							<div class="form-group row">
								<div style="margin-left:90px;">
									<form:errors path="title" cssClass="error" />
								</div>
							</div>

							<div class="form-group row">
								<label for="username" class="col-md-3 control-label"><span
									class="glyphicon glyphicon-list-alt"></span> Recenzija</label>

								<div class="col-md-6">
									<form:textarea path="text" style="width:250px;"
										placeholder="..." />
								</div>
							</div>
							
							<div class="form-group row">
								<div style="margin-left:90px;">
									<form:errors path="text" cssClass="error" />
								</div>
							</div>
							
							<input type="submit" value="Spremi promjene">
						</div>
						<div class="col-md-3"></div>
					</div>
				</form:form>
			</div>

		</div>

		<div class="row dogadaj">

			<!--Ovo je review od korisnika koji je prijavljen -->
			<div id="finished">
				<div class="row">
					<h3 style="margin-top: 5px;">Moja recenzija</h3>
				</div>
				<div class="row">

					<div class="col-md-6">
						<div class="form-group row">
							<label class="col-md-4 control-label">Naslov:</label>
							<div class="col-md-6">
								<label class="control-label">${myReview.title}</label>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-md-4 control-label">Recenzija:</label>
							<div class="col-md-6">
								<label class="control-label">${myReview.text}</label>
							</div>
						</div>

						<div class="form-group row">
							<label class="col-md-4 control-label">Napisano:</label>
							<div class="col-md-6">
								<label class="control-label">${myReview.dateCreatedAt}</label>
							</div>
						</div>
					</div>
					<div class="col-md-4">
						<button type="button" class="btn"
							onclick="writingReview('true','true','true')">Uredi
							recenziju</button>
						<br> <a class="btn gumbi"
							href="/deleteReview/${myReview.visitor.id}/${myReview.event.id}">Obriši
							recenziju</a>
					</div>
				</div>


			</div>
			<c:if test="${hasFinished=='true' }">
				<c:choose>
					<c:when
						test="${isVisitor=='true' && canComment=='true'  && hasReview=='false'}">
						
						<div id="kreniPisat">
							<label>Kako bi napisali recenziju pritisnite gumb</label>
							<button style="margin-bottom: 10px;" type="button" class="btn"
								onclick="writingReview('true','false','true')">Napiši
								svoju recenziju</button>
						</div>
					</c:when>
					<c:when test="${isVisitor=='true' && canComment=='false'  }">
				</c:when>

					<c:when test="${isVisitor == 'false'  }">
						<div>
							<label>Recenzirati mogu samo prijavljeni Posjetitelji</label>
						</div>

					</c:when>
					<c:otherwise>
						<div>
							<label>Napomena: moguće je napisati samo jednu recenziju</label>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>

			<c:if test="${hasFinished=='false' }">
				<c:choose>
					<c:when test="${isVisitor == 'false'  }">
						<div>
							<label>Recenzirati mogu samo prijavljeni Posjetitelji do
								48 sati nakon završetka događaja.</label>
						</div>

					</c:when>
					<c:otherwise>
						<div>
							<label>Recenzirati je moguće samo unutar 48 sati od
								završetka događaja.</label>
						</div>
					</c:otherwise>
				</c:choose>
			</c:if>


			<!-- <c:choose>
				<c:when test="${isVisitor == 'true' && hasReview == 'false'   }">
					<div id="kreniPisat">
						<label>Kako bi napisali recenziju pritisnite gumb</label>
						<button style="margin-bottom: 10px;" type="button" class="btn"
							onclick="writingReview('true','false','true')">Napiši
							svoju recenziju</button>
					</div>

				</c:when>

				<c:when test="${isVisitor == 'false'  }">
					<div>
						<label>Recenzirati mogu samo prijavljeni Posjetitelji</label>
					</div>

				</c:when>


				<c:otherwise>
					<div>
						<label>Napomena: moguće je napisati samo jednu recenziju</label>
					</div>
				</c:otherwise>
			</c:choose>
		</div>-->


			<!--${isVisitor} ${hasReview} ${isWriting}-->






			<div class="row dogadaj">

				<div class="form-group row">
					<h3 style="float: left; margin-left: 50px;">Ostale recenzije</h3>
				</div>

				<c:forEach items="${reviews}" var="review">

					<div class="row recenzija">
						<div class="col-md-2 desno">
							<label class="control-label">${review.title}</label><br> <label
								class="control-label">${review.visitor.fullName}</label><br>
							<label class="control-label">${review.dateCreatedAt}</label>
						</div>

						<div class="col-md-6">
							<label class="control-label tekst">${review.text}</label>
						</div>
					</div>

				</c:forEach>
			</div>

		</div>
</body>
</html>