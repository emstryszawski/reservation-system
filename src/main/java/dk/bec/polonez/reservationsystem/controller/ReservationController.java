package dk.bec.polonez.reservationsystem.controller;


import dk.bec.polonez.reservationsystem.dto.reservationDto.CreateReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.ResponseReservationDto;
import dk.bec.polonez.reservationsystem.dto.reservationDto.UpdateReservationDto;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservation/")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAll();
    }

    @GetMapping("{id}")
    public Reservation getReservationById(@PathVariable long id) {
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
