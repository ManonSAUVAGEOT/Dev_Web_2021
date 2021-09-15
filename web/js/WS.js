/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var websocket;

        function ouvrirConnexion() {
           websocket = new WebSocket("ws://localhost:8080/CulDeChouetteWeb/chat");
           websocket.onopen = function(evt) {
           onOpen(evt);
           };
           websocket.onmessage = function(evt) {
           onMessage(evt);
           };
           websocket.onerror = function(evt) {
           onError(evt);
           };
           websocket.onclose = function(evt) {
           onClose(evt);
           };
        }

        // appelée quand la connexion est ouverte
        function onOpen(evt) {
            alert("Connexion");
            let pseudo = document.getElementById("nomJoueur");
            var joueur = {
                type: "joueurConnect",
                text: pseudo.innerText
            };
            websocket.send(JSON.stringify(joueur));
        }
        
        var listJoueurs;

       function onMessage(evt) {
            var msg = JSON.parse(evt.data);
            liste = document.getElementById("listeJoueur");
            let pseudo = document.getElementById("nomJoueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            //mettre à jour la liste des joueurs connectés
            if(msg.type === "listeJoueur") {
                alert("Connection de " + msg.text[msg.text.length -1]);
                let str = ``;
                for(var i=0; i<msg.text.length;i++){
                    if(pseudostr !== msg.text[i]){
                        str += `<p><input type="checkbox" name="joueur" value="${msg.text[i]}">${msg.text[i]}</p>`;
                    }
                }
                str += `<input type="button" value="Lancer une partie" onclick="envoiInvit()">`;
                liste.innerHTML = str;
            }//Reception d'une invitation à une partie
            else if(msg.type === "Invitation"){
                //si j'accepte la partie
                if (window.confirm(msg.text + msg.createur)){
                    document.getElementById("ProfilJoueur").style.display = "none";
                    document.getElementById("AttenteJoueur").style.display = "block";
                    document.getElementById("createur").innerHTML =  msg.createur;
                    var invit = {
                        type: "InvitPartie",
                        joueur: pseudostr,
                        reponse: true
                    };
                    websocket.send(JSON.stringify(invit));
                }//Si je refuse la partie
                else{
                    alert("Vous avez refusé la partie.");
                    var invit = {
                        type: "InvitPartie",
                        joueur: pseudostr,
                        reponse: false
                    };
                    console.log(invit);
                    websocket.send(JSON.stringify(invit));
                }
            }//ffiche la liste des joueurs connectés pour la partie
            else if(msg.type === "listePartie") {
                let liste = document.getElementById("participants");
                alert(msg.text[msg.text.length -1] + " a rejoint la partie");
                let str = `` + msg.text;
                listJoueurs = msg.text;
                liste.innerHTML = str;
            }//Si il y a au moins deux joueurs dans la partie, le créateur peut commencer
            else if(msg.type === "confirmation"){
                if(msg.createur === pseudostr){
                    document.getElementById("debutpartie").style.display = "block";
                }
            }//Sinon il retourne au menu
            else if(msg.type === "annulation"){
                if(msg.createur === pseudostr){
                    alert("Tout le monde a refusé la partie..");
                    document.getElementById("retourmenu").style.display = "block";
                }
           }//Début de la partie
           else if(msg.type === "Commencer") {
                document.getElementById("message").innerHTML = "";
                document.getElementById("interaction").innerHTML = "";
                document.getElementById("participants").innerHTML = " ";
                document.getElementById("debutpartie").style.display = "none";
                document.getElementById("AttenteJoueur").style.display = "none";
                document.getElementById("PartieEncours").style.display = "block";
                document.getElementById("trAdversaires").innerHTML = "";
                document.getElementById("des1").innerHTML = "";
                document.getElementById("des2").innerHTML = "";
                document.getElementById("des3").innerHTML = "";
                document.getElementById("tour").innerHTML = msg.tour;
                document.getElementById("score").innerHTML = 0;
                document.getElementById("valeurMaxSpan").innerHTML = msg.valeurmax;
                for (var i = 0; i<listJoueurs.length;i++) {
                    if (listJoueurs[i] !== pseudostr) {
                        document.getElementById("trAdversaires").innerHTML+= "<td>"+ listJoueurs[i]+"<hr />0</td>";
                    }
                }
                listJoueurs = [];
                if(msg.tour === pseudostr){
                    document.getElementById("De1").disabled = false;
                }
            }//Résultats premier lancer de dés
            else if(msg.type === "Lancer1") {
                document.getElementById("message").innerHTML = "";
                document.getElementById("interaction").innerHTML = "";
                if(msg.lanceur === pseudostr){
                    document.getElementById("De1").disabled = true;
                    document.getElementById("De2").disabled = false;
                    document.getElementById("score").innerHTML = msg.score;
                }
                document.getElementById("des1").innerHTML = msg.lancer1;
                document.getElementById("des2").innerHTML = msg.lancer2;
                document.getElementById("des3").innerHTML = "";
            }//Résultat deuxième lancer et test si interaction
            else if(msg.type === "Lancer2") {
                if(msg.lanceur === pseudostr){
                    document.getElementById("De2").disabled = true;
                }
                if(msg.interaction === "velute"){
                    document.getElementById("message").innerHTML = "Chouette velute !";
                    document.getElementById("Velute").disabled = false;
                }
                if(msg.interaction === "suite"){
                    document.getElementById("message").innerHTML = "Suite !";
                    document.getElementById("Grelotte").disabled = false;
                }
                document.getElementById("des1").innerHTML = msg.lancer1;
                document.getElementById("des2").innerHTML = msg.lancer2;
                document.getElementById("des3").innerHTML = msg.lancer3;
            }//Tour suivant, changement de lanceur 
            else if(msg.type === "debutTour"){
                document.getElementById("tour").innerHTML = msg.joueur;
                if(msg.joueur === pseudostr){
                    document.getElementById("De1").disabled = false;
                }
                for( var i=0;i<msg.scoretab.length;i++){
                    //console.log(msg.scoretab[i].joueur);
                    if(msg.scoretab[i].joueur === pseudostr){
                        //console.log(msg.scoretab[i].score);
                        document.getElementById("score").innerHTML = msg.scoretab[i].score;
                    }
                    document.getElementById("trAdversaires").innerHTML="";
                    for (var i = 0; i<msg.scoretab.length;i++) {
                    if (msg.scoretab[i].joueur !== pseudostr) {
                        document.getElementById("trAdversaires").innerHTML+= "<td>"+ msg.scoretab[i].joueur+"<hr />"+msg.scoretab[i].score+"</td>";
                    }
                }   
                }
            }//Reception des cris grelotte
            else if(msg.type === "interactiongrelotte"){
                if(msg.joueur !== pseudostr){
                    document.getElementById("interaction").innerHTML+= "<p>"+ msg.joueur + " a crié 'Grelotte ça picote !'<br /></p>";
                }
           }//reception des cris velute
           else if(msg.type === "interactionvelute"){
                if(msg.joueur !== pseudostr){
                    document.getElementById("interaction").innerHTML+= "<p>"+ msg.joueur + " a crié 'Pas mou le caillou !'<br /></p>";
                }
           }//Reception de fin de partie, afficher le recapitulatifs des scores
           else if(msg.type === "findePartie"){
                document.getElementById("des1").innerHTML = msg.lancer1;
                document.getElementById("des2").innerHTML = msg.lancer2;
                document.getElementById("des3").innerHTML = "";
                if(msg.lancer3 !== "null"){
                    document.getElementById("des3").innerHTML = msg.lancer3;
                }
                alert(msg.gagnant + " a gagné la partie !");
                var sauvegarde = {
                    type: "sauvegarde",
                    gagnant: msg.gagnant//,
                    //joueur: pseudostr
                };
                document.getElementById("RecapScore").style.display = "block";
                //console.log(msg);
                listScore = msg.recap;
                for (var i = 0; i<listScore.length;i++) {
                    document.getElementById("recap").innerHTML+= "<p>"+ listScore[i].joueur+" : "+ listScore[i].points + "<br /></p>";
                }
                listScore = [];
                websocket.send(JSON.stringify(sauvegarde));
            }//Sauvegarde fini, bouton quitter
            else if(msg.type === "fini"){
                document.getElementById("finpartie").style.display = "block";
            }//Reception des nouvelles statistiques du joueur pour mettre à jour l'affichage
            else if(msg.type === "statistiques"){
                document.getElementById("nbrpartie").innerHTML = msg.partie;
                document.getElementById("nbrvictoire").innerHTML = msg.victoire;
                document.getElementById("moyenvictoire").innerHTML = msg.moyenvictoire;
                document.getElementById("scoremoyen").innerHTML = msg.score;
                document.getElementById("nbrsuite").innerHTML = msg.suite;
                document.getElementById("nbrcv").innerHTML = msg.chouettevelute;
                alert("Vos statistiques ont été mise à jour !");
            }//Reception des modifications des infos du joueur
            else if(msg.type === "modification"){
                if(msg.mdp !== ""){
                   document.getElementById("mdp").innerHTML = msg.mdp; 
                }
                if(msg.age !== ""){
                   document.getElementById("age").innerHTML = msg.age; 
                }
                if(msg.sexe !== ""){
                   document.getElementById("sexe").innerHTML = msg.sexe; 
                }
                if(msg.ville !== ""){
                   document.getElementById("ville").innerHTML = msg.ville; 
                }
                alert("Vous avez modifier votre profil !");
            }
        }
        
        //envoyer une invitation aux joueurs qui ont été cochés
        function envoiInvit(){
            const checkboxes = document.querySelectorAll(`input[name="joueur"]:checked`);
            let values = [];
            if(checkboxes.length < 1){
                alert("Sélectionner au moins un joueur");
            }else if(checkboxes.length > 6){
                alert("Sélectionner moins de 6 personnes");
            }else{
                checkboxes.forEach((checkbox) => {
                values.push(checkbox.value);
                });
                let pseudo = document.getElementById("nomJoueur");
                let pseudostr = pseudo.innerHTML.replace("\n","");
                var joueurs = {
                    type: "joueurInvit",
                    createur: pseudostr,
                    text: values
                };
                websocket.send(JSON.stringify(joueurs));
                document.getElementById("createur").innerHTML =  pseudo.innerText;
                document.getElementById("ProfilJoueur").style.display = "none";
                document.getElementById("AttenteJoueur").style.display = "block";
            }
        }

        
       // appelée quand il y a une erreur
       function onError(evt) {
        alert("Erreur : " + evt);
       }
       
       //Commencer une partie
       function commencer(){
            var message = {
                type: "Commencer",
                valeurmax: document.getElementById("valeurmax").value
            };
            websocket.send(JSON.stringify(message));
        }
        
        //Revenir au menu si personne ne veut jouer
        function retour(){
            document.getElementById("retourmenu").style.display = "none";
            document.getElementById("AttenteJoueur").style.display = "none";
            document.getElementById("ProfilJoueur").style.display = "block";
            let pseudo = document.getElementById("joueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            var annule = {
                type: "Annuler",
                joueur: pseudostr
            };
            websocket.send(JSON.stringify(annule));
            
        }
        
        //Lancer les deux premiers dés
        function lancerDe1(){
            let pseudo = document.getElementById("joueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            let de1 = Math.floor(Math.random() * 6) + 1;
            let de2 = Math.floor(Math.random() * 6) + 1;
            var lancer = {
                type: "Lancer1",
                lanceur: pseudostr,
                lancer1: de1,
                lancer2: de2
            };
            websocket.send(JSON.stringify(lancer));
        }
        
        //Lancer le dernier dé
        function lancerDe2(){
            let pseudo = document.getElementById("joueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            let de3 = Math.floor(Math.random() * 6) + 1;
            var lancer = {
                type: "Lancer2",
                lanceur: pseudostr,
                lancer3: de3
            };
            websocket.send(JSON.stringify(lancer));
        }
        
        //Crier 'Pas mou le caillou !' pour une chouette velute
        function crierVelute(){
            let pseudo = document.getElementById("joueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            var velute = {
                type: "crivelute",
                joueur: pseudostr
            };
            document.getElementById("interaction").innerHTML+= "<p>"+ pseudostr + " a crié 'Pas mou le caillou !'<br /></p>";
            websocket.send(JSON.stringify(velute));
            document.getElementById("Velute").disabled = true;
        }
        
        //Crier 'Grelotte ça picotte !' pour une suite
        function crierGrelotte(){
            let pseudo = document.getElementById("joueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            var grelotte = {
                type: "crigrelotte",
                joueur: pseudostr
            };
            document.getElementById("interaction").innerHTML+= "<p>"+ pseudostr + " a crié 'Grelotte ça picote !'<br /></p>";
            websocket.send(JSON.stringify(grelotte));
            document.getElementById("Grelotte").disabled = true;
        }
        
        //Quitter la partie et mettre à jour les statistiques du joueur
        function findePartie(){
            let pseudo = document.getElementById("nomJoueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            document.getElementById("recap").innerHTML = " ";
            document.getElementById("finpartie").style.display = "none";
            document.getElementById("RecapScore").style.display = "none";
            document.getElementById("PartieEncours").style.display = "none";
            document.getElementById("ProfilJoueur").style.display = "block";
            var demande = {
                type: "demande",
                joueur: pseudostr
            };
            websocket.send(JSON.stringify(demande));
        }
        
        //modifier les données du joueur
        function modifier(){
            document.getElementById("btnmodif").style.visibility = "hidden";
            document.getElementById("deconnect").style.visibility = "hidden";
            document.getElementById("modifier").style.display = "block";
        }
        
        //annuler la modification des données du joueur
        function annuler(){
            document.getElementById("btnmodif").style.visibility = "visible";
            document.getElementById("deconnect").style.visibility = "visible";
            document.getElementById("modifier").style.display = "none";
        }
        
        //enregistrer les modification des données du joueur
        function enregistrer(){
            let pseudo = document.getElementById("nomJoueur");
            let pseudostr = pseudo.innerHTML.replace("\n","");
            document.getElementById("btnmodif").style.visibility = "visible";
            document.getElementById("deconnect").style.visibility = "visible";
            document.getElementById("modifier").style.display = "none";
            let age;
            if(document.getElementById("agemodif").value === null){
                age = -1;
            }else{
                age = parseInt(document.getElementById("agemodif").value);
            }
            var modif = {
                type: "modification",
                joueur: pseudostr,
                mdp: document.getElementById("mdp2").value,
                age: age,
                sexe: document.getElementById("sexemodif").value,
                ville: document.getElementById("villemodif").value   
            };
            websocket.send(JSON.stringify(modif));
            //document.getElementById("mdp2").value = "";
            //document.getElementById("agemodif").value = "";
            //document.getElementById("sexemodif").value = "";
            //document.getElementById("villemodif").value = "";
        }
        
        //Se déconnecter et revenir à la page de connection
        function deconnecter(){
            websocket.close();
            window.location.href ="http://localhost:8080/CulDeChouetteWeb";
        }