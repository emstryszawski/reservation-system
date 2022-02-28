package dk.bec.polonez.reservationsystem.dto.offerDto;

import dk.bec.polonez.reservationsystem.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseOfferDto {

    private long id;

    private String name;

    private long ownerId;

    private String description;
}
