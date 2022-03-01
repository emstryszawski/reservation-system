package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.feature.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.feature.ResponseFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
    }

    public List<Feature> getAll() {
        return featureRepository.findAll();
    }

    public Feature getFeatureById(long id) {
        Optional<Feature> optionalFeature = featureRepository.findById(id);
        return optionalFeature.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public ResponseFeatureDto addFeature(CreateFeatureDto featureDto) {
        Feature.FeatureBuilder builder = Feature.builder();
        Feature feature = builder
                .name(featureDto.getName())
                .description(featureDto.getDescription())
                .build();
        Feature savedFeature = featureRepository.save(feature);
        ResponseFeatureDto.ResponseFeatureDtoBuilder response = ResponseFeatureDto.builder();
        return response
                .id(savedFeature.getId())
                .name(savedFeature.getName())
                .description(savedFeature.getDescription())
                .build();
    }

    public ResponseFeatureDto updateFeature(CreateFeatureDto featureDto, long id) {
        Feature.FeatureBuilder builder = Feature.builder();
        Feature feature = builder
                .id(id)
                .name(featureDto.getName())
                .description(featureDto.getDescription())
                .build();
        Feature savedFeature = featureRepository.save(feature);
        ResponseFeatureDto.ResponseFeatureDtoBuilder response = ResponseFeatureDto.builder();
        return response
                .id(savedFeature.getId())
                .name(savedFeature.getName())
                .description(savedFeature.getDescription())
                .build();
    }

    public ResponseFeatureDto deleteFeatureById(long id) {
        Feature deletedFeature = featureRepository.getById(id);
        featureRepository.deleteById(id);
        ResponseFeatureDto.ResponseFeatureDtoBuilder response = ResponseFeatureDto.builder();
        return response
                .id(deletedFeature.getId())
                .name(deletedFeature.getName())
                .description(deletedFeature.getDescription())
                .build();
    }
}
