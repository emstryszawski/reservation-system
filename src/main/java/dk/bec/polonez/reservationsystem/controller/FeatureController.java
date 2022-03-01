package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.feature.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.feature.ResponseFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.service.FeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/features/")
public class FeatureController {
    private final FeatureService featureService;

    public FeatureController(FeatureService featureService) {
        this.featureService = featureService;
    }

    @GetMapping
    public List<Feature> getAllFeatures() {
        return featureService.getAll();
    }

    @GetMapping("{id}")
    public Feature getFeatureById(@PathVariable long id) {
        return featureService.getFeatureById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseFeatureDto addFeature(@RequestBody CreateFeatureDto featureDto) {
        return featureService.addFeature(featureDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseFeatureDto updateFeature(@RequestBody CreateFeatureDto featureDto, @PathVariable long id) {
        return featureService.updateFeature(featureDto, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.GONE)
    public ResponseFeatureDto deleteFeatureById(@PathVariable long id) {
        return featureService.deleteFeatureById(id);
    }
}
