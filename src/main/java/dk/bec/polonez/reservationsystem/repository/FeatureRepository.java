package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.Feature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureRepository extends JpaRepository<Feature, Long> {
}
