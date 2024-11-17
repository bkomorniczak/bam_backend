package psk.bam_1.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import psk.bam_1.entity.UserEntity;
import psk.bam_1.entity.UserRepository;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expire:240000}")
    private Integer accessTokenExpireInMillis;

    @SneakyThrows
    public String generateToken(final @NonNull String username) {
        try {
            Optional<UserEntity> user = userRepository.findByUsername(username);

            if (user.isEmpty()) {
                throw new RuntimeException("User not found");
            }

            UserEntity userEntity = user.get();
            return Jwts.builder()
                    .setSubject(userEntity.getUsername())
                    .claim("authorities", userEntity.getAuthorities())
                    .claim("userId", userEntity.getUserId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpireInMillis))
                    .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .compact();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

}
