package dk.bec.polonez.reservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Discount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String code;

    private long valueInPercentage;

    @ManyToOne
    @JoinColumn
    private Offer offer;

    private long dateFrom;

    private long dateTo;

}
