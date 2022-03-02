package dk.bec.polonez.reservationsystem.dto.notificationDto;

import lombok.Data;

import java.util.List;

@Data
public class UpcomingEventsNotificationInput {

    private String username;

    private List<String> upcomingReservationsInfo;
}
