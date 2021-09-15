package DAO;

import pojo.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * DAO pour la classe/table Joueur avec implémentation en JDBC
 * @author manon
 */
public class DAO_JDBC_Joueur extends DAO<Joueur> {

    public Connection connection = null;
    
    @Override
    public Set<Joue> getJoueJoueur(Joueur j) throws SQLException {
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM Joue WHERE Code_joueur=" + j.getCodeJoueur());
        System.out.println(" Les parties du joueur " + j.getPseudo());
        HashSet<Joue> joue = new HashSet<>();
        Joue jj;
        while (res.next()) {
            System.out.println(" -> " + res.getInt(2));
            jj = new Joue();
            jj.setJouePK(new JouePK(res.getInt(1), res.getInt(2)));
            jj.setScore(res.getInt(3));
            jj.setNbrSuitesGagnees(res.getInt(4));
            jj.setNbrCvPerdues(res.getInt(5));
            jj.setJoueur(j);
            joue.add(jj);
        }
        return joue;
    }
    
    @Override
    public void getStat(Joueur j) throws SQLException {
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("select sum(SCORE), count(*), sum(NBR_SUITES_GAGNEES), sum(NBR_CV_PERDUES), sum(NBR_SUITES), sum(NBR_CV)\n" +
                                    "from Joue j, Partie p where j.Code_partie=p.Code_partie and j.Code_joueur =" + j.getCodeJoueur() + " group by code_joueur;");
        while (res.next()) {
            j.getScoreMoyen(res.getInt(1)/res.getInt(2));
            j.setMoyenneSuitesGagnees((float) res.getInt(3)/res.getInt(5));
            j.setMoyenneCvPerdues((float) res.getInt(4)/res.getInt(6));
        }
    }
 
    @Override
    public Joueur find(int id) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Code_joueur =" + id);
            if (res.next()) {
                Joueur j = new Joueur();
                j.setCodeJoueur(res.getInt(1));
                j.setPseudo(res.getString(2));
                j.setMotDePasse(res.getString(3));
                j.setAge(res.getInt(4));
                j.setSexe(res.getString(5));
                j.setVille(res.getString(6));
                j.setNbrPartieJouee(res.getInt(7));
                j.setNbrVictoire(res.getInt(8));
                j.setNbrMoyenVictoire(res.getFloat(9));
                j.setScoreMoyen(res.getFloat(10));
                j.setMoyenneSuitesGagnees(res.getFloat(11));
                j.setMoyenneCvPerdues(res.getFloat(12));
                return j;
            } else {
                throw new DAOException("Le joueur d'identifiant " + id + " n'existe pas");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public Joueur trouveJoueur(String pseudo) throws DAOException{
        try {
            Statement req = connection.createStatement();
            //System.out.println("avant requête");
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo = '" + pseudo + "'");
            //System.out.println("après requête");
            if (res.next()) {
                Joueur j = new Joueur();
                j.setCodeJoueur(res.getInt(1));
                j.setPseudo(res.getString(2));
                j.setMotDePasse(res.getString(3));
                j.setAge(res.getInt(4));
                j.setSexe(res.getString(5));
                j.setVille(res.getString(6));
                j.setNbrPartieJouee(res.getInt(7));
                j.setNbrVictoire(res.getInt(8));
                j.setNbrMoyenVictoire(res.getFloat(9));
                j.setScoreMoyen(res.getFloat(10));
                j.setMoyenneSuitesGagnees(res.getFloat(11));
                j.setMoyenneCvPerdues(res.getFloat(12));
                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique test (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public Joueur Connextion(String pseudo, String mdp) throws DAOException {
        try {
            Statement req = connection.createStatement();
            //System.out.println("avant requête");
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo = '" + pseudo + "' AND Mot_De_Passe = '" + mdp + "'");
            //System.out.println("après requête");
            if (res.next()) {
                Joueur j = new Joueur();
                j.setCodeJoueur(res.getInt(1));
                j.setPseudo(res.getString(2));
                j.setMotDePasse(res.getString(3));
                j.setAge(res.getInt(4));
                j.setSexe(res.getString(5));
                j.setVille(res.getString(6));
                j.setNbrPartieJouee(res.getInt(7));
                j.setNbrVictoire(res.getInt(8));
                j.setNbrMoyenVictoire(res.getFloat(9));
                j.setScoreMoyen(res.getFloat(10));
                j.setMoyenneSuitesGagnees(res.getFloat(11));
                j.setMoyenneCvPerdues(res.getFloat(12));
                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique test (" + e.getMessage() + ")");
        }
    }

    @Override
    public void create(Joueur j) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT MAX(Code_joueur) FROM Joueur");
            res.next();
            int codeJoueur = res.getInt(1);
            codeJoueur++;
            j.setCodeJoueur(codeJoueur);

            req = connection.createStatement();
            int nb = req.executeUpdate("INSERT INTO Joueur VALUES (" + codeJoueur + " , '" + j.getPseudo() + "' , '" + j.getMotDePasse() + "' , "+ j.getAge()+ " , '" + j.getSexe() + "' , '"+ j.getVille()+ "' , " + "0,0,0,0,0,0)");
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(Joueur j) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("UPDATE Joueur SET Mot_de_passe = ?, Age = ?,Sexe = ?, Ville = ?, Nbr_partie_jouee = ?, Nbr_victoire = ?, Nbr_moyen_victoire = ?, Score_moyen = ?, Moyenne_suites_gagnees = ?, Moyenne_cv_perdues = ? WHERE Code_joueur = ?");
            reqParam.setString(1, j.getMotDePasse());
            reqParam.setInt(2, j.getAge());
            reqParam.setString(3, j.getSexe());
            reqParam.setString(4, j.getVille());
            reqParam.setInt(5, j.getNbrPartieJouee());
            reqParam.setInt(6, j.getNbrVictoire());
            reqParam.setFloat(7, j.getNbrMoyenVictoire());
            reqParam.setFloat(8, j.getScoreMoyen());
            reqParam.setFloat(9, j.getMoyenneSuitesGagnees());
            reqParam.setFloat(10, j.getMoyenneCvPerdues());
            reqParam.setInt(11, j.getCodeJoueur());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problèle technique (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public void delete(Joueur j) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("DELETE FROM Joue WHERE Code_joueur = ?");
            reqParam.setInt(1, j.getCodeJoueur());
            int nb = reqParam.executeUpdate();

            reqParam = connection.prepareStatement("DELETE FROM Joueur WHERE Code_joueur = ?");
            reqParam.setInt(1, j.getCodeJoueur());
            nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }

    public DAO_JDBC_Joueur() throws DAOException {
        this.connection = SQLConnection.getConnection();
    }
    
}
