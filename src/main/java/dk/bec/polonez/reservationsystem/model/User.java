package dk.bec.polonez.reservationsystem.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String name;

    private String email;

    @ManyToOne
    @JoinColumn
    private Role role;

    private boolean blocked;

}
