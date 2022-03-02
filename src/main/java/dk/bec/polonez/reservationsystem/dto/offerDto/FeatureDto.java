package dk.bec.polonez.reservationsystem.dto.offerDto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeatureDto {


    private long id;

    private String name;

    private String description;
}
