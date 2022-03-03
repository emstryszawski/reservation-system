package dk.bec.polonez.reservationsystem.dto.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationStatusNotificationInput {

    private String username;

    private String reservationInfo;

    private String reservationStatus;
}
