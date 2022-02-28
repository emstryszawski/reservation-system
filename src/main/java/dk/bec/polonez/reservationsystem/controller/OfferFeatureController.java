package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.model.OfferFeature;
import dk.bec.polonez.reservationsystem.service.OfferFeatureService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/offerfeature/")
public class OfferFeatureController {

    private final OfferFeatureService offerFeatureService;

    public OfferFeatureController(OfferFeatureService offerFeatureService) {
        this.offerFeatureService = offerFeatureService;
    }

    @GetMapping("ft/{id}")
    public Set<OfferFeature> getAllOffersUsingFeature(@PathVariable long id) {
        return offerFeatureService.getAllOffersUsingFeature(id);
    }


    @GetMapping("of/{id}")
    public Set<OfferFeature> getAllFeaturesUsedByOffer(@PathVariable long id) {
        return offerFeatureService.getAllFeaturesForOffer(id);
    }
}
