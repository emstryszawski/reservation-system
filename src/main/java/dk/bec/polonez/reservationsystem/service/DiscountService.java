package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.offerDto.DiscountDto;
import dk.bec.polonez.reservationsystem.model.Discount;
import dk.bec.polonez.reservationsystem.dto.offerDto.CreateDiscountDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.repository.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;


    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
        this.modelMapper = new ModelMapper();
    }

    public DiscountDto getByCode(String code) {
        Discount discount = discountRepository.findDiscountByCode(code);
        return modelMapper.map(discount, DiscountDto.class);
    }

    public DiscountDto getById(long id) {
        Discount discount = discountRepository.getById(id);
        return modelMapper.map(discount, DiscountDto.class);
    }
    public List<DiscountDto> getAll() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream()
                .map(discount -> modelMapper.map(discount, DiscountDto.class))
                .collect(Collectors.toList());
    }

    public DiscountDto deleteDiscount(long id) {
        Discount discountToDelete = discountRepository.getById(id);
        discountRepository.delete(discountToDelete);
        return modelMapper.map(discountToDelete, DiscountDto.class);
    }

    public DiscountDto updateDiscount(long id, CreateDiscountDto discountDto) {
        Discount discount = discountRepository.getById(id);
        modelMapper.map(discountDto, discount);
        Discount updatedDiscount = discountRepository.save(discount);
        return modelMapper.map(updatedDiscount, DiscountDto.class);
    }

    public DiscountDto addDiscount(CreateDiscountDto discountDto) {
            Discount discount = modelMapper.map(discountDto, Discount.class);
            Discount savedDiscount = discountRepository.save(discount);
            return modelMapper.map(savedDiscount, DiscountDto.class);
    }

}
