package dk.bec.polonez.reservationsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import dk.bec.polonez.reservationsystem.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    public void a() {

    }

}
