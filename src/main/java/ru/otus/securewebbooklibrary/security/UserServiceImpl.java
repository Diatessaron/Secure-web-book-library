package ru.otus.securewebbooklibrary.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.securewebbooklibrary.domain.User;
import ru.otus.securewebbooklibrary.repository.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.findByUsername(username).orElseThrow
                (() -> new IllegalArgumentException("Incorrect username"));

        return new UserPrincipal(user);
    }
}
