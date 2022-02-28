package dk.bec.polonez.reservationsystem.dto;

import lombok.Data;

@Data
public class SignupRequest {

    private String username;

    private String password;

    private String email;

    private String role;
}
