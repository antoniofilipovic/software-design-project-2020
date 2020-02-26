<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<% User loggedInUser = (User) request.getSession().getAttribute("loggedInUser");%>
<% String userType = (String) request.getSession().getAttribute("userType"); %>


<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Plati članarinu</title>

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

	<label class="control-label">Članarina trenutno iznosi: ${subscription} kuna</label><br>
	<a class="btn gumbi" style="margin-bottom:30px;" href="/payment/view/${loggedInUser.id}">Pregled načina plaćanja</a>  
	
	<br>
	<label>Račun je aktivan do: &nbsp;${organiser.accountValidUntil} </label>
	
		<c:if test="${paymentTry == 'true'   }">
			<div>${paymentSuccess}</div>

		</c:if>
	



	<c:choose>
		<c:when test="${noPayments == 'true'   }">
			<div>
			<label class="control-label">Trenutno nema dostupnih načina plaćanja. Morate ih dodati.</label><br>
			<a class="btn gumbi" href="/payment/view/${organiser.id}">Dodaj način plaćanja</a>
			</div>

		</c:when>


		<c:otherwise>


			<form action="/payment/pay/${organiser.id}" method="post">
				<p>
					<label class="control-label">Odaberi način plaćanja članarine:</label>
					<form:select name="paymentType" path="payments">
						<form:options items="${payments}" />
					</form:select>
					
				</p>


				<p>
					<input type="submit" name="submit" value="Plati članarinu" />
				</p>
			</form>

		</c:otherwise>
	</c:choose>


</div>








</body>
</html>