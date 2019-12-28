package af.asr.identity.data.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
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

}
