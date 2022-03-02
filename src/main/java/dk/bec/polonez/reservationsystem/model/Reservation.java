package dk.bec.polonez.reservationsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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

    @Override
    public String toString() {
        // example info
        return offer.getName() + ", w dniu" + new Date(dateFrom) + ", nr " + id;
    }
}
