package psk.bam_1.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID>, UserDetailsService {
    Optional<UserEntity> findByUsername(final String username);

    @Override
    default UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
