package dk.bec.polonez.reservationsystem.dto.offer;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateOfferDto {

    @Size(min = 2, max = 128, message = "name should have at least 2 chars up to 128")
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 2000, message = "description cannot be longer than 2000 chars")
    private String description;

    private String durationUnit;

    private int units;
}
