package dk.bec.polonez.reservationsystem.model;

import lombok.Data;

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

    private boolean crudPlace;

    private boolean cdPlace;

    private boolean addFeatures;

    private boolean crudUsers;

    private boolean crudRequests;

    private boolean readMyReservations;

    private boolean crudReservation;
}
