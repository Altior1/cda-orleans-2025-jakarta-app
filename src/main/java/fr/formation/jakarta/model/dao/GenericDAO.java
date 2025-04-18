package fr.formation.jakarta.model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.function.Consumer;

import java.util.List;

public abstract class GenericDAO<T, ID> implements GenericDAOInterface<T, ID>
{
    protected EntityManager entityManager;
    protected Class<T> entityClass;

    public GenericDAO(EntityManager entityManager, Class<T> entityClass) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
    }

    protected void executeInTransaction(Consumer<EntityManager> action){
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            action.accept(entityManager);
            tx.commit();
        } catch (Exception e){
            tx.rollback();
            System.out.println(e.getMessage());
        }
    }

    public List<T> findAll(){
        String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        TypedQuery<T> query =  entityManager.createQuery(jpql, entityClass);
        return query.getResultList();
    }

    public T findById(ID id){
        return entityManager.find(entityClass, id);
    }

    public void persist(T entity){
        executeInTransaction(  em->{ em.persist(entity); });
    }

    public void update(T entity){
        executeInTransaction(  em->{ em.merge(entity); });
    }

    public void deleteOneById(ID id){
        executeInTransaction(em->{
            em.remove(
                    em.find(entityClass, id)
            );
        });
    }

}
