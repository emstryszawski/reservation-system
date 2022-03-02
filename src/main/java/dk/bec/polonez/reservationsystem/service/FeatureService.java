package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.feature.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.feature.ResponseFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    private final ModelMapper modelMapper;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<Feature> getAll() {
        return featureRepository.findAll();
    }

    public Feature getFeatureById(long id) {
        Optional<Feature> optionalFeature = featureRepository.findById(id);
        return optionalFeature.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseFeatureDto addFeature(CreateFeatureDto featureDto) {
        Feature feature = modelMapper.map(featureDto, Feature.class);
        Feature savedFeature = featureRepository.save(feature);
        return modelMapper.map(savedFeature,ResponseFeatureDto.class);
    }

    public ResponseFeatureDto updateFeature(CreateFeatureDto featureDto, long id) {
        Feature feature = featureRepository.getById(id);
        modelMapper.map(featureDto, feature);
        Feature updatedFeature = featureRepository.save(feature);
        return modelMapper.map(updatedFeature,ResponseFeatureDto.class);
    }

    public ResponseFeatureDto deleteFeatureById(long id) {
        Feature deletedFeature = featureRepository.getById(id);
        featureRepository.deleteById(id);
        return  modelMapper.map(deletedFeature, ResponseFeatureDto.class);
    }
}
