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

    @Column(columnDefinition = "boolean default true", name = "active")
    private boolean active;
    @Column(columnDefinition = "boolean default false",name = "is_core")
    private boolean core;


    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "role_privilge",
            joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    @JsonIgnore
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<Privilege> privileges;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<Group> groups;
}
