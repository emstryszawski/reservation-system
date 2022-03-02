package dk.bec.polonez.reservationsystem.dto.offerDto;


import dk.bec.polonez.reservationsystem.model.Offer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

    private long id;

    private String name;

    private String code;

    private long valueInPercentage;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private OfferDto offer;
}

