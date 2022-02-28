package dk.bec.polonez.reservationsystem.dto.userDto;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;

    private String password;
}
