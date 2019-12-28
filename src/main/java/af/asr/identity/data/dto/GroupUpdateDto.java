package af.asr.identity.data.dto;

import af.asr.identity.data.model.Role;
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
public class GroupUpdateDto {

    @NotNull(message = "Group Id is required")
    private long id;
    @NotNull(message = "Group name can't be null")
    @Size(max = 64, min = 3 , message = "type valid name for tenant")
    private String name;
    private String description;
    private boolean active;
    private boolean core;
    @NotNull
    private Collection<Role> roles;
}
