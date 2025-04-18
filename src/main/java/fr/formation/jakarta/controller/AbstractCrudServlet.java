package fr.formation.jakarta.controller;

import fr.formation.jakarta.model.dao.GenericDAO;
import fr.formation.jakarta.model.dao.StudentDAO;
import fr.formation.jakarta.model.entity.Student;
import fr.formation.jakarta.utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public abstract class AbstractCrudServlet<T, ID>  extends AbstractServlet {

    protected abstract GenericDAO<T, ID> getDao(EntityManager em);
    protected abstract String getEntityName();
    protected abstract void hydrateForm(HttpServletRequest req, T entity);
    protected abstract T hydrateEntity(HttpServletRequest req);
    protected abstract ID getIdFromRequest(HttpServletRequest req);

    protected void showAll(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        EntityManager em = JpaUtils.getEntityManager();
        List<T> entityList = getDao(em).findAll();

        context.put(getEntityName() + "List", entityList);
        render(resp, req.getContextPath()  + getEntityName() + "/list.peb");
    }

    protected void showOne(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        EntityManager em = JpaUtils.getEntityManager();
        ID id = getIdFromRequest(req);
        T entity = getDao(em).findById(id);
        hydrateForm(req, entity);
        render(resp, req.getContextPath()  + getEntityName() + "/one.peb");
    }

    protected void create(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException
    {
        EntityManager em = JpaUtils.getEntityManager();
        T entity = hydrateEntity(req);
        getDao(em).persist(entity);
        em.close();
        resp.sendRedirect(req.getContextPath() + getEntityName() + "/list");
    }

    protected void update(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        EntityManager em = JpaUtils.getEntityManager();
        T entity = hydrateEntity(req);
        getDao(em).update(entity);
        em.close();
        resp.sendRedirect(req.getContextPath() + getEntityName() + "/list");
    }

    protected void showForm(
            HttpServletRequest req,
            HttpServletResponse resp,
            boolean forEdit
    ) throws IOException {
        if (forEdit) {
            ID id = getIdFromRequest(req);
            EntityManager em = JpaUtils.getEntityManager();
            GenericDAO<T, ID> dao = getDao(em);
            T entity = dao.findById(id);
            em.close();

            if (entity != null) {
                hydrateForm(req, entity);
            }
        }
        render(resp, req.getContextPath() +  getEntityName() + "/form.peb");
    }

    protected void delete(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws IOException {
        EntityManager em = JpaUtils.getEntityManager();
        ID id = getIdFromRequest(req);
        getDao(em).deleteOneById(id);
        em.close();
        resp.sendRedirect(req.getContextPath() + getEntityName() + "/list");
    }
}
