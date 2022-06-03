<%@page import="java.time.LocalDateTime"%>
<%@page import="java.time.LocalTime"%>
<%@page import="fr.eni.encheres.bo.Retrait"%>
<%@page import="fr.eni.encheres.bo.Enchere"%>
<%@page import="fr.eni.encheres.bo.Categorie"%>
<%@page import="fr.eni.encheres.bo.Article"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
   <% Article article = (Article) request.getAttribute("article");  %>


    
<%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<%@include file="/WEB-INF/jspf/header.jspf" %>
<body>




<div class="containerArticle">

	<img src="asset/img/ImgArticles/Article<%=article.getNoArticle()%>.jpg">
	
	<div class="containerInfo">
	
	    <% if(article.getNoAcheteur() == user.getNoUtilisateur()){%>
	    
	    	<h3>Vous avez remportez l'enchère</h3>   
	    
	    <%}else{%>
	    
	      <% if(article.getNoAcheteur() != 0){%>
	      <h3> <a href="<%= request.getContextPath() %>/utilisateur?id=<%= article.getEnchere().getEncherisseur().getNoUtilisateur()%>"> <%=article.getEnchere().getEncherisseur().getPseudo() %> </a>à remporté l'enchère</h3>
	    
	    <%}
	      
	    } %>
	    
	    
	
		<h2><%=article.getNomArticle() %></h2>
		<span>Catégorie: <%= article.getCatagorie().getLibelle() %>  </span>
		
		<p> Description : <%= article.getDescription() %></p>
		
		
		
		<div class="divider"></div>
		<%if(article.getEnchere().getEncherisseur().getPseudo()!= null){ %>
		
			
			<p>Meilleur offre : <%= article.getEnchere().getMontant() %>  </p>
		
		
		
		<%} %>
		
		
		
		
		<%if(article.getEnchere().getNoUtilisateur() == user.getNoUtilisateur()){ %>
				
				<span>(vous avez la meilleure enchère)</span> 
			<%}else{ %>
			
			<% if(article.getEnchere().getNoUtilisateur() != 0) { %>
				<span>par <a href="<%= request.getContextPath() %>/utilisateur?id=<%= article.getEnchere().getNoUtilisateur() %>"> <%=article.getEnchere().getEncherisseur().getPseudo() %> </a></span>
			<%}else{ %>
		
				<span>Il n'y a aucune enchère sur cette vente</span>
		 <%}} %>
		<p> Mise à prix :<%= article.getPrixInitial() %> points</p>
		
	<div class="divider"></div>
		<p>Fin de l'enchère <%= article.getSTRDateFinEnchere() %></p>
	<div class="divider"></div>	
		
		<div class="infoRetrait"></div>
		<p>Retrait :</p>
		 <% Retrait retrait = article.getRetrait(); %>
		 <p>
		 <%= retrait.getRue() %> </br>
		 <%= retrait.getVille() %> <%=retrait.getCodePostal() %>
		 
		 
		 </p>
		
		
		
		
		
		<p>Vendu par:  <a href="<%= request.getContextPath() %>/utilisateur?id=<%= article.getNoVendeur()%>"> <%=article.getVendeur().getPseudo() %> </a></p>
		<% if(article.getNoAcheteur() == user.getNoUtilisateur()){ %> <p>Téléphone : <%= article.getVendeur().getTelephone() %></p><%} %>
		<div class="divider"></div>
		

			<%if(user != null ){%>


			<% if(user.getNoUtilisateur() != article.getNoVendeur() && article.getNoAcheteur() == 0){ %>

					<% LocalDateTime dateToday = LocalDateTime.now();
					if(dateToday.compareTo(article.getDateDebutEnchere())>0){%>

						<p>Vos crédit disponible : <%= user.getCredit() %></p>
						<form action="<%=request.getContextPath()%>/article?idarticle=<%=article.getNoArticle() %>" method="post">
							<input type="number" name="offre" max="<%= user.getCredit()%>" min="<%= article.getEnchere().getMontant() %>" value="<%= article.getEnchere().getMontant() %>">
							<input class="montserrat600 bg-blue btn roundRadius SB20 H5" type="submit" value="Enchérir" >


						</form>


					<%}else{ %>
						<p>l'enchère n'a pas commencé</p>
						 <p>Début de l'enchère le :<%= article.getSTRDateDebutEnchere() %></p> 
					<%} %>
					

				<%} %>
				
				<%}else{ %>

					<p>Connectez vous pouvoir enchérir</p>

				<%} %>
		
		<div class="divider"></div>
		<!-- AFFICHAGE BOUTON DE MODIFICIATION D'ARTICLE -->
		<% 
		if(user != null && user.getNoUtilisateur() == article.getNoVendeur()){
		%>
		<div class="btnUtilisateurArticle">
					
					<a class="montserrat600 bg-blue btn roundRadius SB30 H" href="<%=request.getContextPath() %>/annulationVente?idArticle=<%= article.getNoArticle() %>" > Annuler la vente</a>
			
		</div>
		
		<%} %>
		
		
		
	</div>

</div>

</body>
<%@include file="/WEB-INF/jspf/script.jspf" %>
</html>