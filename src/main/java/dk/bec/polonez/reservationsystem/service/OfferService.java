package dk.bec.polonez.reservationsystem.service;


import dk.bec.polonez.reservationsystem.dto.offerDto.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.OfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseOfferFeatureDto;
import dk.bec.polonez.reservationsystem.exception.NoAccessToOfferOperationException;
import dk.bec.polonez.reservationsystem.exception.NotFoundObjectException;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.model.Role;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
                () -> new NotFoundObjectException(Offer.class, id)), OfferDto.class);
    }

    public OfferDto addOffer(CreateOfferDto offerDto) {
        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if (!currentUserRole.hasOfferCreatePrivilege())
            throw new NoAccessToOfferOperationException();

        Offer offer = modelMapper.map(offerDto, Offer.class);
        offer.setOwner(currentUser);
        Offer savedOffer = offerRepository.save(offer);
        return modelMapper.map(savedOffer, OfferDto.class);
    }

    public OfferDto updateOffer(long id, CreateOfferDto offerDto) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        Offer offer = optionalOffer.orElseThrow(
                () -> new NotFoundObjectException(Offer.class, id));

        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if (!currentUserRole.hasOfferUpdatePrivilege())
            throw new NoAccessToOfferOperationException();

        if (!offer.getOwner().equals(currentUser) && !currentUserRole.hasOfferUpdateOthersOfferPrivilege())
            throw new NoAccessToOfferOperationException("You cannot update other Place Owner's offer as a Place Owner, to do that log as an Admin");

        modelMapper.map(offerDto, offer);
        Offer updatedOffer = offerRepository.save(offer);
        return modelMapper.map(updatedOffer, OfferDto.class);
    }

    public OfferDto deleteOffer(long id) {
        Optional<Offer> optionalOffer = offerRepository.findById(id);
        Offer offer = optionalOffer.orElseThrow(
                () -> new NotFoundObjectException(Offer.class, id));

        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if (!currentUserRole.hasOfferDeletePrivilege())
            throw new NoAccessToOfferOperationException();
        if (!offer.getOwner().equals(currentUser) && !currentUserRole.hasOfferDeleteOthersOfferPrivilege())
            throw new NoAccessToOfferOperationException("You cannot delete other Place Owner's offer as a Place Owner, to do that log as an Admin");

        offerRepository.delete(offer);
        return modelMapper.map(offer, OfferDto.class);
    }

    public ResponseOfferFeatureDto addFeatureToOffer(long featureId, long offerId) {
        Optional<Feature> optionalFeature = featureRepository.findById(featureId);
        Optional<Offer> optionalOffer = offerRepository.findById(offerId);
        Offer offer = optionalOffer.orElseThrow(
                () -> new NotFoundObjectException(Offer.class, offerId));
        Feature feature = optionalFeature.orElseThrow(
                () -> new NotFoundObjectException(Feature.class, featureId));

        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if (!currentUserRole.hasOfferUpdatePrivilege())
            throw new NoAccessToOfferOperationException();
        if (!offer.getOwner().equals(currentUser) && !currentUserRole.hasOfferDeleteOthersOfferPrivilege)
            throw new NoAccessToOfferOperationException("You cannot update other Place Owner's offer features as a Place Owner, to do that log as an Admin");

        List<Feature> features = offer.getFeatures();
        features.add(feature);
        offer.setFeatures(features);
        Offer savedOffer = offerRepository.save(offer);
        return modelMapper.map(savedOffer, ResponseOfferFeatureDto.class);
    }
}
