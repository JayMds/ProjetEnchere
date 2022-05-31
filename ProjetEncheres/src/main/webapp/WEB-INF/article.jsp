<%@page import="fr.eni.encheres.bo.Enchere"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
   <% Article article = (Article) request.getAttribute("article"); %>
    
<%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<body>




<div class="containerArticle">

	<img src="asset/imageProduit/<%=article.getNoArticle()%>.jpeg">
	
	<div class="containerInfo">
		<h2><%=article.getNomArticle() %></h2>
		<span>Catégorie: <%= article.getCatagorie().getLibelle() %>  </span>
		
		<p><%= article.getDescription() %></p>
		
		<%if(article.getEnchere().getEncherisseur().getPseudo()!=null){ %>
		<p>Meilleur offre : <%= article.getEnchere().getMontant() %> <span>par 	<a href="<%= request.getContextPath() %>/utilisateur?id=<%= article.getEnchere().getEncherisseur().getNoUtilisateur()%>"> <%=article.getEnchere().getEncherisseur().getPseudo() %> </a></span> </p>
		
		
		
		<%} %>
		<p> Mise à prix :<%= article.getPrixInitial() %></p>
		
	
		<p>Fin de l'enchère <%= article.getSTRDateFinEnchere() %></p>
		
		<p>Retrait: //TODO faire managerRetrait</p>
		
		<p>Vendu par:  <a href="<%= request.getContextPath() %>/utilisateur?id=<%= article.getNoVendeur()%>"> <%=article.getVendeur().getPseudo() %> </a></p>
		
		
		<%
		
		if(user != null ){
		%>
			<p>Vos crédit disponible : <%= user.getCredit() %></p>
			<form action="<%=request.getContextPath()%>/article?idarticle=<%=article.getNoArticle() %>" method="post">
				<input type="number" name="offre" max="<%= user.getCredit()%>" min="<%= article.getEnchere().getMontant() %>" value="<%= article.getEnchere().getMontant() %>">
				<input type="submit" value="Enchérir" >
			
			</form>
		<%}else{ %>
		
		<p>Connectez vous pouvoir enchérir</p>
		<%} %>
		
		
		
		
		
		
	</div>

</div>

</body>
<%@include file="/WEB-INF/jspf/script.jspf" %>
</html>