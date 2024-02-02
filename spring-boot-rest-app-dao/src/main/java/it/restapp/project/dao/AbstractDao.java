package it.restapp.project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao <I extends Serializable, Id extends Serializable> implements GenericRepository<I, Id>{

    @PersistenceContext
    protected EntityManager entityManager;

    protected final Class<I> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractDao()
    {
        this.entityClass = (Class<I>) ((ParameterizedType)
                this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    @Override
    public List<I> getAll() {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<I> query = builder.createQuery(this.entityClass);

        return this.entityManager.createQuery(
                query.select(query.from(this.entityClass))).getResultList();
    }

    @Override
    public I insert(I entity) {
        this.entityManager.persist(entity);
        flushAndClear();
        return entity;
    }

    @Override
    public I update(I entity) {
        this.entityManager.merge(entity);
        flushAndClear();
        return entity;
    }

    @Override
    public void delete(I entity) {
        this.entityManager.remove(this.entityManager.contains(entity) ? entity : this.entityManager.merge(entity));
        flushAndClear();
    }

    @Override
    public void deleteById(Id id) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaDelete<I> query = builder.createCriteriaDelete(this.entityClass);

        this.entityManager.createQuery(
                query.where(
                        builder.equal(
                                query.from(this.entityClass)
                                        .get("id"), id)
                )).executeUpdate();

        flushAndClear();
    }

    @Override
    public I getById(Id id) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<I> query = builder.createQuery(this.entityClass);
        return this.entityManager.createQuery(
                        query.where(
                                builder.equal(
                                        query.from(this.entityClass).
                                        get("id"), id))).
                        getSingleResult();
    }
    public List<I> getByParam(String paramName, Object paramValue) {
        CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<I> query = builder.createQuery(this.entityClass);
        return this.entityManager.createQuery(
                        query.where(
                                builder.equal(
                                        query.from(this.entityClass).
                                                get(paramName), paramValue))).
                getResultList();
    }
    private void flushAndClear()
    {
        entityManager.flush();
        entityManager.clear();
    }
}
