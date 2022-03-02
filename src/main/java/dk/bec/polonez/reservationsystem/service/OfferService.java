package dk.bec.polonez.reservationsystem.service;


import dk.bec.polonez.reservationsystem.dto.offerDto.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.OfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseOfferFeatureDto;
import dk.bec.polonez.reservationsystem.exception.AuthenticationException;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final FeatureRepository featureRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;

    public OfferService(OfferRepository offerRepository, FeatureRepository featureRepository, AuthService authService) {
        this.offerRepository = offerRepository;
        this.featureRepository = featureRepository;
        this.authService = authService;
        this.modelMapper = new ModelMapper();
    }

    public List<OfferDto> getAll() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    public OfferDto getById(long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        return modelMapper.map(offer.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer with id=" + id + " not found")), OfferDto.class);
    }

    public OfferDto addOffer(CreateOfferDto offerDto) {
        if (authService.isPoLoggedIn() || authService.isAdminLoggedIn()) {
            Offer offer = modelMapper.map(offerDto, Offer.class);
            offer.setOwner(authService.getCurrentUser());
            Offer savedOffer = offerRepository.save(offer);
            return modelMapper.map(savedOffer, OfferDto.class);
        } else {
            throw new AuthenticationException(HttpStatus.FORBIDDEN, "Access denied, log as Place Owner or System Admin");
        }
    }

    public OfferDto updateOffer(long id, CreateOfferDto offerDto) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (authService.isPoLoggedIn() || authService.isAdminLoggedIn()) {
            Offer offer = optionalOffer.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer with id=" + id + " not found"));

            if ((authService.isPoLoggedIn() && offer.getOwner().equals(authService.getCurrentUser())) || authService.isAdminLoggedIn()) {
                modelMapper.map(offerDto, offer);
                Offer updatedOffer = offerRepository.save(offer);
                return modelMapper.map(updatedOffer, OfferDto.class);
            } else {
                throw new AuthenticationException(HttpStatus.FORBIDDEN, "You cannot update other Place Owner's offer as a Place Owner, to do that log as an Admin");
            }
        } else {
            throw new AuthenticationException(HttpStatus.FORBIDDEN, "Access denied, log as Place Owner or System Admin");
        }
    }

    public OfferDto deleteOffer(long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        if (authService.isPoLoggedIn() || authService.isAdminLoggedIn()) {
            Offer offer = optionalOffer.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer with id=" + id + " not found"));

            if ((authService.isPoLoggedIn() && offer.getOwner().equals(authService.getCurrentUser())) || authService.isAdminLoggedIn()) {
                offerRepository.delete(offer);
                return modelMapper.map(offer, OfferDto.class);
            } else {
                throw new AuthenticationException(HttpStatus.FORBIDDEN, "You cannot delete other Place Owner's offer as a Place Owner, to do that log as an Admin");
            }
        } else {
            throw new AuthenticationException(HttpStatus.FORBIDDEN, "Access denied, log as Place Owner or System Admin");
        }
    }

    public ResponseOfferFeatureDto addFeatureToOffer(long featureId, long offerId) {
        Optional<Feature> optionalFeature = featureRepository.findById(featureId);
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);

        if (authService.isPoLoggedIn() || authService.isAdminLoggedIn()) {
            Offer offer = optionalOffer.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer with id=" + offerId + " not found"));
            Feature feature = optionalFeature.orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Feature with id=" + featureId + " not found"));

            if ((authService.isPoLoggedIn() && offer.getOwner().equals(authService.getCurrentUser())) || authService.isAdminLoggedIn()) {
                List<Feature> features = offer.getFeatures();
                features.add(feature);
                offer.setFeatures(features);
                Offer savedOffer = offerRepository.save(offer);
                return modelMapper.map(savedOffer, ResponseOfferFeatureDto.class);
            } else {
                throw new AuthenticationException(HttpStatus.FORBIDDEN, "You cannot update other Place Owner's offer features as a Place Owner, to do that log as an Admin");
            }
        } else {
            throw new AuthenticationException(HttpStatus.FORBIDDEN, "Access denied, log as Place Owner or System Admin");
        }
    }

}
