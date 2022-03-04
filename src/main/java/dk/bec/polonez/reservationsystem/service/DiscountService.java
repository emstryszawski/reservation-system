
package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.offer.DiscountDto;
import dk.bec.polonez.reservationsystem.exception.NoAccessToOperationException;
import dk.bec.polonez.reservationsystem.exception.NotFoundObjectException;
import dk.bec.polonez.reservationsystem.model.Discount;
import dk.bec.polonez.reservationsystem.dto.offer.CreateDiscountDto;
import dk.bec.polonez.reservationsystem.model.Role;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.DiscountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class DiscountService {

    private final DiscountRepository discountRepository;
    private final ModelMapper modelMapper;
    private final AuthService authService;
    private final OfferService offerService;

    public DiscountService(DiscountRepository discountRepository, AuthService authService, OfferService offerService) {
        this.discountRepository = discountRepository;
        this.authService = authService;
        this.offerService = offerService;
        this.modelMapper = new ModelMapper();
    }

    public DiscountDto getByCode(String code) {
        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if(!currentUserRole.hasDiscountReadPrivilege() || currentUser.isBlocked())
            throw  new NoAccessToOperationException();

        Optional<Discount> discount = discountRepository.findDiscountByCode(code);
        return modelMapper.map(discount
                .orElseThrow(() -> new NotFoundObjectException(Discount.class, code)), DiscountDto.class);

    }

    public DiscountDto getById(long id) {
        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if(!currentUserRole.hasDiscountReadPrivilege() || currentUser.isBlocked())
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
        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if(!currentUserRole.hasDiscountDeletePrivilege() || currentUser.isBlocked())
            throw  new NoAccessToOperationException();

        Optional<Discount> optionalDiscount  = discountRepository.findById(id);
        Discount discountToDelete = optionalDiscount
                .orElseThrow(() -> new NotFoundObjectException(Discount.class, id));

        if(!discountToDelete.getOffer().getOwner().equals(currentUser) && !authService.isAdminLoggedIn())
            throw  new NoAccessToOperationException("You can not delete discounts of other users");

        discountRepository.delete(discountToDelete);
        return modelMapper.map(discountToDelete, DiscountDto.class);

    }

    public DiscountDto updateDiscount(long id, CreateDiscountDto discountDto) {
        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if(!currentUserRole.hasDiscountUpdatePrivilege() || currentUser.isBlocked())
            throw  new NoAccessToOperationException();

        Optional<Discount> optionalDiscount = discountRepository.findById(id);
        Discount discount = optionalDiscount
                .orElseThrow(() -> new NotFoundObjectException(Discount.class, id));

        if(!discount.getOffer().getOwner().equals(currentUser) && !authService.isAdminLoggedIn())
            throw  new NoAccessToOperationException("You can not update discounts of other users");

        modelMapper.map(discountDto, discount);
        Discount updatedDiscount = discountRepository.save(discount);
        return modelMapper.map(updatedDiscount, DiscountDto.class);
    }

    public DiscountDto addDiscount(CreateDiscountDto discountDto) {
        User currentUser = authService.getCurrentUser();
        Role currentUserRole = currentUser.getRole();

        if(!currentUserRole.hasDiscountCreationPrivilege() || currentUser.isBlocked())
            throw  new NoAccessToOperationException();

        Discount discount = modelMapper.map(discountDto, Discount.class);

        if(!discount.getOffer().getOwner().equals(currentUser) && !authService.isAdminLoggedIn())
            throw  new NoAccessToOperationException("You can not add discount to another user offer");

        Discount savedDiscount = discountRepository.save(discount);
        return modelMapper.map(savedDiscount, DiscountDto.class);
    }


}
