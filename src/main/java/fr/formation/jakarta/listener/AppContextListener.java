package fr.formation.jakarta.listener;

import fr.formation.jakarta.utils.JpaUtils;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Ferme EntityManagerFactory à l'arrêt de l'application
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Fermeture de l'EMF à l'arrêt de l'application
        JpaUtils.close();
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Rien à faire au démarrage pour l'instant
    }
}