package dk.bec.polonez.reservationsystem.dto.reservationDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseReservationDto {
    //Long on dates - timestamp
    private Long id;
    private Long createdAt;
    private Long dateFrom;
    private Long dateTo;
    private String status;
    private Long userId;
    private Long offerId;
}
