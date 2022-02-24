package dk.bec.polonez.reservationsystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Offer {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn
    private User owner;

    private String description;
}
