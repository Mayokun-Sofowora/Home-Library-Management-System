package entities;

import entities.Loan;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-06-15T19:32:57", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Member.class)
public class Member_ { 

    public static volatile ListAttribute<Member, Loan> loans;
    public static volatile SingularAttribute<Member, String> name;
    public static volatile SingularAttribute<Member, String> email;
    public static volatile SingularAttribute<Member, Long> memberId;

}