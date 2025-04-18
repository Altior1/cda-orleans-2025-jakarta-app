package fr.formation.jakarta.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Définition d'un attribut personnalisé
        // dans l'objet Request.
        // Cet attribut sera lu par la page JSP
        req.setAttribute(
                "message",
                "Hello depuis le Servlet!"
        );

        // Récupération d'un dispatcher pour la page cible
        RequestDispatcher dispatcher = req.getRequestDispatcher(
                "index.js"
        );

        // Transmission de la requête à la page JSP
        dispatcher.forward(req, resp);
    }
}
