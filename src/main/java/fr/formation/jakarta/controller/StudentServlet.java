package fr.formation.jakarta.controller;

import fr.formation.jakarta.model.dao.StudentDAO;
import fr.formation.jakarta.model.entity.Student;
import fr.formation.jakarta.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/student/*")
public class StudentServlet extends AbstractCrudServlet<Student, Long> {
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        String action = req.getPathInfo();
        switch (action) {
            case "/list" -> this.showAll(req, resp);
            case "/show-one" -> this.showOne(req, resp);
            case "/create" -> this.showForm(req, resp, false);
            case "/edit" -> this.showForm(req, resp, true);
            case "/delete" -> this.delete(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        switch (req.getPathInfo()) {
            case "/create" -> this.create(req, resp);
            case "/edit" -> this.update(req, resp);
            default -> resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected StudentDAO getDao(EntityManager em) {
        return new StudentDAO(em);
    }

    protected String getEntityName(){ return "student"; }

    protected void hydrateForm(HttpServletRequest req, Student entity) {
        context.put("student", entity);
    }

    protected Student hydrateEntity(HttpServletRequest req) {
        Student entity = new Student();
        Long id = Long.parseLong(req.getParameter("id"));
        entity.setId(id);
        entity.setName(req.getParameter("name"));
        return entity;
    }

    protected Long getIdFromRequest(HttpServletRequest req) {
        return Long.parseLong(req.getParameter("id"));
    }
}
