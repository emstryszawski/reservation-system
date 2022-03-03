package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.notification.ReservationConfirmationInput;
import dk.bec.polonez.reservationsystem.dto.notification.ReservationStatusNotificationInput;
import dk.bec.polonez.reservationsystem.dto.notification.UpcomingEventsNotificationInput;
import dk.bec.polonez.reservationsystem.model.NotificationTemplate;
import dk.bec.polonez.reservationsystem.repository.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    private final NotificationTemplateRepository templateRepository;

    public void sendReservationStatusNotification(String emailAddress, ReservationStatusNotificationInput input) {
        NotificationTemplate template = templateRepository.getByName("RESERVATION_STATUS");
        String message = String.format(
                template.getMessage(),
                input.getUsername(),
                input.getReservationInfo(),
                input.getReservationStatus());

        sendEmail(emailAddress, template.getSubject(), message);
    }

    public void sendUpcomingEventsNotification(String emailAddress, UpcomingEventsNotificationInput input) {
        NotificationTemplate template = templateRepository.getByName("UPCOMING_EVENTS");
        String message = String.format(
                template.getMessage(),
                input.getUsername(),
                String.join(", ", input.getUpcomingReservationsInfo()));

        sendEmail(emailAddress, template.getSubject(), message);
    }

    public void sendReservationConfirmation(String emailAddress, ReservationConfirmationInput input) {
        NotificationTemplate template = templateRepository.getByName("RESERVATION_CONFIRMATION");
        String message = String.format(
                template.getMessage(),
                input.getUsername(),
                input.getReservationInfo());

        sendEmail(emailAddress, template.getSubject(), message);
    }

    private void sendEmail(String address, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

}
