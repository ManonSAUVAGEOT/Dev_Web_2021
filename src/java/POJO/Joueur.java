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
@Table(name = "JOUEUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Joueur.findAll", query = "SELECT j FROM Joueur j"),
    @NamedQuery(name = "Joueur.findByCodeJoueur", query = "SELECT j FROM Joueur j WHERE j.codeJoueur = :codeJoueur"),
    @NamedQuery(name = "Joueur.findByPseudo", query = "SELECT j FROM Joueur j WHERE j.pseudo = :pseudo"),
    @NamedQuery(name = "Joueur.findByMotDePasse", query = "SELECT j FROM Joueur j WHERE j.motDePasse = :motDePasse"),
    @NamedQuery(name = "Joueur.findByAge", query = "SELECT j FROM Joueur j WHERE j.age = :age"),
    @NamedQuery(name = "Joueur.findBySexe", query = "SELECT j FROM Joueur j WHERE j.sexe = :sexe"),
    @NamedQuery(name = "Joueur.findByVille", query = "SELECT j FROM Joueur j WHERE j.ville = :ville"),
    @NamedQuery(name = "Joueur.findByNbrPartieJouee", query = "SELECT j FROM Joueur j WHERE j.nbrPartieJouee = :nbrPartieJouee"),
    @NamedQuery(name = "Joueur.findByNbrVictoire", query = "SELECT j FROM Joueur j WHERE j.nbrVictoire = :nbrVictoire"),
    @NamedQuery(name = "Joueur.findByNbrMoyenVictoire", query = "SELECT j FROM Joueur j WHERE j.nbrMoyenVictoire = :nbrMoyenVictoire"),
    @NamedQuery(name = "Joueur.findByScoreMoyen", query = "SELECT j FROM Joueur j WHERE j.scoreMoyen = :scoreMoyen"),
    @NamedQuery(name = "Joueur.findByMoyenneSuitesGagnees", query = "SELECT j FROM Joueur j WHERE j.moyenneSuitesGagnees = :moyenneSuitesGagnees"),
    @NamedQuery(name = "Joueur.findByMoyenneCvPerdues", query = "SELECT j FROM Joueur j WHERE j.moyenneCvPerdues = :moyenneCvPerdues")})
public class Joueur implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_JOUEUR")
    private Integer codeJoueur;
    @Column(name = "PSEUDO")
    private String pseudo;
    @Column(name = "MOT_DE_PASSE")
    private String motDePasse;
    @Column(name = "AGE")
    private Integer age;
    @Column(name = "SEXE")
    private String sexe;
    @Column(name = "VILLE")
    private String ville;
    @Column(name = "NBR_PARTIE_JOUEE")
    private Integer nbrPartieJouee;
    @Column(name = "NBR_VICTOIRE")
    private Integer nbrVictoire;
    @Column(name = "NBR_MOYEN_VICTOIRE")
    private Float nbrMoyenVictoire;
    @Column(name = "SCORE_MOYEN")
    private Float scoreMoyen;
    @Column(name = "MOYENNE_SUITES_GAGNEES")
    private Float moyenneSuitesGagnees;
    @Column(name = "MOYENNE_CV_PERDUES")
    private Float moyenneCvPerdues;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "joueur")
    private Set<Joue> joueSet;

    public Joueur() {
    }

    public Joueur(Integer codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public Integer getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(Integer codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public Integer getNbrPartieJouee() {
        return nbrPartieJouee;
    }

    public void setNbrPartieJouee(Integer nbrPartieJouee) {
        this.nbrPartieJouee = nbrPartieJouee;
    }

    public Integer getNbrVictoire() {
        return nbrVictoire;
    }

    public void setNbrVictoire(Integer nbrVictoire) {
        this.nbrVictoire = nbrVictoire;
    }

    public Float getNbrMoyenVictoire() {
        return nbrMoyenVictoire;
    }

    public void setNbrMoyenVictoire(Float nbrMoyenVictoire) {
        this.nbrMoyenVictoire = nbrMoyenVictoire;
    }

    public Float getScoreMoyen() {
        return scoreMoyen;
    }

    public void setScoreMoyen(Float scoreMoyen) {
        this.scoreMoyen = scoreMoyen;
    }

    public Float getMoyenneSuitesGagnees() {
        return moyenneSuitesGagnees;
    }

    public void setMoyenneSuitesGagnees(Float moyenneSuitesGagnees) {
        this.moyenneSuitesGagnees = moyenneSuitesGagnees;
    }

    public Float getMoyenneCvPerdues() {
        return moyenneCvPerdues;
    }

    public void setMoyenneCvPerdues(Float moyenneCvPerdues) {
        this.moyenneCvPerdues = moyenneCvPerdues;
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
        hash += (codeJoueur != null ? codeJoueur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Joueur)) {
            return false;
        }
        Joueur other = (Joueur) object;
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Joueur[ codeJoueur=" + codeJoueur + " ]";
    }

    public void getScoreMoyen(int aInt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
