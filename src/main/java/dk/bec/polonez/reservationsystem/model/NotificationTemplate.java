package dk.bec.polonez.reservationsystem.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class NotificationTemplate {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    private String message;
}
