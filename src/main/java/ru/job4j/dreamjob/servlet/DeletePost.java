package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.stores.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeletePost extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        PsqlStore.instOf().deletePost(Integer.parseInt(id));
        req.getRequestDispatcher("/posts.do").forward(req, resp);
    }
}
