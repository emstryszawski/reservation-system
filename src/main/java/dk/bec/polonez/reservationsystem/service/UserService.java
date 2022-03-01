package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.userDto.ProfileRequest;
import dk.bec.polonez.reservationsystem.dto.userDto.ProfileResponse;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupRequest;
import dk.bec.polonez.reservationsystem.dto.userDto.SignupResponse;
import dk.bec.polonez.reservationsystem.model.Role;
import dk.bec.polonez.reservationsystem.model.User;
import dk.bec.polonez.reservationsystem.repository.RoleRepository;
import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(user.getRole().getName())));

    }

    public List<ProfileResponse> getAllUsers() {
        List<ProfileResponse> profiles = new ArrayList<ProfileResponse>();
        userRepository.findAll().forEach( (n) -> profiles.add(userToResponse(n)));

        return profiles;
    }

    public SignupResponse createUser(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Email is already in use!");
        }

        String strRole = request.getRole().toUpperCase();
        Role role = roleRepository.findByName(strRole)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Role not found"));

        User user = User.builder()
                .username(request.getUsername())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);
        return SignupResponse.builder()
                .username(savedUser.getUsername())
                .email(savedUser.getEmail())
                .build();
    }


    public ProfileResponse getById(Long id) {

        return userToResponse(userRepository.getById(id));
    }

    public ProfileResponse block(Long id) {
        User user = userRepository.getById(id);

        user.setBlocked(true);

        User blockedUser = userRepository.save(user);

        return userToResponse(blockedUser);
    }

    public ProfileResponse unblock(Long id) {
        User user = userRepository.getById(id);

        user.setBlocked(false);



        User unblockedUser = userRepository.save(user);

        return userToResponse(unblockedUser);
    }

    public ProfileResponse userToResponse(User user) {
        return ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .roleName(user.getRole().getName())
                .isBlocked(user.isBlocked())
                .build();
    }


}
