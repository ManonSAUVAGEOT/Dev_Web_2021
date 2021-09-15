package DAO;

import pojo.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO pour la classe/table Joue avec implémentation en JDBC
 * @author manon
 */
public class DAO_JDBC_Joue extends DAO<Joue> {

    private Connection connection = null;
    
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
        try{
            System.out.println("getStat");
            Statement req = connection.createStatement();
            System.out.println(j.getCodeJoueur());
            int i = j.getCodeJoueur();
            //System.out.println(req.executeQuery("SELECT SUM(SCORE), COUNT(*), SUM(NBR_SUITES_GAGNEES), SUM(NBR_CV_PERDUES), SUM(NBR_SUITES), SUM(NBR_CV) FROM Joue j, Partie p WHERE j.Code_partie = p.CODE_PARTIE AND j.Code_joueur = " + i + " GROUP BY j.Code_joueur;"));
            ResultSet res = req.executeQuery("SELECT SUM(SCORE), COUNT(*), SUM(NBR_SUITES_GAGNEES), SUM(NBR_CV_PERDUES), SUM(NBR_SUITES), SUM(NBR_CV) FROM Joue j, Partie p WHERE j.Code_partie = p.CODE_PARTIE AND j.Code_joueur = " + i + " GROUP BY j.Code_joueur;");
            if(res.next()) {
                System.out.println("info");
                System.out.println(res.getInt(1)/res.getInt(2));
                j.getScoreMoyen(res.getInt(1)/res.getInt(2));
                System.out.println("test");
                j.setMoyenneSuitesGagnees((float) res.getInt(3)/res.getInt(5));
                System.out.println("float");
                j.setMoyenneCvPerdues((float) res.getInt(4)/res.getInt(6));
            } else {
                throw new DAOException("test");
            }
            System.out.println("getStat fin");
        }catch (Exception e) {
            try {
                throw new DAOException("Problème technique (" + e.getMessage() + ")");
            } catch (DAOException ex) {
                Logger.getLogger(DAO_JDBC_Joue.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @Override
    public Joue find(int id) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM Joue WHERE Code_joueur =" + id);
            if (res.next()) {
                Joue j = new Joue();
                j.setJouePK(new JouePK(res.getInt(1),res.getInt(2)));
                j.setScore(res.getInt(3));
                j.setNbrSuitesGagnees(res.getInt(4));
                j.setNbrCvPerdues(res.getInt(5));
                return j;
            } else {
                throw new DAOException("Pas d'identifiant " + id );
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
     @Override
    public Joue Connextion(String pseudo, String mdp) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo =" + pseudo);
            if (res.next()) {
                Joue j = new Joue();

                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
        @Override
    public Joue trouveJoueur(String pseudo) throws DAOException{
        try {
            Statement req = connection.createStatement();
            //System.out.println("avant requête");
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo = '" + pseudo + "'");
            //System.out.println("après requête");
            if (res.next()) {
                Joue j = new Joue();

                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique test (" + e.getMessage() + ")");
        }
    }

    @Override
    public void create(Joue j) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("INSERT INTO Joue VALUES (?,?,?,?,?)");
            reqParam.setInt(1, j.getJouePK().getCodeJoueur());
            reqParam.setInt(2, j.getJouePK().getCodePartie());
            reqParam.setInt(3, j.getScore());
            reqParam.setInt(4, j.getNbrSuitesGagnees());
            reqParam.setInt(5, j.getNbrCvPerdues());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(Joue j) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("UPDATE Joue SET SCORE = ?, NBR_SUITES_GAGNEES = ?, NBR_CV_PERDUES = ? WHERE Code_joueur = ? and Code_partie = ?");
            reqParam.setInt(1, j.getScore());
            reqParam.setInt(2, j.getNbrSuitesGagnees());
            reqParam.setInt(3, j.getNbrCvPerdues());
            reqParam.setInt(4, j.getJouePK().getCodeJoueur());
            reqParam.setInt(5, j.getJouePK().getCodePartie());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problèle technique (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public void delete(Joue j) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("DELETE FROM Joue WHERE Code_Joueur = ? and Code_partie = ?");
            reqParam.setInt(1, j.getJouePK().getCodeJoueur());
            reqParam.setInt(2, j.getJouePK().getCodePartie());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }

    public DAO_JDBC_Joue() throws DAOException {
        this.connection = SQLConnection.getConnection();
    }
}

