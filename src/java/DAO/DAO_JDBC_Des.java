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
 * DAO pour la classe/table Des avec implémentation en JDBC
 * @author manon
 */
public class DAO_JDBC_Des extends DAO<Des> {

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
    public Des find(int id) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM Des WHERE CODE_DES =" + id);
            if (res.next()) {
                Des d = new Des();
                d.setCodeDes(res.getInt(1));
                d.setLanceur(res.getString(2));
                d.setInteraction(res.getString(3));
                d.setDe1(res.getInt(4));
                d.setDe2(res.getInt(5));
                d.setDe3(res.getInt(6));
                return d;
            } else {
                throw new DAOException("Le lancé de dés d'identifiant " + id + " n'existe pas");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public Des Connextion(String pseudo, String mdp) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo =" + pseudo);
            if (res.next()) {
                Des j = new Des();

                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
        @Override
    public Des trouveJoueur(String pseudo) throws DAOException{
        try {
            Statement req = connection.createStatement();
            //System.out.println("avant requête");
            ResultSet res = req.executeQuery("SELECT * FROM Joueur WHERE Pseudo = '" + pseudo + "'");
            //System.out.println("après requête");
            if (res.next()) {
                Des j = new Des();

                return j;
            } else {
                throw new DAOException("Le joueur " + pseudo + " n'existe pas ou le mot de passe est faux");
            }
        } catch (Exception e) {
            throw new DAOException("Problème technique test (" + e.getMessage() + ")");
        }
    }

    @Override
    public void create(Des d) throws DAOException {
        try {
            Statement req = connection.createStatement();
            ResultSet res = req.executeQuery("SELECT MAX(CODE_DES) FROM Des");
            res.next();
            int codeDes = res.getInt(1);
            codeDes++;
            d.setCodeDes(codeDes);

            PreparedStatement reqParam = connection.prepareStatement("INSERT INTO Des VALUES (?,?,?,?,?,?,?)");
            reqParam.setInt(1, d.getCodeDes());
            reqParam.setString(2, d.getLanceur());
            reqParam.setString(3, d.getInteraction());
            reqParam.setInt(4, d.getDe1());
            reqParam.setInt(5, d.getDe2());
            reqParam.setInt(6, d.getDe3());
            reqParam.setInt(7,d.getCodePartie().getCodePartie());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }

    @Override
    public void update(Des d) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("UPDATE Des SET lanceur = ?, interaction = ?, de_1 =?, de_2 =?, de_3=? WHERE CODE_DES = ?");
            reqParam.setString(1, d.getLanceur());
            reqParam.setString(2, d.getInteraction());
            reqParam.setInt(3, d.getDe1());
            reqParam.setInt(4, d.getDe2());
            reqParam.setInt(5, d.getDe3());
            reqParam.setInt(6, d.getCodeDes());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }
    
    @Override
    public void delete(Des d) throws DAOException {
        try {
            PreparedStatement reqParam = connection.prepareStatement("DELETE FROM Des WHERE CODE_DES = ?");
            reqParam.setInt(1, d.getCodeDes());
            int nb = reqParam.executeUpdate();
        } catch (Exception e) {
            throw new DAOException("Problème technique (" + e.getMessage() + ")");
        }
    }

    public DAO_JDBC_Des() throws DAOException {
        this.connection = SQLConnection.getConnection();
    }
}
