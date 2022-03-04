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


    public boolean collides(Reservation otherReservation) {

        Reservation currentReservation = this;

        if(currentReservation.getDateTo() >= otherReservation.getDateFrom() && currentReservation.getDateTo() <= otherReservation.getDateTo())
            return true;

        if(currentReservation.getDateFrom() >= otherReservation.getDateFrom() && currentReservation.getDateFrom() <= otherReservation.getDateTo())
            return true;

        return false;
    }
}
