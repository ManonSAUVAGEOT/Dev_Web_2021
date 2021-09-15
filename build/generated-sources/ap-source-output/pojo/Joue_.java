package pojo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojo.JouePK;
import pojo.Joueur;
import pojo.Partie;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-05-17T11:28:17")
@StaticMetamodel(Joue.class)
public class Joue_ { 

    public static volatile SingularAttribute<Joue, Integer> nbrCvPerdues;
    public static volatile SingularAttribute<Joue, Integer> score;
    public static volatile SingularAttribute<Joue, JouePK> jouePK;
    public static volatile SingularAttribute<Joue, Partie> partie;
    public static volatile SingularAttribute<Joue, Joueur> joueur;
    public static volatile SingularAttribute<Joue, Integer> nbrSuitesGagnees;

}