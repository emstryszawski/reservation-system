package dk.bec.polonez.reservationsystem.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OfferDto {

    private long id;

    private String name;

    private OfferOwnerDto owner;

    private String description;
}
