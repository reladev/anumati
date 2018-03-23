package org.reladev.anumati.tickets.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.reladev.anumati.SecurityContext;
import org.reladev.anumati.tickets.entity.User;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

@Component
public class BaseRepository<T> {
    @PersistenceContext
    protected EntityManager entityManager;
    private Class<T> typeClass;

    @SuppressWarnings("unchecked")
    public BaseRepository() {
        typeClass = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), BaseRepository.class);
    }

    public void save(T entity) {
        entityManager.persist(entity);
    }

    public T get(long id) {
        return entityManager.find(typeClass, id);
    }


    public void delete(long id) {
        T delete = get(id);
        if (delete != null) {
            entityManager.remove(delete);
        }
    }

    public List<T> findAll() {
        Long companyId = ((User) SecurityContext.getUser()).getCompanyId();
        String query = "SELECT e FROM " + typeClass.getSimpleName() + " e ";
        if (companyId != null) {
            query += "WHERE e.companyId = " + companyId;
        }
        return entityManager.createQuery(query, typeClass).getResultList();

    }
}