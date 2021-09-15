package DAO;

import pojo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

/**
 * DAO pour la classe/table Partie avec implémentation en JDBC
 * @author manon
 */
public class DAO_JDBC_Partie extends DAO<Partie> {

    private Connection connection = null;

    public Set<Des> getDesPartie(Partie p) throws SQLException {
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM Des WHERE Code_partie=" + p.getCodePartie());
        System.out.println(" Les lancés de dés de la partie " + p.getCodePartie());
        HashSet<Des> d = new HashSet<>();
        Des des;
        while (res.next()) {
            System.out.println(" -> " + res.getString(2));
            des = new Des();
            des.setCodeDes(res.getInt(1));
            des.setLanceur(res.getString(2));
            des.setInteraction(res.getString(3));
            des.setDe1(res.getInt(4));
            des.setDe2(res.getInt(5));
            des.setDe3(res.getInt(6));
            des.setCodePartie(p);
            d.add(des);
        }
        return d;
    }
    
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
    
    
    public Set<Joue> getJouePartie(Partie p) throws SQLException {
        Statement req = connection.createStatement();
        ResultSet res = req.executeQuery("SELECT * FROM Joue WHERE Code_partie=" + p.getCodePartie());
        System.out.println(" Les joueurs de la partie " + p.getCodePartie());
        HashSet<Joue> joue = new HashSet<>();
        Joue jj;
        while (res.next()) {
            System.out.println(" -> " + res.getInt(1));
            jj = new Joue();
            jj.setJouePK(new JouePK(res.getInt(1), res.getInt(2)));
            jj.setScore(res.getInt(3));
            jj.setNbrSuitesGagnees(res.getInt(4));
            jj.setNbrCvPerdues(res.getInt(5));
            jj.setPartie(p);
            joue.add(jj);
        }
        return joue;
    }

    @Override
    public Partie find(int id) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM Partie WHERE CODE_PARTIE =" + id);
            if (res.next()) {
                Partie p = new Partie();
                p.setCodePartie(res.getInt(1));
                p.setNbrSuites(res.getInt(2));
                p.setNbrCv(res.getInt(3));
                return p;
            } else {
                throw new DAOException("La partie d'identifiant " + id + " n'existe pas");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public Partie Connextion(String pseudo, String mdp) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo =" + pseudo);
            if (res.next()) {
                Partie j = new Partie();

                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
        @Override
    public Partie trouveJoueur(String pseudo) throws DAOException{
        try {
            Statement req = connection.createStatement();
            //System.out.println("avant requête");
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo = '" + pseudo + "'");
            //System.out.println("après requête");
            if (res.next()) {
                Partie j = new Partie();

                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique test (" + e.getMessage() + ")");
        }
    }

    @Override
    public void create(Partie p) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT MAX(CODE_PARTIE) FROM Partie");
            res.next();
            int codePartie = res.getInt(1);
            codePartie++;
            p.setCodePartie(codePartie);

            req = connection.createStatement();
            int nb = req.executeUpdate("INSERT INTO Partie VALUES (" + codePartie + ",0,0)");
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
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
    public void update(Partie p) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("UPDATE Partie SET NBR_SUITES = ?, NBR_CV = ? WHERE CODE_PARTIE = ?");
            reqParam.setInt(1, p.getNbrSuites());
            reqParam.setInt(2, p.getNbrCv());
            reqParam.setInt(3, p.getCodePartie());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problèle technique (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public void delete(Partie p) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("DELETE FROM Joue WHERE CODE_PARTIE = ?");
            reqParam.setInt(1, p.getCodePartie());
            int nb = reqParam.executeUpdate();
            
            reqParam = connection.prepareStatement("DELETE FROM Des WHERE CODE_PARTIE = ?");
            reqParam.setInt(1, p.getCodePartie());
            nb = reqParam.executeUpdate();
            
            reqParam = connection.prepareStatement("DELETE FROM Partie WHERE CODE_PARTIE = ?");
            reqParam.setInt(1, p.getCodePartie());
            nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }

    public DAO_JDBC_Partie() throws DAOException {
        this.connection = SQLConnection.getConnection();
    }
}
