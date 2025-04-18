package fr.formation.jakarta.controller.user;

import fr.formation.jakarta.controller.AbstractServlet;
import fr.formation.jakarta.model.entity.User;
import fr.formation.jakarta.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/persist")
public class UserPersistServlet extends AbstractServlet {
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException
    {
        // Création d'une entité
        User user = new User();
        user    .setUsername("Severn")
                .setEmail("severn@gmail.com")
                .setAge(57);

        // Récupération de l'EntityManager
        EntityManager em = JpaUtils.getEntityManager();

        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        em.close();

        context.put("user", user);
        render(resp, "user/persist.peb");

    }
}
