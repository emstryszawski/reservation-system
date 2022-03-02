package dk.bec.polonez.reservationsystem.dto.reservationDto;

import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

