package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import dk.bec.polonez.reservationsystem.service.exception.UserForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public boolean isAdminLoggedIn() {
        return getCurrentUser().getRole().getName().equals("SYS_ADMIN");
    }

    public boolean isPlaceOwnerLoggedIn() {
        return getCurrentUser().getRole().getName().equals("PLACE_OWNER");
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(UserForbiddenException::new);
    }
}
