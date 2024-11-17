package psk.bam_1.api;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public final class LoginRequest implements Serializable {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
