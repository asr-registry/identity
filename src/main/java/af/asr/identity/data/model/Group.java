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
@Table(name = "groups")
@Builder
@Audited
@AuditTable(value = "groups_audits", schema = DatasourceSchema.PUBLIC)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Group name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

    @Column(columnDefinition = "boolean default true", name = "active")
    private boolean active;

    @Column(columnDefinition = "boolean default false",name = "is_core")
    private boolean core;


    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "group_role",
            joinColumns = @JoinColumn( name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    @JsonIgnore
    private Collection<Role> roles;

    @ManyToMany(mappedBy = "groups")
    @JsonIgnore
    private Collection<User> users;
}
