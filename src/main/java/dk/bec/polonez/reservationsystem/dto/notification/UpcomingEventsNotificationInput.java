package dk.bec.polonez.reservationsystem.dto.notification;

import lombok.Data;

import java.util.List;

@Data
public class UpcomingEventsNotificationInput {

    private String username;

    private List<String> upcomingReservationsInfo;
}
