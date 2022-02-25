package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


}
