package pojo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojo.Joue;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-05-17T11:28:17")
@StaticMetamodel(Joueur.class)
public class Joueur_ { 

    public static volatile SingularAttribute<Joueur, Float> nbrMoyenVictoire;
    public static volatile SingularAttribute<Joueur, String> ville;
    public static volatile SingularAttribute<Joueur, Float> scoreMoyen;
    public static volatile SingularAttribute<Joueur, Integer> codeJoueur;
    public static volatile SingularAttribute<Joueur, String> sexe;
    public static volatile SingularAttribute<Joueur, Integer> nbrPartieJouee;
    public static volatile SingularAttribute<Joueur, String> motDePasse;
    public static volatile SetAttribute<Joueur, Joue> joueSet;
    public static volatile SingularAttribute<Joueur, Integer> nbrVictoire;
    public static volatile SingularAttribute<Joueur, String> pseudo;
    public static volatile SingularAttribute<Joueur, Float> moyenneSuitesGagnees;
    public static volatile SingularAttribute<Joueur, Integer> age;
    public static volatile SingularAttribute<Joueur, Float> moyenneCvPerdues;

}