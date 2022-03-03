package dk.bec.polonez.reservationsystem.controller;


import dk.bec.polonez.reservationsystem.dto.reservationDto.CreateReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.ResponseReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.UpdateReservationDto;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/reservations/")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<ResponseReservationDto> getAllReservations(@RequestParam boolean sortedByCreationDate) {
        List<ResponseReservationDto> reservations = reservationService.getAll();

        if(sortedByCreationDate && !reservations.isEmpty())
            reservations.sort(Comparator.comparingLong(ResponseReservationDto::getCreatedAt));

        return reservations;
    }

    @GetMapping("{id}")
    public ResponseReservationDto getReservationByReservationId(@PathVariable long id) {
        return reservationService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseReservationDto addReservation(@RequestBody CreateReservationDto reservationDto) {
        return reservationService.addReservation(reservationDto);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteReservation(@PathVariable Long id) {
        return reservationService.deleteReservation(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseReservationDto updateReservation(@RequestBody UpdateReservationDto reservationDto) {
        return reservationService.updateReservation(reservationDto);
    }

}
