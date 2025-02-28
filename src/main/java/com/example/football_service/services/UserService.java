package com.example.football_service.services;

import com.example.football_service.domain.common.request.CreateUser;
import com.example.football_service.domain.entities.AppUser;
import com.example.football_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
    private final UserRepository userRepository;

    public Long create(CreateUser request) {
        AppUser newUser = new AppUser(request.firstName(), request.lastName(), request.password(), request.email());
        return userRepository.save(newUser).getId();
    }

    public UserDetails loadUserByUsername(String email) {
        AppUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );
    }

    public AppUser getByEmail(String email) {
        AppUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with email: " + email);
        }
        return user;
    }
}
