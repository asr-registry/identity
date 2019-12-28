package af.asr.identity.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Role name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column(name = "is_core", length = 1, nullable = false)
    private boolean core;


    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "role_privilges",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Privilege> privileges;
}
