package af.asr.identity.infrastructure.init;

import af.asr.identity.data.model.Tenant;
import af.asr.identity.infrastructure.util.Constants;
import af.asr.identity.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TenantDataInitializer {

    @Autowired
    private TenantService tenantService;

    public void init()
    {
        createCoreTenantsIfNotExist();
    }

    public Tenant createCoreTenantsIfNotExist()
    {
        if(tenantService.findAll().size() < 1)
        {
            Tenant tenant = new Tenant();
            tenant.setName(Constants.DEFAULT_TENANT_NAME);
            tenant.setCore(true);
            tenant.setDescription("This is the core and main tenant.");

            return tenantService.save(tenant);
        }

        return tenantService.findAll().get(0);
    }
}
