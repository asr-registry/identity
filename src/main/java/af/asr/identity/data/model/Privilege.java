package af.asr.identity.data.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@ToString(exclude="roles")
@EqualsAndHashCode
@Entity
@Table(name = "privileges")
@Builder
@Audited
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Privilege name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnore
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Collection<Role> roles;

}
