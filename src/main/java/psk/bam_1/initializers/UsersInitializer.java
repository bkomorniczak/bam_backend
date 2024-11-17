package psk.bam_1.initializers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import psk.bam_1.entity.UserRepository;

@Log4j2
@RequiredArgsConstructor
public class UsersInitializer implements ApplicationRunner {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UsersToAddConfig config;

    @Override
    @Transactional
    public void run(final ApplicationArguments args) {
        log.info("{} running", this.getClass().getSimpleName());

        config.getUsers().forEach(user -> {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        });

        log.info("Initialized users: {}", config.getUsers());
    }
}
