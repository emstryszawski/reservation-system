package dk.bec.polonez.reservationsystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue
    private long id;

    private long createdAt;

    private long dateFrom;

    private long dateTo;

    private String status;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Offer offer;
}
