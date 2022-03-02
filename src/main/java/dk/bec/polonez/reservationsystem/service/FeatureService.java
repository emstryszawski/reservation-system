package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.feature.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.feature.FeatureDto;
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

    public List<FeatureDto> getAll() {
        List<Feature> features = featureRepository.findAll();
        return features.stream()
                .map(feature -> modelMapper.map(feature, FeatureDto.class))
                .collect(Collectors.toList());
    }

    public FeatureDto getFeatureById(long id) {
        Feature feature = featureRepository.getById(id);
        return modelMapper.map(feature, FeatureDto.class);
    }

    public FeatureDto addFeature(CreateFeatureDto featureDto) {
        Feature feature = modelMapper.map(featureDto, Feature.class);
        Feature savedFeature = featureRepository.save(feature);
        return modelMapper.map(savedFeature, FeatureDto.class);
    }

    public FeatureDto updateFeature(CreateFeatureDto featureDto, long id) {
        Feature feature = featureRepository.getById(id);
        modelMapper.map(featureDto, feature);
        Feature updatedFeature = featureRepository.save(feature);
        return modelMapper.map(updatedFeature, FeatureDto.class);
    }

    public FeatureDto deleteFeatureById(long id) {
        Feature deletedFeature = featureRepository.getById(id);
        featureRepository.deleteById(id);
        return modelMapper.map(deletedFeature, FeatureDto.class);
    }
}
