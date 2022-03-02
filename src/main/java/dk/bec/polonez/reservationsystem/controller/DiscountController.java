package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.dto.offerDto.CreateDiscountDto;
import dk.bec.polonez.reservationsystem.dto.offerDto.ResponseDiscountDto;
import dk.bec.polonez.reservationsystem.model.Discount;
import dk.bec.polonez.reservationsystem.service.DiscountService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discount/")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("{code}")
    public Discount getByCode(@PathVariable String code) {
        return discountService.getByCode(code);
    }

    @GetMapping("{id}")
    public Discount getById(@PathVariable long id) {
        return discountService.getById(id);
    }

    @GetMapping
    public List<Discount> getAll() {
        return discountService.getAll();
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteDiscount(@PathVariable long id) {
        return discountService.deleteDiscount(id);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseDiscountDto updateDiscount(@RequestBody ResponseDiscountDto discountDto) {
        return discountService.updateDiscount(discountDto);
    }

    @PostMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDiscountDto addDiscount(@RequestBody CreateDiscountDto discountDto) {
        return discountService.addDiscount(discountDto);
    }
}
