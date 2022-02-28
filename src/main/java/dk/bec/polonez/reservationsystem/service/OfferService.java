package dk.bec.polonez.reservationsystem.service;


import dk.bec.polonez.reservationsystem.dto.offerDto.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseOfferDto;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final UserRepository userRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository, UserRepository userRepository) {
        this.offerRepository = offerRepository;
        this.userRepository = userRepository;
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
        User owner = userRepository.getById(offerDto.getOwnerId());
        Offer offer = Offer.builder()
                .name(offerDto.getName())
                .owner(owner)
                .description(offerDto.getDescription())
                .build();

        Offer savedOffer = offerRepository.save(offer);

        return ResponseOfferDto.builder()
                .name(savedOffer.getName())
                .ownerId(savedOffer.getOwner().getId())
                .description(savedOffer.getDescription())
                .build();
    }
    public ResponseOfferDto updateOffer(long id, CreateOfferDto offerDto) {
        User owner = userRepository.getById(offerDto.getOwnerId());
        Offer offer = offerRepository.getById(id);

        offer.setName(offerDto.getName());
        offer.setOwner(owner);
        offer.setDescription(offerDto.getDescription());

        Offer updatedOffer = offerRepository.save(offer);

        return ResponseOfferDto.builder()
                .id(updatedOffer.getId())
                .name(updatedOffer.getName())
                .ownerId(updatedOffer.getOwner().getId())
                .description(updatedOffer.getDescription())
                .build();
    }

    public ResponseOfferDto deleteOffer(long id) {
        Offer offerToDelete = offerRepository.getById(id);
        offerRepository.delete(offerToDelete);

        return ResponseOfferDto.builder()
                .id(offerToDelete.getId())
                .name(offerToDelete.getName())
                .ownerId(offerToDelete.getOwner().getId())
                .description(offerToDelete.getDescription())
                .build();
    }
}
