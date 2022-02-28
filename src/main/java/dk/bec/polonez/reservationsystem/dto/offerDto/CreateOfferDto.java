package dk.bec.polonez.reservationsystem.dto.offerDto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CreateOfferDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Size(max = 2000)
    private String description;

    private long ownerId;
}
