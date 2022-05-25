<%@page import="fr.eni.encheres.bo.Utilisateur"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
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
</body>
</html>