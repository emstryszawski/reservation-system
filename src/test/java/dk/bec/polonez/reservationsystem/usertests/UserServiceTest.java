package dk.bec.polonez.reservationsystem.usertests;

import dk.bec.polonez.reservationsystem.dto.userDto.SignupRequest;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupResponse;
import dk.bec.polonez.reservationsystem.model.Role;
import dk.bec.polonez.reservationsystem.repository.RoleRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import dk.bec.polonez.reservationsystem.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UserService userService;


    private PasswordEncoder encoder;

    @Test(expected = ResponseStatusException.class)
    public void should_ThrowResponseStatusException_When_InvalidRoleIsPassed()  {
        SignupRequest request = new SignupRequest();
        request.setUsername("MasnyBen");
        request.setPassword("2115ToGeng");
        request.setRole("USERR");
        request.setEmail("xdd@xdddd.pl");

        SignupResponse user = new SignupResponse();
        user.setUsername("MasnyBen");
        user.setEmail("xdd@xdddd.pl");


//        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(roleRepository.getById(any(Long.class))).thenReturn(new Role());

        Role role = roleRepository.getById(1L);

        SignupResponse created = userService.createUser(request);

        assertThat(created.getUsername()).isSameAs(request.getUsername());
    }
}
