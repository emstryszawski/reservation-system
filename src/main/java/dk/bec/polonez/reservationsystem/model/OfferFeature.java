package dk.bec.polonez.reservationsystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class OfferFeature {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn
    private Offer offer;

    @ManyToOne
    @JoinColumn
    private Feature feature;
}
