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
@Table(name = "groups")
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Group name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

    @Column(name = "active", length = 1, nullable = false)
    private boolean active;

    @Column(name = "is_core", length = 1, nullable = false)
    private boolean core;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "group_role",
            joinColumns = @JoinColumn( name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Role> roles;
}
