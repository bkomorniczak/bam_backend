package psk.bam_1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import psk.bam_1.entity.UserEntity;
import psk.bam_1.entity.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthProvider implements AuthenticationManager {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        final String username = authentication.getName();
        final String password = (String) authentication.getCredentials();

        final Optional<UserEntity> user = userRepository.findByUsername(username);

        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return new UsernamePasswordAuthenticationToken(username, password, user.get().getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }


}
