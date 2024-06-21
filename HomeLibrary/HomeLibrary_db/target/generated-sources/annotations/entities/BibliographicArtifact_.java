package entities;

import entities.Author;
import entities.BookshelfLocation;
import entities.Genre;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-21T17:13:16", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(BibliographicArtifact.class)
public abstract class BibliographicArtifact_ { 

    public static volatile SingularAttribute<BibliographicArtifact, Integer> year;
    public static volatile ListAttribute<BibliographicArtifact, Genre> genres;
    public static volatile SingularAttribute<BibliographicArtifact, Integer> availableCopies;
    public static volatile SingularAttribute<BibliographicArtifact, String> language;
    public static volatile SingularAttribute<BibliographicArtifact, BookshelfLocation> location;
    public static volatile SingularAttribute<BibliographicArtifact, Long> id;
    public static volatile SingularAttribute<BibliographicArtifact, String> title;
    public static volatile SingularAttribute<BibliographicArtifact, String> type;
    public static volatile SingularAttribute<BibliographicArtifact, Boolean> isCensored;
    public static volatile ListAttribute<BibliographicArtifact, Author> authors;

}