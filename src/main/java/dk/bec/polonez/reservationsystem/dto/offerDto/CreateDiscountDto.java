package dk.bec.polonez.reservationsystem.dto.offerDto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateDiscountDto {

    private String name;

    private String code;

    private long valueInPercentage;

    private LocalDate dateFrom;

    private LocalDate dateTo;

    private Long offerId;

}