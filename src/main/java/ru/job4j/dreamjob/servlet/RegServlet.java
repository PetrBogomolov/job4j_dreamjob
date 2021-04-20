package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.stores.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("reg.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PsqlStore.instOf().saveUser(
                new User(0, req.getParameter("name"), req.getParameter("email"), req.getParameter("password"))
        );
        resp.sendRedirect(req.getContextPath() + "/posts.do");
    }
}
