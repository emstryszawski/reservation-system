package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser(User user);

}
