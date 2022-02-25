package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.OfferFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OfferFeatureRepository extends JpaRepository<OfferFeature, Long> {

    Set<OfferFeature> findAllFeaturesForOffer(long OfferID);

    Set<OfferFeature> findAllOffersUsingFeature(long FeatureID);
}
