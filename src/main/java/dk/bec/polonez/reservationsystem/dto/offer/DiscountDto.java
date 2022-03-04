package dk.bec.polonez.reservationsystem.dto.offer;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private long id;

    private String name;

    private String code;

    private long valueInPercentage;

    private long dateFrom;

    private long dateTo;

    private OfferDto offer;
}

