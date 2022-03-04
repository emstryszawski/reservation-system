package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.offer.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offer.OfferDto;
import dk.bec.polonez.reservationsystem.dto.offer.ResponseOfferFeatureDto;
import dk.bec.polonez.reservationsystem.service.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/offers/")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public List<OfferDto> getAllOffers() {
        return offerService.getAll();
    }

    @GetMapping("{id}")
    public OfferDto getOfferById(@PathVariable long id) {
        return offerService.getById(id);
    }

    @PostMapping
    public ResponseEntity<OfferDto> addOffer(@Valid @RequestBody CreateOfferDto offerDto) {
        return new ResponseEntity<>(offerService.addOffer(offerDto), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<OfferDto> updateOffer(@PathVariable long id, @Valid @RequestBody CreateOfferDto offerDto) {
        return new ResponseEntity<>(offerService.updateOffer(id, offerDto), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<OfferDto> deleteOffer(@PathVariable long id) {
        return new ResponseEntity<>(offerService.deleteOffer(id), HttpStatus.OK);
    }

    @PostMapping("{offerId}/features")
    public ResponseEntity<ResponseOfferFeatureDto> addFeatureToOffer(@RequestParam long featureId, @PathVariable long offerId) {
        return new ResponseEntity<>(offerService.addFeatureToOffer(featureId, offerId), HttpStatus.CREATED);
    }
}
