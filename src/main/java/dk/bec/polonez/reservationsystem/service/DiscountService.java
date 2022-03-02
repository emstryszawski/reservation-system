package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.model.Discount;
import dk.bec.polonez.reservationsystem.dto.DiscountDto;
import dk.bec.polonez.reservationsystem.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    public Discount findById(int id){
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        return optionalDiscount.
                orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Discount> getAll(){
        return discountRepository.findAll();
    }

    public void deleteDiscount(int id){
        discountRepository.deleteById(id);
    }

    public void updateDiscount(int id, DiscountDto updateDiscount){
        Discount discountToUpdate = discountRepository.getById(id);

        discountToUpdate.setCode(updateDiscount.getCode());
        discountToUpdate.setDateFrom(updateDiscount.getDateFrom());
        discountToUpdate.setDateTo(updateDiscount.getDateTo());
        discountToUpdate.setName(updateDiscount.getName());

        discountRepository.save(discountToUpdate);
    }

}
