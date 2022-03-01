package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.reservationDto.CreateReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.ResponseReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.UpdateReservationDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.model.ReservationStatus;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import dk.bec.polonez.reservationsystem.repository.ReservationRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    private final UserRepository userRepository;

    private final OfferRepository offerRepository;

    private final AuthService authService;

    private final ModelMapper modelMapper;

    //TODO: check if reservation collides with other ones on that offer during addition


    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, OfferRepository offerRepository, AuthService authService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.authService = authService;
        this.modelMapper = new ModelMapper();
    }

    public ArrayList<ResponseReservationDto> getAll(Boolean sortedByDate) throws ResponseStatusException{
        // Only for Admin - all reservations in the system
        if(!authService.isAdminLoggedIn())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error: You don't have a permission to see it!");

        List<Reservation> reservations = reservationRepository.findAll();

        if(reservations.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: There are no reservations");

        if(sortedByDate)
            reservations.sort(Comparator.comparingLong(Reservation::getCreatedAt));

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ResponseReservationDto.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //TODO: Get all my reservations DONE

    public ArrayList<ResponseReservationDto> getAllMine(Boolean sortedByDate) throws ResponseStatusException {
        User currentUser = authService.getCurrentUser();

        final List<Reservation> reservations = new ArrayList<>();

        if(authService.isPoLoggedIn())
            offerRepository.getAllByOwner(currentUser)
                    .forEach((reservation) -> reservations.addAll(reservationRepository.findByOffer(reservation)));
        else
            reservations.addAll(reservationRepository.findByUser(currentUser));

        if(reservations.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: There are no reservations");

        if(sortedByDate)
            reservations.sort(Comparator.comparingLong(Reservation::getCreatedAt));

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ResponseReservationDto.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public ArrayList<ResponseReservationDto> getAllMineSortedByDate() {

        User currentUser = authService.getCurrentUser();

        ArrayList<Reservation> reservations = new ArrayList<>(reservationRepository.findByUser(currentUser));

        reservations.sort(Comparator.comparingLong(Reservation::getCreatedAt));

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ResponseReservationDto.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<ResponseReservationDto> getAllByUserId(Long id) {

        List<Reservation> reservations = reservationRepository.findByUser(userRepository.getById(id));

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ResponseReservationDto.class))
                .collect(Collectors.toList());
    }


    public ResponseReservationDto getById(long id) throws ResponseStatusException{
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);

        //TODO: if the reservation is not mine, I can't see it, unless admin

        if(optionalReservation.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: Reservation with this id is not existing");

        return modelMapper.map(optionalReservation.get(), ResponseReservationDto.class);
    }



    public ResponseReservationDto addReservation(CreateReservationDto reservationDto) {
        if(authService.isPoLoggedIn())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        User user = userRepository.getById(reservationDto.getUserId().getId());
        Offer offer = offerRepository.getById(reservationDto.getOfferId().getId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation reservation = reservationBuilder
                .createdAt(reservationDto.getCreatedAt())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(ReservationStatus.PENDING.name())
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

        //TODO: I can edit only My reservation (if ia m an owner or user on the reservation + admin can edit everything)

        Reservation reservationExistingTest = reservationRepository.getById(reservationDto.getId());

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

    public boolean deleteReservation(Long id) throws ResponseStatusException {
        //TODO: I can delete only if I am an owner of user of this reservation + admin

        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        Reservation reservation;

        if(optionalReservation.isPresent())
            reservation = optionalReservation.get();
        else
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: This reservation not exist!");

        if(!reservation.getStatus().equals(ReservationStatus.PENDING.name()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error: This reservation is already approved!");

        reservationRepository.deleteById(id);
        return true;
    }

}
