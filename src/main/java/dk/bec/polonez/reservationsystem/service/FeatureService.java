package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.offerDto.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.model.Reservation;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Feature getById(long id) {
        Optional<Feature> optionalFeature = featureRepository.findById(id);
        return optionalFeature.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public boolean deleteFeature(long id){
        featureRepository.deleteById(id);
        return true;
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
                .name(savedFeature.getName())
                .description(savedFeature.getDescription())
                .build();
    }

    public ResponseFeatureDto updateFeature(ResponseFeatureDto featureDto){
        Feature featureExistingTest = getById(featureDto.getId());

        Feature.FeatureBuilder featureBuilder = Feature.builder();

        Feature feature = featureBuilder
                .id(featureDto.getId())
                .name(featureDto.getName())
                .description(featureDto.getDescription())
                .build();

        Feature updatedFeature = featureRepository.save(feature);

        ResponseFeatureDto.ResponseFeatureDtoBuilder responseFeatureDto = ResponseFeatureDto.builder();

        return responseFeatureDto
                .id(updatedFeature.getId())
                .description(updatedFeature.getDescription())
                .name(updatedFeature.getName())
                .build();
    }


}
