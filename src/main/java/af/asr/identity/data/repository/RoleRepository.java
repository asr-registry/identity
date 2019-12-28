package af.asr.identity.data.repository;

import af.asr.identity.data.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RevisionRepository<Role, Long, Long> {
    Role findByName(String name);
}
