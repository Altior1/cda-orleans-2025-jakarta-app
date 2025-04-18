package fr.formation.jakarta.model.dao;

import fr.formation.jakarta.model.entity.User;
import jakarta.persistence.EntityManager;

public class UserDAO extends GenericDAO<User, Long> {

    public UserDAO(EntityManager entityManager) {
        super(entityManager, User.class);
    }
}
