package dk.bec.polonez.reservationsystem.dto.userDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteResponse {

    private Long id;

    private String username;

    private String message;
}
