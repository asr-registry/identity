package af.asr.identity.data.dto;

import af.asr.identity.data.model.Group;
import af.asr.identity.data.model.Tenant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserCreateDto {

    @NotNull(message = "Role name can't be null")
    @Size(max = 64, min = 3 , message = "type valid name for tenant")

    private boolean active;

    private boolean core;

    @NotNull(message = "Phone Number can't be null")
    private String phone;

    @NotNull(message = "Username Can't be null")
    @Size(min = 3, max = 100, message = "Username must at least 3 characters.")
    private String username;

    @NotNull(message = "Password Can't be null")
    @JsonIgnore
    private String password;

    @NotNull
    private String confirmPassword;

    @NotNull
    private String email;

    @NotNull
    private Collection<Group> groups = new HashSet<>();

    @NotNull
    private Tenant tenant;
}
