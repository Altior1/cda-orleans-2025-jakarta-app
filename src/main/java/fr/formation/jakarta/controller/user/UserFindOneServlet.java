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

@WebServlet("/user/show")
public class UserFindOneServlet extends AbstractServlet {

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException
    {
        int id = Integer.parseInt(req.getParameter("id"));
        EntityManager em = JpaUtils.getEntityManager();
        User user = em.find(User.class, id);

        context.put("user", user);

        render(resp, "/user/one.peb");
    }
}
