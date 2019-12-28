package af.asr.identity.service;

import af.asr.identity.data.model.Privilege;
import af.asr.identity.data.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

    @Autowired
    private PrivilegeRepository repository;

    public Privilege save(Privilege privilege)
    {
        return repository.save(privilege);
    }
    public List<Privilege> findAll()
    {
        return repository.findAll();
    }
}