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

    private boolean crudPlace;

    private boolean cdPlace;

    private boolean addFeatures;

    private boolean crudUsers;

    private boolean crudRequests;

    private boolean readMyReservations;

    private boolean crudReservation;

    /*  Reservation */

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

}
