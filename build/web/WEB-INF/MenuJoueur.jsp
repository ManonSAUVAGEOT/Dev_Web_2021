<%@page import="pojo.Joueur"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Jeu du Cul de Chouette</title>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/WS.js"></script>
        <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet" type="text/css" />
    </head>
    <body onload="ouvrirConnexion()">
        <section id="ProfilJoueur">
            <%Joueur joueur = (Joueur) request.getAttribute("Joueur");%>
            <div class="divStyle">
                <h1>Mon profil</h1>
                <hr/>
                <p>
                    <ul>
                        <li>Pseudo : <span id="nomJoueur"><%out.println(joueur.getPseudo());%></span></li>
                        <li>Age : <span id="age"><%out.println( joueur.getAge() );%></span></li>
                        <li>Sexe : <span id="sexe"><%out.println( joueur.getSexe() );%></span></li>
                        <li>Ville : <span id="ville"><%out.println( joueur.getVille() );%></span></li>
                    </ul>
                    
                    </p>
                    <div id="modifier" style="display: none;">
                        <p>Mot de passe : <input type="password" id="mdp2"></p>
                        <p>Age : <input type="number" placeholder="<%out.println( joueur.getAge() );%>" min="0" max="120" id="agemodif"></p>
                        <p>Sexe : <input type="select" id="sexemodif" placeholder="<%out.println( joueur.getSexe() );%>"></p>
                        <p>Ville : <input type="text" id="villemodif" placeholder="<%out.println( joueur.getVille() );%>"></p>
                        <p>Entre seulement les champs que tu veux modifier.</p>
                        <input value="Annuler" type="button" onclick="annuler()">
                        <input value="Enregistrer" type="button" onclick="enregistrer()">
                    </div>
                    <input id="btnmodif" type="button" value="Modifier le profil" onclick="modifier()"> 
                    <input id="deconnect" type="button" value="Se déconnecter" style="margin-top: 1em" onclick="deconnecter()">
            </div>
            
            <div class="divStyle">
                <h1>Statistiques</h1>
                <hr/>
                <p>
                    <ul>
                        <li>Nombre de parties jouées : <span id="nbrpartie"><%out.println( joueur.getNbrPartieJouee() );%></span></li>
                        <li>Nombre de victoires : <span id="nbrvictoire"><%out.println( joueur.getNbrVictoire() );%></span></li>
                        <li>Nombre moyen de victoires : <span id="moyenvictoire"><%out.println( joueur.getNbrMoyenVictoire() );%></span></li>
                        <li>Score moyen : <span id="scoremoyen"><%out.println( joueur.getScoreMoyen() );%></span></li>
                        <li>Moyenne de suites gagnées : <span id="nbrsuite"><%out.println( joueur.getMoyenneSuitesGagnees() );%></span> </li>
                        <li>Moyenne de chouettes velutes perdues : <span id="nbrcv"><%out.println( joueur.getMoyenneCvPerdues() );%></span></li>
                    </ul>
                </p>
            </div>
            
            <div class="divStyle">
                <h1>Joueurs Connectés</h1>
                <hr/>
                <form id="listeJoueur"></form>
            </div>
            
        </section>
<!------------------------------------------------------EN ATTENTE DE JOUEURS--------------------------------------------------------------------------------------------------------------------->
        <section id="AttenteJoueur" style="display: none;">
            <div class="divStyle">
                <h1>En attente de joueurs !</h1>
                <hr />
                <p>Partie créée par <span id="createur"></span></p>
                <p>Vous : <span id="joueur"><%out.println(joueur.getPseudo());%></span></p>
                <p>Liste des joueurs : <span id="participants"></span></p>
                
                <div id="retourmenu" style="display:none;">
                    <input onclick="retour()" value="Revenir au menu" type="button">
                </div>
                <div id="debutpartie" style="display:none;">
                    <p>Valeur à atteindre : <input id="valeurmax" type="number" value="343" min="1"></p>
                    <input onclick="commencer()" value="Commencer la partie" type="button">
                </div>
            </div>
            
        </section>
<!------------------------------------------------------PARTIE EN COURS-------------------------------------------------------------------------------------------------------------------->
        <section id="PartieEncours" style="display: none;">
            <div id="InfosMoi">
                    <p>
                        Moi : 
                        <br/>
                        <b><span id="joueur"><%out.println(joueur.getPseudo());%></span></b>
                        <br/>
                        Score :  <b><span id="score"></span></b>
                    </p>
                </div>
                
                <div id="InfosAdversaires">
                    <div style="boder: solid 1px grey">
                        <table>
                            <tr id="trAdversaires"></tr>
                        </table>
                    </div>
                   
                    <h3>Valeur à atteindre : <span id="valeurMaxSpan"></span></h3>
                    <h2>Tour de : <b><span id="tour"></span></b><br /></h2>
                </div>
                
                <div id="RecapScore" class="modal">
                    <div class="modal-content">
                      <h2>Récapitulatif des scores :</h2>
                      <hr />
                      <div id="recap"></div>
                      <input id="finpartie" onclick="findePartie()" value="Quitter la partie" type="button" style="display:none;">
                    </div>
                </div>
                        
                <div id="MessageJeu">
                    <p style="margin-top: 0.5em">Lancers : <span id="message"></span></p>
                    <div id="Div3Des">
                        <div class="d" id="des1"></div>
                        <div class="d" id="des2"></div>
                        <div class="d" id="des3"></div>
                    </div>
                    <p style="margin-top: 0.5em"><span id="interaction"></span></p>
                </div>
                        
                <div id="actions">
                    <input id="De1" disabled="true" alt="Lancer les deux premiers dés" type="image" onclick="lancerDe1()" src="${pageContext.request.contextPath}/images/de.png"/>
                    <input id="De2" disabled="true" alt="Lancer le dernier dé" type="image" onclick="lancerDe2()" src="${pageContext.request.contextPath}/images/de1.png"/>
                    <input id="Grelotte" disabled="true" value="Grelotte ça picoste!" type="button" onclick="crierGrelotte()"/>
                    <input id="Velute" disabled="true" value="Pas mou le caillou!" type="button" onclick="crierVelute()"/>
                </div>
            
                
        </section>
        
     </body>
</html>
