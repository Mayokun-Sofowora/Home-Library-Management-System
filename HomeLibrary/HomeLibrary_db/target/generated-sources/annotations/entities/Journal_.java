package entities;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-15T19:32:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Journal.class)
public class Journal_ extends BibliographicArtifact_ {

    public static volatile SingularAttribute<Journal, Integer> volume;
    public static volatile SingularAttribute<Journal, Integer> issue;
    public static volatile SingularAttribute<Journal, Integer> journalId;
    public static volatile SingularAttribute<Journal, String> ISSN;

}