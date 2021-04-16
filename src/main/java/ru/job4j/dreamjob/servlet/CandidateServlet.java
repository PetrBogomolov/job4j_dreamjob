package ru.job4j.dreamjob.servlet;

import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.stores.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CandidateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", PsqlStore.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        PsqlStore.instOf().saveCandidate(new Candidate(Integer.parseInt(req.getParameter("id")), req.getParameter("name")));
        resp.sendRedirect(req.getContextPath() + "/candidates.do");
    }
}
