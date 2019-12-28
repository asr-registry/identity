package af.asr.identity.service;


import af.asr.identity.data.model.Tenant;
import af.asr.identity.data.repository.TenantRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TenantService {

    @Autowired
    private TenantRepository tenantRepository;

    public Tenant save(Tenant tenant)
    {
        return tenantRepository.save(tenant);
    }


    public List<Tenant> findAll()
    {
        return tenantRepository.findAll();
    }

    public Tenant findById(long id)
    {
        return tenantRepository.findById(id).get();
    }

    public boolean delete(Long id)
    {
        if (id == null || id < 1) return false;
        tenantRepository.deleteById(id);
        return true;
    }

    public Tenant findByTenantName(String name)
    {
        return tenantRepository.findByName(name);
    }

}
