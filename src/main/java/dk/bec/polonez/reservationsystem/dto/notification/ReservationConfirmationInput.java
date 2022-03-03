package dk.bec.polonez.reservationsystem.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationConfirmationInput {

    private String username;

    private String reservationInfo;
}
