<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Profil utilisateur</title>
</head>
<body>
<% Utilisateur user = (Utilisateur) request.getAttribute("user");  %>

<p><%= user.toString() %></p>



<%
	Utilisateur connectedUser = (Utilisateur) request.getSession(false).getAttribute("connectedUser");
	if(connectedUser == user ){
%>

<a href="#">Modifier</a>

<% } %>
</body>
</html>