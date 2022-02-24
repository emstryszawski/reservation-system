package dk.bec.polonez.reservationsystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Discount {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String code;

    private int value;

    @ManyToOne
    @JoinColumn
    private Offer offer;

    private long dateFrom;

    private long dateTo;

}
