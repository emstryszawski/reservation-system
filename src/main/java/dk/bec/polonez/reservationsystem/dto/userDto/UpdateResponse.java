package dk.bec.polonez.reservationsystem.dto.userDto;

import lombok.Data;

@Data
public class UpdateResponse {

    private Long id;

    private String username;

    private String name;

    private String email;

    private String roleName;

    private boolean isBlocked;
}
