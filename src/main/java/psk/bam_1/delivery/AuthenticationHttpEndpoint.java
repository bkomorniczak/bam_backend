package psk.bam_1.delivery;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import psk.bam_1.api.LoginRequest;
import psk.bam_1.jwt.JwtService;
import psk.bam_1.service.AuthProvider;


@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthenticationHttpEndpoint {
    private final AuthProvider authProvider;
    private final JwtService jwtService;

    @PostMapping(value = "login")
    public ResponseEntity<Void> login(final @Valid @RequestBody LoginRequest loginRequest) {
        final var authRequest = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
        final Authentication authentication;

        try {
            authentication = authProvider.authenticate(authRequest);
        } catch (final BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        final String token = jwtService.generateToken((String) authentication.getPrincipal());
        return ResponseEntity.ok().header("Authorization", "Bearer " + token).build();
    }

}
