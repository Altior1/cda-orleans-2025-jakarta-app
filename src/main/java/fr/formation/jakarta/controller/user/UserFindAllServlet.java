package fr.formation.jakarta.controller.user;

import fr.formation.jakarta.controller.AbstractServlet;
import fr.formation.jakarta.model.entity.User;
import fr.formation.jakarta.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/list")
public class UserFindAllServlet extends AbstractServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = JpaUtils.getEntityManager();

        TypedQuery<User> query = em.createQuery(
                "select u from User u",
                User.class
        );
        List<User> users = query.getResultList();

        em.close();

        context.put("userList", users);

        render(resp, "user/list.peb");
    }
}
