package dk.bec.polonez.reservationsystem.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;

    private String username;

    private String name;

    private String email;

    private String roleName;

    private boolean blocked;
}
