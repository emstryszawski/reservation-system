package dk.bec.polonez.reservationsystem.dto.notification;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UpcomingEventsNotificationInput {

    private String username;

    private List<String> upcomingReservationsInfo;
}
