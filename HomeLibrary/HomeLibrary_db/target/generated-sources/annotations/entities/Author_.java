package entities;

import entities.BibliographicArtifact;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-21T17:13:16", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Author.class)
public class Author_ { 

    public static volatile SingularAttribute<Author, String> country;
    public static volatile ListAttribute<Author, BibliographicArtifact> authoredArtifacts;
    public static volatile SingularAttribute<Author, String> name;
    public static volatile SingularAttribute<Author, Long> authorId;

}