package af.asr.identity.data.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class TenantUpdateDto {

    @NotNull(message = "Tenant Id is required")
    private long id;
    @NotNull(message = "Tenant name can't be null")
    @Size(max = 64, min = 3 , message = "type valid name for tenant")
    private String name;
    private String description;
    @NotNull
    private boolean active;
    @NotNull
    private boolean core;
    private boolean superTenant;
}
