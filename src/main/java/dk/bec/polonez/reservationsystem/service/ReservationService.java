package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.notification.ReservationConfirmationInput;
import dk.bec.polonez.reservationsystem.dto.notification.ReservationStatusNotificationInput;
import dk.bec.polonez.reservationsystem.dto.notification.UpcomingEventsNotificationInput;
import dk.bec.polonez.reservationsystem.dto.reservationDto.CreateReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.ResponseReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.UpdateReservationDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import dk.bec.polonez.reservationsystem.repository.ReservationRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final OfferRepository offerRepository;

    private final MailService mailService;

    public List<Reservation> getAll() {
        return reservationRepository.findAll();
    }

    public Reservation getById(long id) {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        return optionalReservation
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseReservationDto addReservation(CreateReservationDto reservationDto) {
        User user = userRepository.getById(reservationDto.getUserId());
        Offer offer = offerRepository.getById(reservationDto.getOfferId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation reservation = reservationBuilder
                .createdAt(reservationDto.getCreatedAt())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(reservationDto.getStatus())
                .user(user)
                .offer(offer)
                .build();

        Reservation savedReservation = reservationRepository.save(reservation);
        ResponseReservationDto.ResponseReservationDtoBuilder response = ResponseReservationDto.builder();

        String email = savedReservation.getUser().getEmail();
        ReservationConfirmationInput input = ReservationConfirmationInput.builder()
                .username(savedReservation.getUser().getUsername())
                .reservationInfo(getReservationDetails(savedReservation))
                .build();
        mailService.sendReservationConfirmation(email, input);

        return response
                .id(savedReservation.getId())
                .createdAt(savedReservation.getCreatedAt())
                .dateFrom(savedReservation.getDateFrom())
                .dateTo(savedReservation.getDateTo())
                .status(savedReservation.getStatus())
                .userId(savedReservation.getUser().getId())
                .offerId(savedReservation.getOffer().getId())
                .build();
    }

    public ResponseReservationDto updateReservation(UpdateReservationDto reservationDto) {
        Reservation existingReservation = getById(reservationDto.getId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation reservation = reservationBuilder
                .id(reservationDto.getId())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(reservationDto.getStatus())
                .build();

        Reservation updatedReservation = reservationRepository.save(reservation);

        ResponseReservationDto.ResponseReservationDtoBuilder response = ResponseReservationDto.builder();

        if (hasStatusChanged(existingReservation, updatedReservation)) {
            String email = updatedReservation.getUser().getEmail();
            ReservationStatusNotificationInput input = ReservationStatusNotificationInput.builder()
                    .username(updatedReservation.getUser().getUsername())
                    .reservationStatus(reservation.getStatus())
                    .reservationInfo(getReservationDetails(reservation))
                    .build();
            input.setReservationStatus(updatedReservation.getStatus());
            mailService.sendReservationStatusNotification(email, input);
        }

        return response
                .id(updatedReservation.getId())
                .createdAt(updatedReservation.getCreatedAt())
                .dateFrom(updatedReservation.getDateFrom())
                .dateTo(updatedReservation.getDateTo())
                .status(updatedReservation.getStatus())
                .userId(updatedReservation.getUser().getId())
                .offerId(updatedReservation.getOffer().getId())
                .build();
    }

    private boolean hasStatusChanged(Reservation existing, Reservation updated) {
        return !existing.getStatus().equals(updated.getStatus());
    }

    private String getReservationDetails(Reservation reservation) {
        return reservation.getOffer().getName() + ", " + new Date(reservation.getDateFrom());
    }

    public boolean deleteReservation(Long id) {
        reservationRepository.deleteById(id);
        return true;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void notifyAboutUpcomingEvents() {
        long timestamp = LocalDateTime.now()
                .plusDays(7)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
        List<Reservation> upcomingReservations = reservationRepository.findAllByDateFrom(timestamp);

        Map<User, List<Reservation>> userReservations = upcomingReservations.stream()
                .collect(Collectors.groupingBy(Reservation::getUser));

        userReservations.forEach((user, reservations) -> {
            UpcomingEventsNotificationInput input = UpcomingEventsNotificationInput.builder()
                    .username(user.getUsername())
                    .upcomingReservationsInfo(reservations.stream()
                            .map(this::getReservationDetails)
                            .collect(Collectors.toList()))
                    .build();
            mailService.sendUpcomingEventsNotification(user.getEmail(), input);
        });
    }

}
