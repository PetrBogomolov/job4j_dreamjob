package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.stores.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

public class DeleteCandidate extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        File file = new File("c:\\images\\"  + req.getParameter("id"));
        if (file.exists()) {
            file.delete();
        }
        PsqlStore.instOf().deleteCandidate(Integer.parseInt(id));
        req.getRequestDispatcher("/candidates.do").forward(req, resp);
    }
}
