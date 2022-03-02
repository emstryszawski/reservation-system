package dk.bec.polonez.reservationsystem.dto.offerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferOwnerDto {

    private long id;

    private String username;

    private String name;

    private String email;
}
