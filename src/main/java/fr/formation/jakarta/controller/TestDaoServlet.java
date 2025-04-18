package fr.formation.jakarta.controller;


import fr.formation.jakarta.model.dao.StudentDAO;
import fr.formation.jakarta.model.entity.Course;
import fr.formation.jakarta.model.entity.Student;
import fr.formation.jakarta.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/test-dao")
public class TestDaoServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Student martine = new Student();
        martine.setName("Martine");
        martine.addCourse(
                new Course().setName("Java Programming"));
        martine.addCourse(
                new Course().setName("Algèbre linéaire")
        );
        martine.addCourse(
                new Course().setName("peinture sur soi")
        );

        EntityManager em = JpaUtils.getEntityManager();
        StudentDAO dao = new StudentDAO(em);
        dao.persist(martine);

        render(resp, "test-dao.peb");
    }
}
