package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.offerDto.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseOfferDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offer/")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<Offer> getAllOffers() {
        return offerService.getAll();
    }

    @GetMapping("{id}")
    public Offer getOfferById(@PathVariable long id) {
        return offerService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseOfferDto addOffer(@RequestBody CreateOfferDto offerDto) {
        return offerService.addOffer(offerDto);
    }
}
