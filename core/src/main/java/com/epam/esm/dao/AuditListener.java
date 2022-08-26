package com.epam.esm.dao;

import com.epam.esm.common.BeanUtil;
import com.epam.esm.entity.Audit;
import com.epam.esm.entity.GenericEntity;


import javax.persistence.EntityManager;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import java.time.LocalDateTime;

/**
 * This class enters a class into the database, who does and when does a certain operation
 */
public class AuditListener {

    @PostPersist
    private void beforePersistOperation(Object object) {
        persistAudit("Persist", object);
    }

    @PostUpdate
    private void beforeUpdateOperation(Object object) {
        persistAudit("Update", object);
    }

    @PostRemove
    private void beforeRemoveOperation(Object object) {
        persistAudit("Remove", object);
    }

    /**
     * Build entity audit
     * @param operation for sql
     * @param object class that make operation
     */
    public void persistAudit(String operation, Object object) {
        GenericEntity entity = (GenericEntity) object;
        Audit audit = Audit.builder()
                .entityClass(entity.getClass().getSimpleName())
                .entityId(entity.getId())
                .operation(operation)
                .dateTime(LocalDateTime.now())
                .build();

        LocalContainerEntityManagerFactoryBean bean = BeanUtil.getBean(LocalContainerEntityManagerFactoryBean.class);
        EntityManager entityManager = bean.getObject().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(audit);
        entityManager.getTransaction().commit();
    }
}
