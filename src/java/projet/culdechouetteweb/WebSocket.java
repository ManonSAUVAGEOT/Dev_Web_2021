/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet.culdechouetteweb;

import DAO.AbstractDAOFactory;
import DAO.CulDeChouetteDAOFactory;
import DAO.DAO;
import DAO.DAOException;
import DAO.PersistenceKind;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONArray;
import org.json.JSONObject;
import pojo.Des;
import pojo.Joue;
import pojo.JouePK;
import pojo.Joueur;
import pojo.Partie;
/**
 *
 * @author manon
 */
@ServerEndpoint("/chat")
public class WebSocket {
    
    // la liste des websockets : en static pour être partagée
    private static ArrayList<Session> listeWS = new ArrayList<>();
    private static ArrayList<String> listeJoueurConnecte = new ArrayList<>();
    private static ArrayList<String> listeAttente = new ArrayList<>();
    private static ArrayList<String> listeJoueurPartie = new ArrayList<>();
    private static Integer CodePartie;
    private static ArrayList<Joue> TabJoue = new ArrayList<>();
    private static Des des = new Des();
    private static Partie p = new Partie();
    private static ArrayList<String> Interaction = new ArrayList<>();
    private static Integer tour = 0;
    private static Integer valeurmax;
    private static Integer save = 0;
    
    //Envoie un message à tous les joueurs dans une partie
    private void envoiJoueurPartie(JSONObject message) throws IOException{
        for (int i=0; i< listeJoueurPartie.size() ; i++) {
            int index = listeJoueurConnecte.indexOf(listeJoueurPartie.get(i));
            listeWS.get(index).getBasicRemote().sendText(message.toString());
        }
    }

    
    @OnOpen
    public void open(Session session) throws DAOException {
        // à l'ouverture d'une connexion, on rajoute la WS dans la liste
        WebSocket.listeWS.add(session);
        System.out.println("Open websoket session");
    }
    @OnClose
        public void onClose(CloseReason reason) {
        System.out.println("Fermeture de la WS");
    }
    @OnError
        public void error(Throwable t) {
        System.err.println("Erreur WS : "+t);
    }
        
