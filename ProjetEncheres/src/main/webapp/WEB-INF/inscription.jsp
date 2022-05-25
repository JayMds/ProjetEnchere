

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Inscription</title>
</head>
<body>



<form action="<%= request.getContextPath() %>/inscription" method="post">
<div>
	<label for="idPseudo">Pseudo:</label>
	<input type ="text" id="idPseudo" name="pseudo" required="required" placeholder="votre speudo" >
</div>
<div>
	<label for="idNom">Nom :</label>
	<input type ="text" id="idNom" name="nom" required="required" placeholder="votre nom" >
</div>	
<div>
	<label for="idPrenom">Prénom :</label>
	<input type ="text" id="idPrenom" name="prenom" required="required" placeholder="votre prénom" >
</div>	
<div>
	<label for="idEmail">Email :</label>
	<input type ="email" id="idEmail" name="email" required="required" placeholder="votre email" >
</div>	
<div>
	<label for="idTelephone">Téléphone :</label>
	<input type ="tel" id="idTelephone" name="telephone" required="required" placeholder="votre téléphone" inputmode="tel" pattern="^\+?\d{0,13}" >
</div>	
<div>
	<label for="idRue">Rue :</label>
	<input type ="text" id="idRue" name="rue" required="required" placeholder="votre adresse">
</div>	
<div>
	<label for="idCodePostal">Code postal :</label>
	<input type ="number" id="idCodePostal" name="codePostal" required="required" placeholder="votre code postal" inputmode="numeric" pattern="^(?(^00000(|-0000))|(\d{5}(|-\d{4})))$">
</div>	
<div>
	<label for="idVille">Ville :</label>
	<input type ="text" id="idVille" name="ville" required="required" placeholder="votre ville" >
</div>	
<div>
	<label for="idMotdePasse">Mot de passe :</label>
	<input type ="password" id="idMotdePassee" name="motDePasse" required="required" placeholder="votre MotDePasse" >
</div>
<div>
	<label for="idConfirmation">confirmation:</label>
	<input type ="password" id="idConfirmation" name="confirmation" required="required" placeholder="Confirmer votre mot de passe" >
</div>


	
	
	
	
	<input type="submit" value="valider">
	<a href="<%=request.getContextPath()%>"><input type="button" value="Annuler"/></a>
</form>


</body>


<script>
document.getElementById("idPseudo").setAttribute("value","MonSpeudo");
document.getElementById("idEmail").setAttribute("value","mail@gmail.com");
document.getElementById("idNom").setAttribute("value","MonNom");
document.getElementById("idPrenom").setAttribute("value","MonPrenom");
document.getElementById("idTelephone").setAttribute("value","0698657485");
document.getElementById("idRue").setAttribute("value","9 rue du clos");
document.getElementById("idCodePostal").setAttribute("value","95000");
document.getElementById("idVille").setAttribute("value","Paris");
document.getElementById("idMotdePasse").setAttribute("value","123");
document.getElementById("idConfirmation").setAttribute("value","123");


</script>
</html>