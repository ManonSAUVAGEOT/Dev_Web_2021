package pojo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pojo.Des;
import pojo.Joue;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-05-17T11:28:17")
@StaticMetamodel(Partie.class)
public class Partie_ { 

    public static volatile SingularAttribute<Partie, Integer> codePartie;
    public static volatile SetAttribute<Partie, Joue> joueSet;
    public static volatile SetAttribute<Partie, Des> desSet;
    public static volatile SingularAttribute<Partie, Integer> nbrSuites;
    public static volatile SingularAttribute<Partie, Integer> nbrCv;

}