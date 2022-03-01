package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.offerDto.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.OfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseOfferFeatureDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.service.OfferService;
import org.springframework.web.bind.annotation.*;

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
    public OfferDto addOffer(@RequestBody CreateOfferDto offerDto) {
        return offerService.addOffer(offerDto);
    }

    @PutMapping("{id}")
    public OfferDto updateOffer(@PathVariable long id, @RequestBody CreateOfferDto offerDto) {
        return offerService.updateOffer(id, offerDto);
    }

    @DeleteMapping("{id}")
    public OfferDto deleteOffer(@PathVariable long id) {
        return offerService.deleteOffer(id);
    }

    @PostMapping("{id}/feature")
    public ResponseOfferFeatureDto addFeatureToOffer(@RequestParam long featureId, @PathVariable long offerId) {
        return offerService.addFeatureToOffer(featureId, offerId);
    }
}
