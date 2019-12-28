package af.asr.identity.data.model;


import af.asr.identity.infrastructure.util.DatasourceSchema;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "roles")
@Audited
@AuditTable(value = "roles_audits", schema = DatasourceSchema.PUBLIC)
@Builder
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

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private Collection<Group> groups;
}
