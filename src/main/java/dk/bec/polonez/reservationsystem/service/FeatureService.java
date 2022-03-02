package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.offerDto.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.FeatureDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;
    private final ModelMapper modelMapper;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<FeatureDto> getAll() {
        List<Feature> features = featureRepository.findAll();
        return features.stream()
                .map(feature -> modelMapper.map(feature,FeatureDto.class))
                .collect(Collectors.toList());
    }

    public FeatureDto getById(long id) {
        Feature feature = featureRepository.getById(id);
        return modelMapper.map(feature, FeatureDto.class);
    }

    public FeatureDto deleteFeature(long id){
        Feature featureToDelete = featureRepository.getById(id);
        featureRepository.delete(featureToDelete);
        return modelMapper.map(featureToDelete, FeatureDto.class);
    }

    public FeatureDto addFeature(CreateFeatureDto featureDto) {
        Feature feature = modelMapper.map(featureDto, Feature.class);
        Feature savedFeature = featureRepository.save(feature);
        return modelMapper.map(savedFeature, FeatureDto.class);
    }

    public FeatureDto updateFeature(long id, CreateFeatureDto featureDto){
        Feature feature = featureRepository.getById(id);
        modelMapper.map(featureDto, feature);
        Feature updatedFeature = featureRepository.save(feature);
        return modelMapper.map(updatedFeature, FeatureDto.class);
    }


}
