package dk.bec.polonez.reservationsystem.dto.reservationDto;

import lombok.Data;

@Data
public class UpdateReservationDto {
    //Long on dates - timestamp
    private Long id;
    private Long dateFrom;
    private Long dateTo;
    private String status;
}
