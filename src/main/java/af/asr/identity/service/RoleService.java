package af.asr.identity.service;

import af.asr.identity.data.model.Role;
import af.asr.identity.data.model.Tenant;
import af.asr.identity.data.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll()
    {
        return roleRepository.findAll();
    }

    public Role findByName(String name)
    {
        return  roleRepository.findByName(name);
    }

    public Role findById(Long id)
    {
        return roleRepository.findById(id).get();
    }

    public Role save(Role role)
    {
        return roleRepository.save(role);
    }

    public boolean delete(Long id)
    {

        Role obj = findById(id);
        if (id == null || id < 1 || obj.isCore()) {
            return false;
        }

        roleRepository.deleteById(id);
        return true;
    }

}
