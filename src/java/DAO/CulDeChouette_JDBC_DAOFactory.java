package DAO;

import pojo.*;

/**
 * Fabrique concrète de DAO pour le schéma relationnel joueurs avec une implémentation en JDBC.
 * @author manon
 */
public class CulDeChouette_JDBC_DAOFactory extends CulDeChouetteDAOFactory {
    
    private DAO_JDBC_Partie daoPartie = null;
    
    private DAO_JDBC_Joueur daoJoueur = null;
    
    private DAO_JDBC_Joue daoJoue = null;
    
    private DAO_JDBC_Des daoDes = null;
        
    @Override
    public DAO<Joueur> getDAOJoueur() throws DAOException {
        if (daoJoueur == null) daoJoueur = new DAO_JDBC_Joueur();
        return daoJoueur;
    }

    @Override
    public DAO<Partie> getDAOPartie() throws DAOException {
        if (daoPartie == null) daoPartie = new DAO_JDBC_Partie();
        return daoPartie;
    }
   
    @Override
    public DAO<Joue> getDAOJoue() throws DAOException {
        if (daoJoue == null) daoJoue = new DAO_JDBC_Joue();
        return daoJoue;
    }
    
    @Override
    public DAO<Des> getDAODes() throws DAOException {
        if (daoDes == null) daoDes = new DAO_JDBC_Des();
        return daoDes;
    }
}