    @OnMessage
    public void onMessage(Session session, String msg) throws IOException, DAOException, SQLException {
        JSONObject obj = new JSONObject(msg);
        //Reception d'un joueur qui se connecte
        if ("joueurConnect".equals(obj.getString("type"))) {
            String n = obj.getString("text");
            listeJoueurConnecte.add(n);
            JSONArray jsArray = new JSONArray(listeJoueurConnecte);

            JSONObject liste = new JSONObject();
            liste.put("type", "listeJoueur");
            liste.put("text", jsArray);
            try {
                for (int i=0; i< listeWS.size() ; i++) {
                  listeWS.get(i).getBasicRemote().sendText(liste.toString());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }//Recpetion d'une création de partie avec une liste de joueur à inviter
        else if("joueurInvit".equals(obj.getString("type"))){
            String createur = obj.getString("createur");
            listeJoueurPartie.clear();
            listeJoueurPartie.add(createur);
            JSONArray jsArray = new JSONArray(obj.getJSONArray("text"));
            for (int i=0; i< jsArray.length();i++){
                listeAttente.add(jsArray.getString(i));
                int index = listeJoueurConnecte.indexOf(jsArray.get(i));
                if(index!= -1){
                    JSONObject invit = new JSONObject();
                    invit.put("type", "Invitation");
                    invit.put("createur", createur);
                    invit.put("text", "Tu es invité dans une partie par ");
                    try{
                        listeWS.get(index).getBasicRemote().sendText(invit.toString());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }//Reception de la décision des joueurs pour rejoindre ou non la partie
        else if("InvitPartie".equals(obj.getString("type"))){
            //Si un joueur accepte l'invitation
            if(obj.getBoolean("reponse")){
                //System.out.println("accepter");
                listeJoueurPartie.add(obj.getString("joueur"));
                listeAttente.remove(obj.getString("joueur"));
                JSONArray jsArray = new JSONArray(listeJoueurPartie);
                JSONObject liste = new JSONObject();
                liste.put("type", "listePartie");
                liste.put("text", jsArray);
                //vérifier qu'il y ai au moins un joueur en plus du créateur;
                envoiJoueurPartie(liste);
            }else{
                listeAttente.remove(obj.getString("joueur"));
            }
            //Quand tout le monde a répondu à l'invitation (par oui ou par non)
            if(listeAttente.isEmpty()){
                if(listeJoueurPartie.size()>1){
                    JSONObject confirmation = new JSONObject();
                    confirmation.put("type", "confirmation");
                    confirmation.put("createur", listeJoueurPartie.get(0));
                    int index = listeJoueurConnecte.indexOf(listeJoueurPartie.get(0));
                    listeWS.get(index).getBasicRemote().sendText(confirmation.toString());
                }else{
                    JSONObject confirmation = new JSONObject();
                    confirmation.put("type", "annulation");
                    confirmation.put("createur", listeJoueurPartie.get(0));
                    int index = listeJoueurConnecte.indexOf(listeJoueurPartie.get(0));
                    listeWS.get(index).getBasicRemote().sendText(confirmation.toString());
                } 
            }
        }//Si tout le monde à refuser l'invitation
        else if("Annuler".equals(obj.getString("type"))){
            listeJoueurPartie.remove(obj.getString("joueur"));
        }//Commencer la partie
        else if("Commencer".equals(obj.getString("type"))){
            save = 0;
            tour = 0;
            valeurmax = obj.getInt("valeurmax");
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
            DAO<Partie> daoPartie = factory.getDAOPartie();
            daoPartie.create(p);
            p.setNbrSuites(0);
            p.setNbrCv(0);
            CodePartie = p.getCodePartie();
            initTabJoue();
            JSONObject message = new JSONObject();
            message.put("type", "Commencer");
            message.put("tour", listeJoueurPartie.get(0));
            message.put("valeurmax", valeurmax);
            envoiJoueurPartie(message);
        }//Reception du premier lancer de dés
        else if ("Lancer1".equals(obj.getString("type"))) {
            des.setLanceur(obj.getString("lanceur"));
            des.setDe1(obj.getInt("lancer1"));
            des.setDe2(obj.getInt("lancer2"));
            des.setDe3(null);
            des.setCodePartie(p);
            int score = chouette(des.getDe1(),des.getDe2());
            int index = listeJoueurPartie.indexOf(des.getLanceur());
            score += TabJoue.get(index).getScore();
            TabJoue.get(index).setScore(score);
            if(testScore(score)){
                findePartie(obj.getString("lanceur"));
            }else{
                obj.put("score", score);
                envoiJoueurPartie(obj);
            }
        }//Reception du deuxième lancer de dé
        else if ("Lancer2".equals(obj.getString("type"))) {
            des.setDe3(obj.getInt("lancer3"));
            obj.put("lancer1", des.getDe1());
            obj.put("lancer2", des.getDe2());
            //test pour savoir s'il y a des interactions
            if(testVelute()){
                obj.put("interaction", "velute");
            }else if(testSuite(des.getDe1(), des.getDe2(), des.getDe3())){
                obj.put("interaction", "suite");
            }else{
                int score = velute(des.getDe1(), des.getDe2(), des.getDe3()) + CuldeChouette(des.getDe1(), des.getDe2(), des.getDe3());
                int index = listeJoueurPartie.indexOf(des.getLanceur());
                score += TabJoue.get(index).getScore();
                TabJoue.get(index).setScore(score);
                des.setInteraction(" ");
                if(testScore(score)){
                    findePartie(obj.getString("lanceur"));
                }else{
                    tourSuivant();
                }
            }
            envoiJoueurPartie(obj);
        }//Reception des cries pour une chouette velute
        else if("crivelute".equals(obj.getString("type"))){
            Interaction.add(obj.getString("joueur"));
            if(Interaction.size() == listeJoueurPartie.size()){
                int index = listeJoueurPartie.indexOf(Interaction.get(0));
                int score = des.getDe3()*des.getDe3();
                score += TabJoue.get(index).getScore();
                TabJoue.get(index).setScore(score);
                p.setNbrCv(p.getNbrCv()+1);
                String str = Interaction.get(0) + "a gagné la chouette velute";
                des.setInteraction(str);
                if(testScore(score)){
                    findePartie(Interaction.get(0));
                }else{
                   for( int i=0;i<TabJoue.size();i++){
                        if(i != index){
                            TabJoue.get(i).setNbrCvPerdues(TabJoue.get(i).getNbrCvPerdues()+1);
                        }
                    }
                    Interaction.clear();
                    tourSuivant(); 
                } 
            }else{
                JSONObject message = new JSONObject();
                message.put("type", "interactionvelute");
                message.put("joueur", obj.getString("joueur"));
                envoiJoueurPartie(message);
            }
        }//Reception des cries pour une suites
        else if("crigrelotte".equals(obj.getString("type"))){
            Interaction.add(obj.getString("joueur"));
            if(Interaction.size() == listeJoueurPartie.size()){
                int index = listeJoueurPartie.indexOf(Interaction.get(Interaction.size()-1));
                int score = TabJoue.get(index).getScore();
                score -= 10;
                if(score < 0){
                    score = 0;
                }
                TabJoue.get(index).setScore(score);
                p.setNbrSuites(p.getNbrSuites()+1);
                String str = Interaction.get(Interaction.size()-1) + "a perdu la suite";
                des.setInteraction(str);
                for( int i=0;i<TabJoue.size();i++){
                    if(i != index){
                        TabJoue.get(i).setNbrSuitesGagnees(TabJoue.get(i).getNbrSuitesGagnees()+1);
                    }
                }
                Interaction.clear();
                tourSuivant();
            }else{
                JSONObject message = new JSONObject();
                message.put("type", "interactiongrelotte");
                message.put("joueur", obj.getString("joueur"));
                envoiJoueurPartie(message);
            }
        }//Reception de fin de partie pour sauvegarder les données dans la base de données
        else if("sauvegarde".equals(obj.getString("type"))){
            save += 1;
            if(save == 1){
               sauvegarde(obj.getString("gagnant"));
                JSONObject message = new JSONObject();
                message.put("type", "fini");
                System.out.println("envoie message fini");
                envoiJoueurPartie(message); 
            }
        }//Reception d'une demande pour mettre à jour les statistiques du joueur sur la page de son profil
        else if("demande".equals(obj.getString("type"))){
            //listeJoueurPartie.remove(obj.getString("joueur"));
            System.out.println("demande");
            JSONObject message = new JSONObject();
            message.put("type", "statistiques");
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
            DAO<Joueur> daoJoueur = factory.getDAOJoueur();
            Joueur j = new Joueur();
            j = daoJoueur.trouveJoueur(obj.getString("joueur"));
            message.put("partie", j.getNbrPartieJouee());
            message.put("victoire", j.getNbrVictoire());
            message.put("moyenvictoire", j.getNbrMoyenVictoire());
            message.put("score", j.getScoreMoyen());
            message.put("suite", j.getMoyenneSuitesGagnees());
            message.put("chouettevelute", j.getMoyenneCvPerdues());
            int index = listeJoueurConnecte.indexOf(obj.getString("joueur"));
            listeWS.get(index).getBasicRemote().sendText(message.toString());
        }//Reception des modifications des infos du joueur
        else if("modification".equals(obj.getString("type"))){
            System.out.println(obj);
            JSONObject message = new JSONObject();
            message.put("type", "modification");
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
            DAO<Joueur> daoJoueur = factory.getDAOJoueur();
            Joueur j = new Joueur();
            j = daoJoueur.trouveJoueur(obj.getString("joueur"));
            if(!"".equals(obj.getString("mdp"))){
                j.setMotDePasse(obj.getString("mdp"));
                message.put("mdp",obj.getString("mdp"));
            }else{
                message.put("mdp","");
            }
            if(!(obj.getInt("age") == -1)){
                j.setAge(obj.getInt("age"));
                message.put("age",obj.getInt("age"));
            }else{
                message.put("age","");
            }
            if(!"".equals(obj.getString("sexe"))){
                j.setSexe(obj.getString("sexe"));
                message.put("sexe",obj.getString("sexe"));
            }else{
                message.put("sexe","");
            }
            if(!"".equals(obj.getString("ville"))){
                j.setVille(obj.getString("ville"));
                message.put("ville",obj.getString("ville"));
            }else{
                message.put("ville","");
            }
            daoJoueur.update(j);
            int index = listeJoueurConnecte.indexOf(obj.getString("joueur"));
            listeWS.get(index).getBasicRemote().sendText(message.toString());
        }
    }
        //Initialiser le tableau avec les données pour chaque joueur avec un objet Joue
        private void initTabJoue() throws DAOException{
        for (int i=0; i< listeJoueurPartie.size() ; i++) {
            Joue joue = new Joue();
            Joueur j = new Joueur();
            CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
            DAO<Joueur> daoJoueur = factory.getDAOJoueur();
            j = daoJoueur.trouveJoueur(listeJoueurPartie.get(i));
            joue.setJouePK(new JouePK(j.getCodeJoueur(),CodePartie));
            joue.setScore(0);
            joue.setNbrSuitesGagnees(0);
            joue.setNbrCvPerdues(0);
            joue.setPartie(p);
            joue.setJoueur(j);
            TabJoue.add(joue);
        }
    }
        
    private Integer chouette(int a, int b){
        if(a == b){
            return a*a;
        }else{
            return 0;
        }
    }
    
    private Integer velute(int a, int b, int c){
        if(a != b && c == a + b){
            return c*c;
        }else{
            return 0;
        }
    }
    
    private Integer CuldeChouette(int a, int b, int c){
        if(a == b && b == c){
            return 40 + a*10;
        }else{
            return 0;
        }
    }
    
        private boolean testVelute(){
        if( Objects.equals(des.getDe1(), des.getDe2()) && des.getDe1() + des.getDe2() == des.getDe3()){
            return true;
        }
        return false;
    }
    
    private boolean testSuite(int a, int b, int c){
        if(a <= b && a<= c){
            return (b == a+1 && c == a+2) || (c == a+1 && b == a+2);
        }else if(b <= a && b<= c){
            return (a == b+1 && c == b+2) || (c == b+1 && a == b+2);
        }else{
            return (a == c+1 && b == c+2) || (b == c+1 && a == c+2);
        }
    }
    
    private boolean testScore(int score){
        return score >= valeurmax;
    }
    
    //Terminer la partie car un joueur a atteint le score maximal et mettre à jour dans la base de données la partie
    private void findePartie(String pseudo) throws DAOException, IOException{
        JSONObject liste = new JSONObject();
        liste.put("type", "findePartie");
        liste.put("gagnant", pseudo);
        liste.put("lancer1", des.getDe1());
        liste.put("lancer2", des.getDe2());
        if(des.getDe3() != null){
            liste.put("lancer3", des.getDe3());
        }else{
            liste.put("lancer3", "null");
        }
        JSONArray score = new JSONArray();
        CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
        DAO<Partie> daoPartie = factory.getDAOPartie();
        DAO<Joue> daoJoue = factory.getDAOJoue();
        daoPartie.update(p);
        for (int i=0; i< TabJoue.size() ; i++) {
            JSONObject joueur = new JSONObject();
            joueur.put("joueur", TabJoue.get(i).getJoueur().getPseudo());
            joueur.put("points", TabJoue.get(i).getScore());
            TabJoue.get(i).setPartie(p);
            score.put(joueur);
        }
        DAO<Des> daoDes = factory.getDAODes();
        daoDes.create(des);
        liste.put("recap", score);
        envoiJoueurPartie(liste);
    }
    
    //Sauvegarder les données du joueur dans la base de données
    private void sauvegarde(String pseudo) throws DAOException, SQLException{
        CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
        DAO<Joue> daoJoue = factory.getDAOJoue();
        for (int i=0; i< TabJoue.size() ; i++) {
            daoJoue.create(TabJoue.get(i));
        }
        DAO<Joueur> daoJoueur = factory.getDAOJoueur();
        for (int i=0; i< listeJoueurPartie.size() ; i++) {
            Joueur j = new Joueur();
            j = daoJoueur.trouveJoueur(listeJoueurPartie.get(i));
            int a = j.getNbrPartieJouee()+1;
            j.setNbrPartieJouee(a);
            if(listeJoueurPartie.get(i).equals(pseudo)){
                int b = j.getNbrVictoire()+1;
                j.setNbrVictoire(b);
            }
            float c = j.getNbrVictoire()/j.getNbrPartieJouee();
            j.setNbrMoyenVictoire(c);
            int score = TabJoue.get(i).getScore();
            j.setScoreMoyen((j.getScoreMoyen()*(j.getNbrPartieJouee()-1)+score)/j.getNbrPartieJouee());
            j.setMoyenneCvPerdues(j.getMoyenneCvPerdues() + TabJoue.get(i).getNbrCvPerdues());
            j.setMoyenneSuitesGagnees(j.getMoyenneSuitesGagnees() + TabJoue.get(i).getNbrSuitesGagnees());
            daoJoueur.update(j);
        }
        TabJoue.clear();
    }
    
    //Lance le tour suivant
    private void tourSuivant() throws IOException, DAOException{
        tour +=1;
        CulDeChouetteDAOFactory factory = AbstractDAOFactory.getDAOFactory(PersistenceKind.JDBC);
        DAO<Des> daoDes = factory.getDAODes();
        daoDes.create(des);
        if(tour > listeJoueurPartie.size()-1){
            tour = 0;
        }
        JSONArray scoretab = new JSONArray();
        JSONObject liste = new JSONObject();
        for (int i=0; i< TabJoue.size() ; i++) {
            JSONObject joueur = new JSONObject();
            joueur.put("joueur", TabJoue.get(i).getJoueur().getPseudo());
            joueur.put("score", TabJoue.get(i).getScore());
            scoretab.put(joueur);
        }
        liste.put("type", "debutTour");
        liste.put("joueur", listeJoueurPartie.get(tour));
        liste.put("scoretab", scoretab);
        envoiJoueurPartie(liste);
    }
    
}
