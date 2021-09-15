/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author manon
 */
@Entity
@Table(name = "JOUE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joue.findAll", query = "SELECT j FROM Joue j"),
    @NamedQuery(name = "Joue.findByCodeJoueur", query = "SELECT j FROM Joue j WHERE j.jouePK.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "Joue.findByCodePartie", query = "SELECT j FROM Joue j WHERE j.jouePK.codePartie = :codePartie"),
    @NamedQuery(name = "Joue.findByScore", query = "SELECT j FROM Joue j WHERE j.score = :score"),
    @NamedQuery(name = "Joue.findByNbrSuitesGagnees", query = "SELECT j FROM Joue j WHERE j.nbrSuitesGagnees = :nbrSuitesGagnees"),
    @NamedQuery(name = "Joue.findByNbrCvPerdues", query = "SELECT j FROM Joue j WHERE j.nbrCvPerdues = :nbrCvPerdues")})
public class Joue implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected JouePK jouePK;
    @Column(name = "SCORE")
    private Integer score;
    @Column(name = "NBR_SUITES_GAGNEES")
    private Integer nbrSuitesGagnees;
    @Column(name = "NBR_CV_PERDUES")
    private Integer nbrCvPerdues;
    @JoinColumn(name = "CODE_JOUEUR", referencedColumnName = "CODE_JOUEUR", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Joueur joueur;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Partie partie;

    public Joue() {
    }

    public Joue(JouePK jouePK) {
        this.jouePK = jouePK;
    }

    public Joue(Integer codeJoueur, Integer codePartie) {
        this.jouePK = new JouePK(codeJoueur, codePartie);
    }

    public JouePK getJouePK() {
        return jouePK;
    }

    public void setJouePK(JouePK jouePK) {
        this.jouePK = jouePK;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getNbrSuitesGagnees() {
        return nbrSuitesGagnees;
    }

    public void setNbrSuitesGagnees(Integer nbrSuitesGagnees) {
        this.nbrSuitesGagnees = nbrSuitesGagnees;
    }

    public Integer getNbrCvPerdues() {
        return nbrCvPerdues;
    }

    public void setNbrCvPerdues(Integer nbrCvPerdues) {
        this.nbrCvPerdues = nbrCvPerdues;
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public Partie getPartie() {
        return partie;
    }

    public void setPartie(Partie partie) {
        this.partie = partie;
    }     
        
    public Joue(int codeJoueur, int codePartie, int score, int nbrSuitesGagnees, int nbrCvPerdues, Joueur joueur) {
        this.jouePK.setCodeJoueur(codeJoueur);
        this.jouePK.setCodePartie(codePartie);
        this.score = score;
        this.nbrSuitesGagnees = nbrSuitesGagnees;
        this.nbrCvPerdues = nbrCvPerdues;
        this.joueur = joueur;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (jouePK != null ? jouePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joue)) {
            return false;
        }
        Joue other = (Joue) object;
        if ((this.jouePK == null && other.jouePK != null) || (this.jouePK != null && !this.jouePK.equals(other.jouePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Joue[ jouePK=" + jouePK + " ]";
    }
    
}
