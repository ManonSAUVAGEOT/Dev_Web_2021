/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pojo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
@Table(name = "DES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Des.findAll", query = "SELECT d FROM Des d"),
    @NamedQuery(name = "Des.findByCodeDes", query = "SELECT d FROM Des d WHERE d.codeDes = :codeDes"),
    @NamedQuery(name = "Des.findByLanceur", query = "SELECT d FROM Des d WHERE d.lanceur = :lanceur"),
    @NamedQuery(name = "Des.findByInteraction", query = "SELECT d FROM Des d WHERE d.interaction = :interaction"),
    @NamedQuery(name = "Des.findByDe1", query = "SELECT d FROM Des d WHERE d.de1 = :de1"),
    @NamedQuery(name = "Des.findByDe2", query = "SELECT d FROM Des d WHERE d.de2 = :de2"),
    @NamedQuery(name = "Des.findByDe3", query = "SELECT d FROM Des d WHERE d.de3 = :de3")})
public class Des implements Serializable {

    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
    @Basic(optional = false)
    @Column(name = "CODE_DES")
    private Integer codeDes;
    @Column(name = "LANCEUR")
    private String lanceur;
    @Column(name = "INTERACTION")
    private String interaction;
    @Column(name = "DE_1")
    private Integer de1;
    @Column(name = "DE_2")
    private Integer de2;
    @Column(name = "DE_3")
    private Integer de3;
    @JoinColumn(name = "CODE_PARTIE", referencedColumnName = "CODE_PARTIE")
    @ManyToOne(optional = false)
    private Partie codePartie;

    public Des() {
    }

    public Des(Integer codeDes) {
        this.codeDes = codeDes;
    }

    public Integer getCodeDes() {
        return codeDes;
    }

    public void setCodeDes(Integer codeDes) {
        this.codeDes = codeDes;
    }

    public String getLanceur() {
        return lanceur;
    }

    public void setLanceur(String lanceur) {
        this.lanceur = lanceur;
    }

    public String getInteraction() {
        return interaction;
    }

    public void setInteraction(String interaction) {
        this.interaction = interaction;
    }

    public Integer getDe1() {
        return de1;
    }

    public void setDe1(Integer de1) {
        this.de1 = de1;
    }

    public Integer getDe2() {
        return de2;
    }

    public void setDe2(Integer de2) {
        this.de2 = de2;
    }

    public Integer getDe3() {
        return de3;
    }

    public void setDe3(Integer de3) {
        this.de3 = de3;
    }

    public Partie getCodePartie() {
        return codePartie;
    }

    public void setCodePartie(Partie codePartie) {
        this.codePartie = codePartie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeDes != null ? codeDes.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Des)) {
            return false;
        }
        Des other = (Des) object;
        if ((this.codeDes == null && other.codeDes != null) || (this.codeDes != null && !this.codeDes.equals(other.codeDes))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pojo.Des[ codeDes=" + codeDes + " ]";
    }
    
}
