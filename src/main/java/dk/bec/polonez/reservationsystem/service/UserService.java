package dk.bec.polonez.reservationsystem.service;

import dk.bec.polonez.reservationsystem.dto.user.*;
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

    private final AuthService authService;

    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, AuthService authService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.authService = authService;
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

    public List<ProfileResponse> getAllUsers() throws ResponseStatusException{
        if(!authService.isAdminLoggedIn() || authService.getCurrentUser().isBlocked())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        List<ProfileResponse> profiles = new ArrayList<>();
        userRepository.findAll().forEach( (n) -> profiles.add(userToResponse(n)));

        return profiles;
    }

    public SignupResponse createUser(SignupRequest request) throws ResponseStatusException{
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

    public UpdateResponse updateUser(UpdateRequest updatedProfile) throws ResponseStatusException{
        if(!authService.isAdminLoggedIn() || authService.getCurrentUser().isBlocked())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);


        User user = userRepository.findById(updatedProfile.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: User not found"));

        user.setUsername(updatedProfile.getUsername());
        user.setEmail(updatedProfile.getEmail());
        user.setName(updatedProfile.getName());
        user.setPassword(updatedProfile.getPassword());

        userRepository.save(user);

        UpdateResponse response = new UpdateResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setBlocked(user.isBlocked());
        response.setRoleName(user.getRole().getName());
        response.setName(user.getName());

        return response;
    }


    public ProfileResponse getById(Long id) throws ResponseStatusException{

        return userToResponse(userRepository.getById(id));
    }

    public ProfileResponse block(Long id) {
        if(!authService.isAdminLoggedIn())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        User user = userRepository.getById(id);

        user.setBlocked(true);

        User blockedUser = userRepository.save(user);

        return userToResponse(blockedUser);
    }

    public ProfileResponse unblock(Long id) {
        if(!authService.isAdminLoggedIn())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        User user = userRepository.getById(id);

        user.setBlocked(false);

        User unblockedUser = userRepository.save(user);

        return userToResponse(unblockedUser);
    }

    public DeleteResponse delete(Long id) {
        if(!authService.isAdminLoggedIn())
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Error: User not found"));

        userRepository.delete(user);

        return DeleteResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .message("User deleted.")
                .build();
    }

    public ProfileResponse userToResponse(User user) {
        return ProfileResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .roleName(user.getRole().getName())
                .blocked(user.isBlocked())
                .build();
    }


}
