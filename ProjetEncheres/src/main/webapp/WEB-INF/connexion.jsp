<%@page import="fr.eni.encheres.servlet.CookieUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jspf/head.jspf" %>
<link rel="stylesheet" href="css/styles-login.css">
 </head>
<body>
    
<%@include file="/WEB-INF/jspf/affichageErreur.jspf" %>
<%
Cookie[] cookies = request.getCookies();
if (CookieUtils.readCookie("message", cookies) != null) {
        	%>
<p style="color:green; text-align: center;"  ><%= CookieUtils.readCookie("message", cookies) %></p>
        	
 <% } %>

			<div style="margin-top: 50px;" class="formConnexion">

	<a class="logo" href="<%= request.getContextPath() %>">
        <img src="asset/logo/Logo_eni_bleuFonce.png">
    </a>


<% if( request.getAttribute("erreurLogin") != null){ %>
	<p style="color:red; text-align:center"><%= request.getAttribute("erreurLogin") %></p>
<%} %>

    <form action="<%= request.getContextPath() %>/connexion" method="post" >
        <input type="email" name="email" class="form-control roundRadius" id="exampleDropdownFormEmail1" placeholder="email@example.com">
        <input type="password" name="password" class="form-control roundRadius" id="exampleDropdownFormPassword1" placeholder="Password">

       
        <div class="form-check">
          <input type="checkbox" class="form-check-input" id="dropdownCheck">
          <label class="form-check-label" for="dropdownCheck">
            Se rappeler de moi
          </label>
        </div>
        <button type="submit" class="montserrat600 bg-blue btn roundRadius SB30 H5">Se connecter</button>
      </form>

      <div class="divider"></div>
      <a class="link-item" href="<%= request.getContextPath()%>/inscription">Nouveau ici? inscris-toi ici</a>
      <a  class="link-item" href="<%= request.getContextPath()%>/forgotpassword">Mot de passe oublié?</a>

</div>


</body>
</html>