package fr.formation.jakarta.controller;

import fr.formation.jakarta.model.entity.UserWithoutJPA;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/pebble-test")
public class PebbleInheritanceTest extends AbstractServlet{
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {

        UserWithoutJPA user = new UserWithoutJPA("Joe", "joe@user.com");
        List<UserWithoutJPA> users = new ArrayList<>();
        users.add(new UserWithoutJPA("Jane", "jane@user.com"));
        users.add(new UserWithoutJPA("Jack", "jack@user.com"));

        this.context.put("name", "Severn");
        this.context.put("userList", users);
        this.context.put("user", user);
        this.render(resp, "pebble.peb");
    }
}
