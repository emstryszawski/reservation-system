package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {

    public NotificationTemplate getByName(String name);
}
