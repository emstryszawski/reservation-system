package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isAdminLoggedIn() {
        return getCurrentUser().getRole().getName().equals("SYS_ADMIN");
    }

    public boolean isPoLoggedIn() {
        return getCurrentUser().getRole().getName().equals("PLACE_OWNER");
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN));
    }

}
