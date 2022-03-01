package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
        public List<Offer> getAllByOwner(User user);
}
