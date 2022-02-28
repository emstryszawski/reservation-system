package dk.bec.polonez.reservationsystem.controller;

import dk.bec.polonez.reservationsystem.service.DiscountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/discount/")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("dc/{code}")
    public int getByCode(String code){
        return discountService.getByCode(code);
    }
}
