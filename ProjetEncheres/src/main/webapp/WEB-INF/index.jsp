
<%@include file="/WEB-INF/jspf/head.jspf" %>
</head>
<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>


<section class="research-section">
    <form>
        <input  class="roundRadius form-control form-control-lg inputResearch" type="text" name="research" placeholder="Trouvez l'objet de vos envies...">
		<!-- Faire itération à partir des catagorie recupérer sur serveur -->
        <div class="flex-spaceb centered montserrat-normal'">
            <select class="H5 montserrat-normal custom-select roundRadius SB30 bg-blue">
                <option selected>Catégorie</option>
                <option value="1">One</option>
                <option value="2">Two</option>
                <option value="3">Three</option>
            </select>

            <a href="" class="montserrat-normal">plus d'options</a>
            <button type="submit" class="montserrat600 bg-blue btn roundRadius SB30 H5"> Rechercher </button>
        </div>
        
    </form>


</section>

<%@include file="/WEB-INF/jspf/affichageArticles.jspf" %>


</body>

<%@include file="/WEB-INF/jspf/script.jspf" %>
</html>