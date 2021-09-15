/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author manon
 */
@Entity
@Table(name = "PARTIE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Partie.findAll", query = "SELECT p FROM Partie p"),
    @NamedQuery(name = "Partie.findByCodePartie", query = "SELECT p FROM Partie p WHERE p.codePartie = :codePartie"),
    @NamedQuery(name = "Partie.findByNbrSuites", query = "SELECT p FROM Partie p WHERE p.nbrSuites = :nbrSuites"),
    @NamedQuery(name = "Partie.findByNbrCv", query = "SELECT p FROM Partie p WHERE p.nbrCv = :nbrCv")})
public class Partie implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_PARTIE")
    private Integer codePartie;
    @Column(name = "NBR_SUITES")
    private Integer nbrSuites;
    @Column(name = "NBR_CV")
    private Integer nbrCv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codePartie")
    private Set<Des> desSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "partie")
    private Set<Joue> joueSet;

    public Partie() {
    }

    public Partie(Integer codePartie) {
        this.codePartie = codePartie;
    }

    public Integer getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(Integer codePartie) {
        this.codePartie = codePartie;
    }

    public Integer getNbrSuites() {
        return nbrSuites;
    }

    public void setNbrSuites(Integer nbrSuites) {
        this.nbrSuites = nbrSuites;
    }

    public Integer getNbrCv() {
        return nbrCv;
    }

    public void setNbrCv(Integer nbrCv) {
        this.nbrCv = nbrCv;
    }

    @XmlTransient
    public Set<Des> getDesSet() {
        return desSet;
    }

    public void setDesSet(Set<Des> desSet) {
        this.desSet = desSet;
    }

    @XmlTransient
    public Set<Joue> getJoueSet() {
        return joueSet;
    }

    public void setJoueSet(Set<Joue> joueSet) {
        this.joueSet = joueSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Partie)) {
            return false;
        }
        Partie other = (Partie) object;
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Partie[ codePartie=" + codePartie + " ]";
    }
    
}
