package entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-21T17:13:16", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(BookshelfLocation.class)
public class BookshelfLocation_ { 

    public static volatile SingularAttribute<BookshelfLocation, Integer> shelfNumber;
    public static volatile SingularAttribute<BookshelfLocation, String> section;
    public static volatile SingularAttribute<BookshelfLocation, Long> id;
    public static volatile SingularAttribute<BookshelfLocation, String> position;

}