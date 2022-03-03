package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.offerDto.CreateDiscountDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.DiscountDto;
import dk.bec.polonez.reservationsystem.model.Discount;
import dk.bec.polonez.reservationsystem.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discounts/")
public class DiscountController {
    private final DiscountService discountService;


    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("{code}")
    public DiscountDto getByCode(@PathVariable String code) {
        return discountService.getByCode(code);
    }

    @GetMapping("{id}")
    public DiscountDto getById(@PathVariable long id) {
        return discountService.getById(id);
    }

    @GetMapping
    public List<DiscountDto> getAll() {
        return discountService.getAll();
    }

    @DeleteMapping("{id}")
    public DiscountDto deleteDiscount(@PathVariable long id) {
        return discountService.deleteDiscount(id);
    }

    @PutMapping("{id}")
    public DiscountDto updateDiscount(@PathVariable long id, @RequestBody CreateDiscountDto discountDto) {
        return discountService.updateDiscount(id, discountDto);
    }

    @PostMapping
    public DiscountDto addDiscount(@RequestBody CreateDiscountDto discountDto) {
        return discountService.addDiscount(discountDto);
    }
}
