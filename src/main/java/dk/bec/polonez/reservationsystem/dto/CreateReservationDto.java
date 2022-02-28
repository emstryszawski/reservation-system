package dk.bec.polonez.reservationsystem.dto;

import lombok.Data;

@Data
public class CreateReservationDto {
    //Long on dates - timestamp
    private Long createdAt;
    private Long dateFrom;
    private Long dateTo;
    private String status;
    private Long userId;
    private Long offerId;
}
