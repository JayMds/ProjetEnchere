<%@page import="fr.eni.encheres.bo.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
   <% Article article = (Article) request.getAttribute("article"); %>
<%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<body>

<p><%= article.toString() %></p>

</body>
<%@include file="/WEB-INF/jspf/script.jspf" %>
</html>