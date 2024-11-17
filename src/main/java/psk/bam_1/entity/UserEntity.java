package psk.bam_1.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SuperBuilder
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            nullable = false,
            updatable = false
    )
    private UUID userId;

    @ToString.Exclude
    @JsonIgnore
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String nationalId;

    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return Objects.equals(userId, that.userId) && Objects.equals(password, that.password)
                && Objects.equals(email, that.email) && Objects.equals(username, that.username)
                && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName)
                && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(nationalId, that.nationalId)
                && Objects.equals(enabled, that.enabled);
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

}
