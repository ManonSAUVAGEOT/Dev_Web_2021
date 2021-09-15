package DAO;

import pojo.*;

/**
 * Fabrique abstraite de DAO pour le schéma joueurs
 * @author manon
 */
public abstract class CulDeChouetteDAOFactory {
    
    /**
     * @return le DAO pour la classe/table Joueur
     * @throws DAOException en cas de problème
     */
    public abstract DAO<Joueur> getDAOJoueur() throws DAOException;
    
    /**
     * @return le DAO pour la classe/table Partie
     * @throws DAOException en cas de problème
     */
    public abstract DAO<Partie> getDAOPartie() throws DAOException;
    
    /**
     * @return le DAO pour la classe/table Des
     * @throws DAOException en cas de problème
     */
    public abstract DAO<Des> getDAODes() throws DAOException;
    
    /**
     * @return le DAO pour la classe/table Joue
     * @throws DAOException en cas de problème
     */
    public abstract DAO<Joue> getDAOJoue() throws DAOException;
}
