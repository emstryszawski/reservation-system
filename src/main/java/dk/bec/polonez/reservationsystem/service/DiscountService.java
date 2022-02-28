package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public int getByCode(String code){
        return discountRepository.getByCode(code);
    }
}
