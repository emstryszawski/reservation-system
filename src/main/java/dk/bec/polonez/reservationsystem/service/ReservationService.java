package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.reservationDto.CreateReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.ResponseReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.UpdateReservationDto;
import dk.bec.polonez.reservationsystem.model.*;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import dk.bec.polonez.reservationsystem.repository.ReservationRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

    public ReservationService(ReservationRepository reservationRepository, UserRepository userRepository, OfferRepository offerRepository, AuthService authService) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.offerRepository = offerRepository;
        this.authService = authService;
        this.modelMapper = new ModelMapper();

        PropertyMap<Reservation, ResponseReservationDto> responseReservationMap = new PropertyMap<Reservation, ResponseReservationDto>() {
            @Override
            protected void configure() {
                map().setOfferId(source.getOffer().getId());
                map().setUserId(source.getUser().getId());
            }
        };

        modelMapper.addMappings(responseReservationMap);
    }

    public List<ResponseReservationDto> getAll() throws ResponseStatusException {
        User currentUser = authService.getCurrentUser();
        Role currentRole = currentUser.getRole();

        List<Reservation> reservations = new ArrayList<>();

        if (currentRole.hasReservationReadPrivilege() && !currentRole.hasReservationMineOnlyPrivilege() && !currentRole.hasReservationMyOffersPrivilege())
            reservations = reservationRepository.findAll();

        if (currentRole.hasReservationReadPrivilege() && currentRole.hasReservationMineOnlyPrivilege())
            reservations = reservationRepository.findByUser(currentUser);

        if (currentRole.hasReservationReadPrivilege() && currentRole.hasReservationMyOffersPrivilege()) {
            for (int i = 0; i < offerRepository.getAllByOwner(currentUser).size(); i++) {
                reservations.addAll(reservationRepository.findByOffer(offerRepository.getAllByOwner(currentUser).get(i)));
            }
        }

        return reservations.stream()
                .map(reservation -> modelMapper.map(reservation, ResponseReservationDto.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ResponseReservationDto getById(long id) throws ResponseStatusException {
        Optional<Reservation> optionalReservation = reservationRepository.findById(id);
        User currentUser = authService.getCurrentUser();
        Role currentRole = currentUser.getRole();

        if (!currentRole.hasReservationReadPrivilege()
                || optionalReservation.isEmpty()
                || !(optionalReservation.get().getUser().getId().equals(currentUser.getId())
                && optionalReservation.get().getOffer().getOwner().getId().equals(currentUser.getId())
                && authService.isAdminLoggedIn()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return modelMapper.map(optionalReservation.get(), ResponseReservationDto.class);
    }

    public ResponseReservationDto addReservation(CreateReservationDto reservationDto) throws ResponseStatusException {
        if (authService.isPlaceOwnerLoggedIn())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        User user = userRepository.getById(reservationDto.getUserId());
        Offer offer = offerRepository.getById(reservationDto.getOfferId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation reservation = reservationBuilder
                .createdAt(reservationDto.getCreatedAt())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(ReservationStatus.PENDING.name())
                .user(user)
                .offer(offer)
                .build();

        if (isReservationCollidingWithOtherOnes(reservation))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error: This reservation is colliding with others on the same offer!");

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

    public ResponseReservationDto updateReservation(UpdateReservationDto reservationDto) throws ResponseStatusException {
        User currentUser = authService.getCurrentUser();
        Role currentRole = currentUser.getRole();

        if (!currentRole.hasReservationUpdatePrivilege())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Reservation existingReservation = reservationRepository.getById(reservationDto.getId());

        Reservation.ReservationBuilder reservationBuilder = Reservation.builder();

        Reservation updatedReservation = reservationBuilder
                .id(reservationDto.getId())
                .dateFrom(reservationDto.getDateFrom())
                .dateTo(reservationDto.getDateTo())
                .status(reservationDto.getStatus())
                .createdAt(existingReservation.getCreatedAt())
                .offer(existingReservation.getOffer())
                .user(existingReservation.getUser())
                .build();

        if (isReservationCollidingWithOtherOnes(updatedReservation))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error: This reservation is colliding with others on the same offer!");

        reservationRepository.save(updatedReservation);
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
        User currentUser = authService.getCurrentUser();
        Role currentRole = currentUser.getRole();

        if (!currentRole.hasReservationDeletePrivilege())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "Error: This reservation is already approved!"));

        if (!reservation.getStatus().equals(ReservationStatus.PENDING.name()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Error: This reservation is already approved!");

        reservationRepository.deleteById(id);
        return true;
    }

    private boolean isReservationCollidingWithOtherOnes(Reservation myReservation) {
        List<Reservation> offerReservations = reservationRepository.findByOffer(myReservation.getOffer());

        for (Reservation otherReservation : offerReservations) {
            if (myReservation.collides(otherReservation))
                return true;
        }

        return false;
    }
}