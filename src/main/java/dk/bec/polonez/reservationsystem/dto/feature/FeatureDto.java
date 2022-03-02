package dk.bec.polonez.reservationsystem.dto.feature;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeatureDto {

    private long id;

    private String name;

    private String description;
}
