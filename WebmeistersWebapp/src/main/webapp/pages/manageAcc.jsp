<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" session="true"%>
<%@ page import="hr.fer.opp.webmeisters.data.model.User" %>
<% User loggedInUser = (User)request.getSession().getAttribute("loggedInUser"); %>
<% User updated = (User)request.getAttribute("isUserUpdated"); %>

<!DOCTYPE html>
<html>
<head>
    <title>Manage your account</title>
    <link rel="stylesheet" type="text/css" href="/classicStyle.css">
</head>

<body style="background-image:url(/resources/background.jpg)">

<ul>
    <li><a href="home">Home</a> </li>
     <% if(loggedInUser != null) { %>
    <li><a href="/registerAsUser">Register As Visitor</a></li>
    <li><a href="/registerAsOrganiser">Register As Organiser</a></li>
   
    <ul style="float:right; ">
        <li style="float: right;"><a href="/logout" style="float: right">Odjavi se</a></li>
        <li><a href="/manageAccount" style="float: right"><%= loggedInUser.getEmail()%></a></li>
    </ul>
    <% } %>
</ul>
<% if(updated!=null){ %>
<div class="updateMessage">
    <h1>Your account has been succsesfully updated!</h1>
</div>
<% } %>

<div align="center">
    <div class="blok">
        <img src="/resources/edit-user.png" alt="editUser" style="width:20%">
        <h1>Edit information about your profile</h1>
        <p class="description">You can manage information about you or your company in any given time</p>
        <p><button class="blokButton" onclick="window.location.href = '/editInfo'">Edit</button></p>
    </div>
    <div class="blok">
        <img src="/resources/add-event.jpg" alt="addEvent" style="width:20%">
        <h1>Add event</h1>
        <p class="description">You can add new events for your company</p>
        <p><button class="blokButton" onclick="window.location.href = '/addEvent'">Add event</button></p>
    </div>
    <div class="blok">
        <img src="/resources/logout.png" alt="logout" style="width:20%">
        <h1>Logout</h1>
        <p class="description">Logout from your current logged in account</p>
        <p><button class="blokButton" onclick="window.location.href = '/logout'">Logout</button></p>
    </div>
    </div>

</body>
</html>

