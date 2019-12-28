package af.asr.identity.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString(exclude="roles")
@EqualsAndHashCode
@Entity
@Table(name = "privileges")
@Builder
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Privilege name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnore
    private Collection<Role> roles;

}
