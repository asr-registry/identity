package af.asr.identity.data.model;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString(exclude="roles")
@EqualsAndHashCode
@Entity
@Table(name = "privileges")
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotNull(message = "Privilege name can't be null")
    @Column(nullable = false, unique = true)
    private String name;
    @Column
    private String description;
}
