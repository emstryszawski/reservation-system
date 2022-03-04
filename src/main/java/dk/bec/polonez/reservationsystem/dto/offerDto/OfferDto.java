package dk.bec.polonez.reservationsystem.dto.offerDto;

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

    private String description;

    private String durationUnit;

    private int units;

    private double pricePerUnit;

    private OfferOwnerDto owner;
}
