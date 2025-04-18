package fr.formation.jakarta.model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public abstract class GenericDAO<T, ID> implements GenericDAOInterface<T, ID>
{
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    public GenericDAO(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    public List<T> findAll(){
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> query =  entityManager.createQuery(jpql, entityClass);
        return query.getResultList();
    }
}
