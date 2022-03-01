package dk.bec.polonez.reservationsystem.service;


import dk.bec.polonez.reservationsystem.dto.offerDto.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseOfferDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> getAll() {
        return offerRepository.findAll();
    }

    public Offer getById(long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        return optionalOffer
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseOfferDto addOffer(CreateOfferDto offerDto) {
        User user = new User();
        user.setId(1L);

        Offer.OfferBuilder builder = Offer.builder();
        Offer offer = builder
                .name(offerDto.getName())
                .owner(user)
                .description(offerDto.getDescription())
                .build();

        Offer savedOffer = offerRepository.save(offer);
        ResponseOfferDto.ResponseOfferDtoBuilder response = ResponseOfferDto.builder();
        return response
                .name(savedOffer.getName())
                .ownerId(savedOffer.getOwner().getId())
                .description(savedOffer.getDescription())
                .build();
    }
}
