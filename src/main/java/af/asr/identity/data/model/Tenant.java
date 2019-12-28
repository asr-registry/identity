package af.asr.identity.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;

//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tenants")
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Tenant name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
    @Column(name = "active", length = 1, nullable = false)
    private boolean active;
    @Column(name = "is_core", length = 1, nullable = false)
    private boolean core;

    @OneToMany
    @JsonIgnore
    private Collection<User> users;

}
