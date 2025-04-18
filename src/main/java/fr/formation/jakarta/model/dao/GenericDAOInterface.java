package fr.formation.jakarta.model.dao;

import java.util.List;

public interface GenericDAOInterface<T, ID> {
    public List<T> findAll();

    public T findById(ID id);

    void persist(T entity);
    void update(T entity);
    void deleteOneById(ID id);

}
