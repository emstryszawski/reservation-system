package dk.bec.polonez.reservationsystem.repository;

import dk.bec.polonez.reservationsystem.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long>{
    Discount findDiscountByCode(String code);
}
