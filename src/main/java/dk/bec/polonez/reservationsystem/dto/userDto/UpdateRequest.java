package dk.bec.polonez.reservationsystem.dto.userDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequest {

    private String username;

    private String password;

    private String email;

    private String name;

}
