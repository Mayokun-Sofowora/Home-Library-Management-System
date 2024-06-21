package entities;

import entities.BibliographicArtifact;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-21T17:13:16", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Genre.class)
public class Genre_ { 

    public static volatile SingularAttribute<Genre, Long> genreId;
    public static volatile SingularAttribute<Genre, String> name;
    public static volatile ListAttribute<Genre, BibliographicArtifact> genreArtifacts;

}