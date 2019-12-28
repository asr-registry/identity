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
import java.util.HashSet;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "tenants")
@Audited
@AuditTable(value = "priviliges_audits", schema = DatasourceSchema.PUBLIC)
@Builder
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Tenant name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

    @Column(columnDefinition = "boolean default true", name = "active")
    private boolean active;
    @Column(columnDefinition = "boolean default true",name = "is_core")
    private boolean core;

    @Column(columnDefinition = "boolean default false",name = "is_super_tenant")
    private boolean superTenant;


    @OneToMany
    @JsonIgnore
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<User> users;

}
