package DAO;

import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Classe Java sérialisée en XML pour stocker la configuration JDBC
 * @author manon
 */
@XmlRootElement
@XmlType(propOrder = {"url", "user", "password"})
public class ConfigJDBC {

    /**
     * URL de connexion JDBC au SGBD
     */
    private String url;
    
    /**
     * Login de l'utilisateur
     */
    private String user;
    
    /**
     * Mot de passe de l'utilisateur
     */
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ConfigJDBC(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }
    
    public ConfigJDBC() {
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.url);
        hash = 61 * hash + Objects.hashCode(this.user);
        hash = 61 * hash + Objects.hashCode(this.password);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConfigJDBC other = (ConfigJDBC) obj;
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return true;
    }
}
