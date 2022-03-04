package dk.bec.polonez.reservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    /** Offer Privileges */
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

    /** Reservation Privileges */

    @Accessors(fluent = true)
    private boolean hasReservationMineOnlyPrivilege;

    @Accessors(fluent = true)
    private boolean hasReservationReadPrivilege;

    @Accessors(fluent = true)
    private boolean hasReservationCreatePrivilege;

    @Accessors(fluent = true)
    private boolean hasReservationDeletePrivilege;

    @Accessors(fluent = true)
    private boolean hasReservationUpdatePrivilege;

    @Accessors(fluent = true)
    private boolean hasReservationMyOffersPrivilege;

    @Accessors(fluent = true)
    private boolean hasReservationCancelPrivilege;

    /** Discount Privileges */

    @Accessors(fluent = true)
    private boolean hasDiscountCreationPrivilege;

    @Accessors(fluent = true)
    private boolean hasDiscountReadPrivilege;

    @Accessors(fluent = true)
    private boolean hasDiscountDeletePrivilege;

    @Accessors(fluent = true)
    private boolean hasDiscountUpdatePrivilege;
}
