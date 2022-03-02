package dk.bec.polonez.reservationsystem.dto.notificationDto;

import lombok.Data;

@Data
public class ReservationConfirmationInput {

    private String username;

    private String reservationInfo;
}
