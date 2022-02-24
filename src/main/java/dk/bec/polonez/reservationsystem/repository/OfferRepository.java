package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

}
