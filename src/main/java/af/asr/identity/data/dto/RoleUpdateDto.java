package af.asr.identity.data.dto;

import af.asr.identity.data.model.Privilege;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class RoleUpdateDto {

    @NotNull(message = "Role Id is required")
    private long id;
    @NotNull(message = "Role name can't be null")
    @Size(max = 64, min = 3 , message = "type valid name for tenant")
    private String name;
    private String description;
    private boolean active;
    private boolean core;
    private Collection<Privilege> privileges;
}
