package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.offerDto.CreateFeatureDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.FeatureDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseFeatureDto;
import dk.bec.polonez.reservationsystem.model.Feature;
import dk.bec.polonez.reservationsystem.service.FeatureService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feature/")
public class FeatureController {
    private final FeatureService featureService;

    public FeatureController(FeatureService featureService){
        this.featureService = featureService;
    }

    @GetMapping
    public List<FeatureDto> getAllFeatures() {
        return featureService.getAll();
    }

    @GetMapping("{id}")
    public FeatureDto getFeatureById(@PathVariable long id) {
        return featureService.getById(id);
    }

    @PostMapping
    public FeatureDto addFeature(@RequestBody CreateFeatureDto featureDto) {
        return featureService.addFeature(featureDto);
    }

    @DeleteMapping("{id}")
    public FeatureDto deleteFeature(@PathVariable long id){
        return featureService.deleteFeature(id);
    }

    @PutMapping("{id}")
    public FeatureDto updateFeature(@PathVariable long id, @RequestBody CreateFeatureDto featureDto) {
        return featureService.updateFeature(id, featureDto);
    }
}
