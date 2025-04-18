package fr.formation.jakarta.utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Singleton pour obtenir un EntityManager
 */
public class JpaUtils {

    // Instance unique de EntityManagerFactory
    private static final EntityManagerFactory emf;

    // Initialisation automatique lors du premier appel statique
    // Une sorte de constructeur statique
    static {
        emf = Persistence.createEntityManagerFactory("pu");
    }

    // Constructeur privé pour empêcher l'instanciation
    private JpaUtils() {}

    /**
     * Retourne un nouvel EntityManager
     * @return EntityManager
     */
    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     * À appeler à l'arrêt de l'application pour libérer les ressources
     */
    public static void close() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}