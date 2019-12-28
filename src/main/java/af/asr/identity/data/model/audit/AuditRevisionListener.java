package af.asr.identity.data.model.audit;

import org.hibernate.envers.RevisionListener;

public class AuditRevisionListener implements RevisionListener {


    @Override
    public void newRevision(Object revisionEntity) {
        AuditRevisionEntity auditRevisionEntity = (AuditRevisionEntity) revisionEntity;
        auditRevisionEntity.setUsername("auditor");
    }

}