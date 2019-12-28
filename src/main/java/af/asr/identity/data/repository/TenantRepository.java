package af.asr.identity.data.repository;

import af.asr.identity.data.model.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long>, RevisionRepository<Tenant, Long, Long> {
    Tenant findByName(String name);
}
