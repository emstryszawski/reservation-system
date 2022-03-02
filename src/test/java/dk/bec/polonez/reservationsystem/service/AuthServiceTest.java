package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.model.Role;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Authentication auth;

    @InjectMocks
    private AuthService authService;

    @AfterEach
    public void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Nested
    class WhenAdmin {

        private static final String ADMIN_USERNAME = "adminUser";

        @BeforeEach
        void init() {
            Role adminRole = new Role();
            adminRole.setName("SYS_ADMIN");
            User adminUser = User.builder()
                    .username(ADMIN_USERNAME)
                    .role(adminRole)
                    .build();
            when(userRepository.findByUsername(ADMIN_USERNAME))
                    .thenReturn(Optional.of(adminUser));
            when(auth.getName())
                    .thenReturn(ADMIN_USERNAME);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        @Test
        void isAdminLoggedIn_ReturnTrue_AdminLoggedIn() {
            assertTrue(authService.isAdminLoggedIn());
        }

        @Test
        void isPlaceOwnerLoggedIn_ReturnFalse_PlaceOwnerNotLoggedIn() {
            assertFalse(authService.isPlaceOwnerLoggedIn());
        }
    }

    @Nested
    class WhenPlaceOwner {

        private static final String PLACE_OWNER_USERNAME = "placeOwnerUser";

        @BeforeEach
        void init() {
            Role placeOwner = new Role();
            placeOwner.setName("PLACE_OWNER");
            User placeOwnerUser = User.builder()
                    .username(PLACE_OWNER_USERNAME)
                    .role(placeOwner)
                    .build();
            when(userRepository.findByUsername(PLACE_OWNER_USERNAME))
                    .thenReturn(Optional.of(placeOwnerUser));
            when(auth.getName())
                    .thenReturn(PLACE_OWNER_USERNAME);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        @Test
        void isAdminLoggedIn_ReturnFalse_AdminNotLoggedIn() {
            assertFalse(authService.isAdminLoggedIn());
        }

        @Test
        void isPlaceOwnerLoggedIn_ReturnTrue_PlaceOwnerLoggedIn() {
            assertTrue(authService.isPlaceOwnerLoggedIn());
        }
    }
}