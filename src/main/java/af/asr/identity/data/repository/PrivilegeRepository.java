package af.asr.identity.data.repository;

import af.asr.identity.data.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, RevisionRepository<Privilege, Long, Long> {
    Privilege findByName(String name);

    @Query(value = "select privileges.id, privileges.name, privileges.description, privileges.active from users inner join user_group on user_group.user_id = users.id inner join groups on groups.id = user_group.group_id inner join group_role on group_role.group_id = user_group.group_id inner join role_privilge on role_privilge.role_id = group_role.role_id inner join privilges on privileges.id = role_permission.permission_id where users.id=?1 and users.tenant_id=?2", nativeQuery = true)
    List<Privilege> findAllByUserAndTenant(Long userId, long tenant);
}


