package dk.bec.polonez.reservationsystem.dto.notificationDto;

import lombok.Data;

@Data
public class ReservationStatusNotificationInput {

    private String username;

    private String reservationInfo;

    private String reservationStatus;
}
