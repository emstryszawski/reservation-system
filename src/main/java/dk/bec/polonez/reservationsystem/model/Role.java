package dk.bec.polonez.reservationsystem.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    /** Offer CRUD Privileges */
    @Accessors(fluent = true)
    private boolean hasOfferCreatePrivilege;

    @Accessors(fluent = true)
    private boolean hasOfferReadPrivilege;

    @Accessors(fluent = true)
    private boolean hasOfferUpdatePrivilege;

    @Accessors(fluent = true)
    private boolean hasOfferDeletePrivilege;

    @Accessors(fluent = true)
    private boolean hasOfferUpdateOthersOfferPrivilege;

    @Accessors(fluent = true)
    public boolean hasOfferDeleteOthersOfferPrivilege;

    /**Discount privileges*/
    @Accessors(fluent = true)
    private boolean hasDiscountCreationPrivilege;
    @Accessors(fluent = true)
    private boolean hasDiscountReadPrivilege;
    @Accessors(fluent = true)
    private boolean hasDiscountDeletePrivilege;
    @Accessors(fluent = true)
    private boolean hasDiscountUpdatePrivilege;
}
