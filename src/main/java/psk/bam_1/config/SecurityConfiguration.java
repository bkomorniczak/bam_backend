package psk.bam_1.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        final String acao = "Access-Control-Allow-Origin";

        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "https://localhost:8080", "http://localhost:3000",
                "https://localhost:3000","https://localhost:8081"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Arrays.asList("Origin", acao, "Content-Type", "Accept",
                "Authorization", "Origin , Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept",
                "Authorization", acao, acao, "Access-Control-Allow-Credentials"));

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final @NonNull HttpSecurity http,
                                                   final @NonNull AuthenticationManager authenticationManager) throws Exception {
        http
                .authenticationManager(authenticationManager)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractAuthenticationFilterConfigurer::disable)

                .logout(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/", "/actuator/**", "/actuator/health/**", "/swagger-ui/", "/swagger-ui/**",
                                "/swagger-ui.html**", "/v3/api-docs/**", "/public/**", "/favicon.ico", "/error",
                                "/instances", "/admin", "/admin/**").permitAll()
                )

                //  API
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers(HttpMethod.POST, "/api/v1/auth/login").anonymous())

                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}
