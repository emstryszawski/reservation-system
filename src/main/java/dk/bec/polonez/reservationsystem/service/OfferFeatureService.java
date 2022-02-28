package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.model.OfferFeature;
import dk.bec.polonez.reservationsystem.repository.OfferFeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class OfferFeatureService {

    private final OfferFeatureRepository offerFeatureRepository;

    @Autowired
    public OfferFeatureService(OfferFeatureRepository offerFeatureRepository) {
        this.offerFeatureRepository = offerFeatureRepository;
    }

    public Set<OfferFeature> getAllOffersUsingFeature(long featureId) {
        return offerFeatureRepository.findAllOffersUsingFeature(featureId);
    }

    public Set<OfferFeature> getAllFeaturesForOffer(long offerId) {
        return offerFeatureRepository.findAllOffersUsingFeature(offerId);
    }
}
