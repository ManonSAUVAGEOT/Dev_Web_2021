
<%-- 
    Document   : createaccount
    Created on : 10 mai 2021, 11:19:01
    Author     : manon
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Création du compte</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <h1>Création profil</h1>
        
        <form action="CreateAccountServlet" method="post">
            <p>Nom d'utilisateur : <input type="text" name="nomUtil" required></p>
            <p>Mot de passe : <input type="password" name="mdp2" required></p>
            <p>Age : <input type="text" name="age"></p>
            <p>Sexe : <input type="text" name="sexe"></p>
            <p>Ville : <input type="text" name="ville"></p>
            <input type="submit" value="Créer profil">
        </form>
    </body>
</html>