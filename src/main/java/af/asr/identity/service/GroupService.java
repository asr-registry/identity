package af.asr.identity.service;

import af.asr.identity.data.model.Group;
import af.asr.identity.data.model.Role;
import af.asr.identity.data.repository.GroupRepository;
import af.asr.identity.data.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public List<Group> findAll()
    {
        return groupRepository.findAll();
    }

    public Group findByName(String name)
    {
        return  groupRepository.findByName(name);
    }

    public Group findById(Long id)
    {
        return groupRepository.findById(id).get();
    }

    public Group save(Group group)
    {
        return groupRepository.save(group);
    }

    public boolean delete(Long id)
    {

        Group obj = findById(id);
        if (id == null || id < 1 || obj.isCore()) {
            return false;
        }

        groupRepository.deleteById(id);
        return true;
    }
}
