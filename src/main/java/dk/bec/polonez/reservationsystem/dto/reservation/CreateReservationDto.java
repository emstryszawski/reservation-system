package dk.bec.polonez.reservationsystem.dto.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateReservationDto {
    //Long on dates - timestamp
    private Long createdAt;
    private Long dateFrom;
    private Long dateTo;
    private String status;
    private Long userId;
    private Long offerId;
}
