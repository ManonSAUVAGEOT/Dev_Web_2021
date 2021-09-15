/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author manon
 */
@Embeddable
public class JouePK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODE_JOUEUR")
    private Integer codeJoueur;
    @Basic(optional = false)
    @Column(name = "CODE_PARTIE")
    private Integer codePartie;

    public JouePK() {
    }

    public JouePK(Integer codeJoueur, Integer codePartie) {
        this.codeJoueur = codeJoueur;
        this.codePartie = codePartie;
    }

    public Integer getCodeJoueur() {
        return codeJoueur;
    }

    public void setCodeJoueur(Integer codeJoueur) {
        this.codeJoueur = codeJoueur;
    }

    public Integer getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(Integer codePartie) {
        this.codePartie = codePartie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeJoueur != null ? codeJoueur.hashCode() : 0);
        hash += (codePartie != null ? codePartie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof JouePK)) {
            return false;
        }
        JouePK other = (JouePK) object;
        if ((this.codeJoueur == null && other.codeJoueur != null) || (this.codeJoueur != null && !this.codeJoueur.equals(other.codeJoueur))) {
            return false;
        }
        if ((this.codePartie == null && other.codePartie != null) || (this.codePartie != null && !this.codePartie.equals(other.codePartie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.JouePK[ codeJoueur=" + codeJoueur + ", codePartie=" + codePartie + " ]";
    }
    
}
