package psk.bam_1.initializers;

import lombok.Data;
import lombok.NoArgsConstructor;
import psk.bam_1.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UsersToAddConfig {
    private List<UserEntity> users = new ArrayList<>();
}
