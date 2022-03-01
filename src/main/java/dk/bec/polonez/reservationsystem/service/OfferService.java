package dk.bec.polonez.reservationsystem.service;


import dk.bec.polonez.reservationsystem.dto.offerDto.CreateOfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.OfferDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseOfferFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.model.Offer;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import dk.bec.polonez.reservationsystem.repository.OfferRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final FeatureRepository featureRepository;
    private final ModelMapper modelMapper;

    public OfferService(OfferRepository offerRepository, FeatureRepository featureRepository) {
        this.offerRepository = offerRepository;
        this.featureRepository = featureRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<OfferDto> getAll() {
        List<Offer> offers = offerRepository.findAll();
        return offers.stream()
                .map(offer -> modelMapper.map(offer, OfferDto.class))
                .collect(Collectors.toList());
    }

    public OfferDto getById(long id) {
        Offer offer = offerRepository.getById(id);
        return modelMapper.map(offer, OfferDto.class);
    }

    public OfferDto addOffer(CreateOfferDto offerDto) {
        Offer offer = modelMapper.map(offerDto, Offer.class);
        Offer savedOffer = offerRepository.save(offer);
        return modelMapper.map(savedOffer, OfferDto.class);
    }

    public OfferDto updateOffer(long id, CreateOfferDto offerDto) {
        Offer offer = offerRepository.getById(id);
        modelMapper.map(offerDto, offer);
        Offer updatedOffer = offerRepository.save(offer);
        return modelMapper.map(updatedOffer, OfferDto.class);
    }

    public OfferDto deleteOffer(long id) {
        Offer offerToDelete = offerRepository.getById(id);
        offerRepository.delete(offerToDelete);
        return modelMapper.map(offerToDelete, OfferDto.class);
    }

    public ResponseOfferFeatureDto addFeatureToOffer(long featureId, long offerId) {
        Feature feature = featureRepository.getById(featureId);
        Offer offer = offerRepository.getById(offerId);
        List<Feature> features = offer.getFeatures();
        features.add(feature);
        offer.setFeatures(features);
        Offer savedOffer = offerRepository.save(offer);
        return modelMapper.map(savedOffer, ResponseOfferFeatureDto.class);
    }
}
