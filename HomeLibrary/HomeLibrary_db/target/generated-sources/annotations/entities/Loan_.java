package entities;

import entities.BibliographicArtifact;
import entities.Member;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-15T19:32:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Loan.class)
public class Loan_ { 

    public static volatile SingularAttribute<Loan, BibliographicArtifact> item;
    public static volatile SingularAttribute<Loan, Date> returnDate;
    public static volatile SingularAttribute<Loan, Member> borrower;
    public static volatile SingularAttribute<Loan, Date> loanDate;
    public static volatile SingularAttribute<Loan, Long> loanId;

}