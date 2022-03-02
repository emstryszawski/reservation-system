package dk.bec.polonez.reservationsystem.dto.offerDto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ResponseDiscountDto {

    private long id;

    private String name;

    private String code;

    private long valueInPercentage;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private Long offerId;
}

