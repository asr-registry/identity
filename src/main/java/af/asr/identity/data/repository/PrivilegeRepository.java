package af.asr.identity.data.repository;

import af.asr.identity.data.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long>, RevisionRepository<Privilege, Long, Long> {
}

