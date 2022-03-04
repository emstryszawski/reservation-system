package dk.bec.polonez.reservationsystem.dto.offer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOfferFeatureDto {

    private long id;

    private String name;

    private String description;

    private OfferOwnerDto owner;

    private List<ResponseFeatureDto> features;
}
