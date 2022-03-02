package dk.bec.polonez.reservationsystem.dto.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateFeatureDto {

    private String name;

    private String description;
}

