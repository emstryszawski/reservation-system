package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.reservationDto.CreateReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.ResponseReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.UpdateReservationDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.ReservationRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final OfferService offerService;


    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, OfferService offerService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.offerService = offerService;
    }

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
        Offer offer = offerService.getById(reservationDto.getOfferId());

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
        Reservation reservationExistingTest = getById(reservationDto.getId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation reservation = reservationBuilder
                .id(reservationDto.getId())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(reservationDto.getStatus())
                .build();

        Reservation updatedReservation = reservationRepository.save(reservation);

        ResponseReservationDto.ResponseReservationDtoBuilder response = ResponseReservationDto.builder();

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

    public boolean deleteReservation(Long id) {
        reservationRepository.deleteById(id);
        return true;
    }

}
