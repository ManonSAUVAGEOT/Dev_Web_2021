package DAO;

/**
 * Factory renvoyant une factory de DAO en fonction du support de persistance choisi
 * @author manon
 */
public class AbstractDAOFactory {

    public static CulDeChouetteDAOFactory getDAOFactory(PersistenceKind type) {
        if (type.equals(PersistenceKind.JDBC)) {
            return new CulDeChouette_JDBC_DAOFactory();
        } else {
            return null;
        }
    }
}
