package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.notificationDto.ReservationConfirmationInput;
import dk.bec.polonez.reservationsystem.dto.notificationDto.ReservationStatusNotificationInput;
import dk.bec.polonez.reservationsystem.dto.notificationDto.UpcomingEventsNotificationInput;
import dk.bec.polonez.reservationsystem.repository.NotificationTemplateRepository;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    private final NotificationTemplateRepository templateRepository;

    public MailService(NotificationTemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public void sendReservationStatusNotification(ReservationStatusNotificationInput input) {

    }

    public void sendUpcomingEventsNotification(UpcomingEventsNotificationInput input) {

    }

    public void sendReservationConfirmation(ReservationConfirmationInput input) {

    }

}
