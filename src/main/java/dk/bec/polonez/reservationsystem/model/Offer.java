package dk.bec.polonez.reservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Offer {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToOne
    @JoinColumn
    private User owner;

    private double pricePerUnit;

    private String unitDuration;

    private String description;
}
