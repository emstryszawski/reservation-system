package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.offerDto.DiscountDto;
import dk.bec.polonez.reservationsystem.exception.AuthenticationException;
import dk.bec.polonez.reservationsystem.exception.NoAccessToOperationException;
import dk.bec.polonez.reservationsystem.exception.NotFoundObjectException;
import dk.bec.polonez.reservationsystem.model.Discount;
import dk.bec.polonez.reservationsystem.dto.offerDto.CreateDiscountDto;
import dk.bec.polonez.reservationsystem.model.Role;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final User currentUser;
    private final Role currentUserRole;


    public DiscountService(DiscountRepository discountRepository, AuthService authService) {
        this.discountRepository = discountRepository;
        this.authService = authService;
        this.modelMapper = new ModelMapper();
        this.currentUser = authService.getCurrentUser();
        this.currentUserRole = currentUser.getRole();
    }

    public DiscountDto getByCode(String code) {

        if(!currentUserRole.hasDiscountReadPrivilege())
            throw  new NoAccessToOperationException();

        Optional<Discount> discount = discountRepository.findDiscountByCode(code);
        return modelMapper.map(discount
                .orElseThrow(() -> new NotFoundObjectException(Discount.class, code)), DiscountDto.class);

            }

    public DiscountDto getById(long id) {
        if(!currentUserRole.hasDiscountReadPrivilege())
            throw  new NoAccessToOperationException();

        Optional<Discount> discount = discountRepository.findById(id);

        return modelMapper.map(discount
                    .orElseThrow(() -> new NotFoundObjectException(Discount.class, id)), DiscountDto.class);

    }
    public List<DiscountDto> getAll() {
        List<Discount> discounts = discountRepository.findAll();
        return discounts.stream()
                .map(discount -> modelMapper.map(discount, DiscountDto.class))
                .collect(Collectors.toList());
    }

    public DiscountDto deleteDiscount(long id) {
        if(!currentUserRole.hasDiscountDeletePrivilege())
            throw  new NoAccessToOperationException();
        Optional<Discount> optionalDiscount  = discountRepository.findById(id);
        Discount discountToDelete = optionalDiscount
                 .orElseThrow(() -> new NotFoundObjectException(Discount.class, id));
        discountRepository.delete(discountToDelete);
        return modelMapper.map(discountToDelete, DiscountDto.class);

    }

    public DiscountDto updateDiscount(long id, CreateDiscountDto discountDto) {

        if(! currentUserRole.hasDiscountUpdatePrivilege())
            throw  new NoAccessToOperationException();

        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        Discount discount = optionalDiscount
                .orElseThrow(() -> new NotFoundObjectException(Discount.class, id));
        modelMapper.map(discountDto, discount);
        Discount updatedDiscount = discountRepository.save(discount);
        return modelMapper.map(updatedDiscount, DiscountDto.class);
}


    public DiscountDto addDiscount(CreateDiscountDto discountDto) {

        if(!currentUserRole.hasDiscountCreationPrivilege())
            throw  new NoAccessToOperationException();

        Discount discount = modelMapper.map(discountDto, Discount.class);
        Discount savedDiscount = discountRepository.save(discount);
        return modelMapper.map(savedDiscount, DiscountDto.class);


    }

}
