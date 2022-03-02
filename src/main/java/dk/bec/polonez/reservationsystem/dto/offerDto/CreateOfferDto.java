package dk.bec.polonez.reservationsystem.dto.offerDto;

import lombok.Data;

@Data
public class CreateOfferDto {

    private String name;

    private String description;

    private long ownerId;
}
