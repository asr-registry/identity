package af.asr.identity.data.repository;

import af.asr.identity.data.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long>, RevisionRepository<Group, Long, Long> {
    Group findByName(String name);
}
