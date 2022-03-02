package dk.bec.polonez.reservationsystem.dto.feature;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFeatureDto {

    private long id;

    private String name;

    private String description;
}
