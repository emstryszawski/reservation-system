package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.feature.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.feature.FeatureDto;
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
    public List<FeatureDto> getAllFeatures() {
        return featureService.getAll();
    }

    @GetMapping("{id}")
    public FeatureDto getFeatureById(@PathVariable long id) {
        return featureService.getFeatureById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FeatureDto addFeature(@RequestBody CreateFeatureDto featureDto) {
        return featureService.addFeature(featureDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public FeatureDto updateFeature(@RequestBody CreateFeatureDto featureDto, @PathVariable long id) {
        return featureService.updateFeature(featureDto, id);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.GONE)
    public FeatureDto deleteFeatureById(@PathVariable long id) {
        return featureService.deleteFeatureById(id);
    }
}
