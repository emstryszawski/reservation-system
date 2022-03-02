package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.feature.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.feature.ResponseFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.repository.FeatureRepository;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    private final FeatureRepository featureRepository;

    private final ModelMapper modelMapper;

    public FeatureService(FeatureRepository featureRepository) {
        this.featureRepository = featureRepository;
        this.modelMapper = new ModelMapper();
    }

    public List<ResponseFeatureDto> getAll() {
        List<Feature> features = featureRepository.findAll();
        return features.stream()
                .map(feature -> modelMapper.map(feature, ResponseFeatureDto.class))
                .collect(Collectors.toList());
    }

    public ResponseFeatureDto getFeatureById(long id) {
        Feature feature = featureRepository.getById(id);
        return modelMapper.map(feature, ResponseFeatureDto.class);
    }

    public ResponseFeatureDto addFeature(CreateFeatureDto featureDto) {
        Feature feature = modelMapper.map(featureDto, Feature.class);
        Feature savedFeature = featureRepository.save(feature);
        return modelMapper.map(savedFeature, ResponseFeatureDto.class);
    }

    public ResponseFeatureDto updateFeature(CreateFeatureDto featureDto, long id) {
        Feature feature = featureRepository.getById(id);
        modelMapper.map(featureDto, feature);
        Feature updatedFeature = featureRepository.save(feature);
        return modelMapper.map(updatedFeature, ResponseFeatureDto.class);
    }

    public ResponseFeatureDto deleteFeatureById(long id) {
        Feature deletedFeature = featureRepository.getById(id);
        featureRepository.deleteById(id);
        return modelMapper.map(deletedFeature, ResponseFeatureDto.class);
    }
}
