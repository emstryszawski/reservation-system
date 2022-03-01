package dk.bec.polonez.reservationsystem.dto.feature;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseFeatureDto {

    private long id;

    private String name;

    private String description;
}
