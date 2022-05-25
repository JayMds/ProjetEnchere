
<body>

<%@include file="/WEB-INF/jspf/header.jspf" %>


<section class="research-section">
    <form>
        <input  class="roundRadius form-control form-control-lg inputResearch" type="text" name="research" placeholder="Trouvez l'objet de vos envies...">

        <div class="flex-spaceb centered montserrat-normal'">
            <select class=" montserrat-normal custom-select roundRadius SB30 bg-blue">
                <option selected>Catégorie</option>
                <option value="1">One</option>
                <option value="2">Two</option>
                <option value="3">Three</option>
            </select>

            <a href="" class="montserrat-normal"> plus d'options</a>
            <button type="submit" class="montserrat600 bg-blue btn btn-primary btn-lg roundRadius SB30"> Rechercher </button>
        </div>
        
    </form>


</section>


</body>

<%@include file="/WEB-INF/jspf/script.jspf" %>
</html>