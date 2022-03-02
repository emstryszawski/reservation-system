package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseDiscountDto;
import dk.bec.polonez.reservationsystem.model.Discount;
import dk.bec.polonez.reservationsystem.dto.offerDto.CreateDiscountDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.Reservation;
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

    private final OfferService offerService;

    public DiscountService(DiscountRepository discountRepository, OfferService offerService) {
        this.discountRepository = discountRepository;
        this.offerService = offerService;
    }

    public Discount getByCode(String code) {
        return discountRepository.findDiscountByCode(code);
    }

    public Discount getById(long id) {
        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        return optionalDiscount.
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Discount> getAll() {
        return discountRepository.findAll();
    }

    public boolean deleteDiscount(long id) {
        discountRepository.deleteById(id);
        return true;
    }

    public ResponseDiscountDto updateDiscount(ResponseDiscountDto discountDto) {
        Discount discountExistingTest = getById(discountDto.getId());

        Offer offer = offerService.getById(discountDto.getOfferId());
        Discount.DiscountBuilder discountBuilder = Discount.builder();

        Discount discount = discountBuilder
                .id(discountDto.getId())
                .dateFrom(discountDto.getDateFrom())
                .dateTo(discountDto.getDateTo())
                .code(discountDto.getCode())
                .name(discountDto.getName())
                .valueInPercentage(discountDto.getValueInPercentage())
                .offer(offer)
                .build();

        Discount updatedDiscount = discountRepository.save(discount);

        ResponseDiscountDto.ResponseDiscountDtoBuilder response = ResponseDiscountDto.builder();

        return response
                .id(updatedDiscount.getId())
                .dateFrom(updatedDiscount.getDateFrom())
                .dateTo(updatedDiscount.getDateTo())
                .code(updatedDiscount.getCode())
                .name(updatedDiscount.getName())
                .valueInPercentage(updatedDiscount.getValueInPercentage())
                .offerId(updatedDiscount.getOffer().getId())
                .build();

    }


    public ResponseDiscountDto addDiscount(CreateDiscountDto discountDto) {
        Offer offer = offerService.getById(discountDto.getOfferId());

        Discount.DiscountBuilder discountBuilder = Discount.builder();

        Discount discount = discountBuilder
                .dateFrom(discountDto.getDateFrom())
                .dateTo(discountDto.getDateTo())
                .code(discountDto.getCode())
                .name(discountDto.getName())
                .valueInPercentage(discountDto.getValueInPercentage())
                .offer(offer)
                .build();

        Discount savedDiscount = discountRepository.save(discount);
        ResponseDiscountDto.ResponseDiscountDtoBuilder response = ResponseDiscountDto.builder();

        return response
                .id(savedDiscount.getId())
                .code(savedDiscount.getCode())
                .name(savedDiscount.getName())
                .valueInPercentage(savedDiscount.getValueInPercentage())
                .offerId(savedDiscount.getOffer().getId())
                .dateFrom(savedDiscount.getDateFrom())
                .dateTo(savedDiscount.getDateTo())
                .build();
    }

}
