package dk.bec.polonez.reservationsystem.dto.feature;

import lombok.Data;

@Data
public class UpdateFeatureDto {

    private long id;

    private String name;

    private String description;
}
