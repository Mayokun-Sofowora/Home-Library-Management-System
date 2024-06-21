package entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-21T17:13:16", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Book.class)
public class Book_ extends BibliographicArtifact_ {

    public static volatile SingularAttribute<Book, Integer> ISBN;
    public static volatile SingularAttribute<Book, Integer> totalCopies;
    public static volatile ListAttribute<Book, String> reviews;

}