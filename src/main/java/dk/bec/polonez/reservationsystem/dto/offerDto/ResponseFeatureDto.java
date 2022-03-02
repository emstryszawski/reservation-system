package dk.bec.polonez.reservationsystem.dto.offerDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseFeatureDto {

    private long id;

    private String name;

    private String description;
}
