package ru.job4j.dreamjob.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dreamjob.stores.Store;
import ru.job4j.dreamjob.model.Candidate;
import ru.job4j.dreamjob.stores.MemStore;
import ru.job4j.dreamjob.stores.PsqlStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class CandidateServletTest {

    @Test
    public void whenDoPostAddNewCandidate() throws ServletException, IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn(String.valueOf(0));
        when(req.getParameter("name")).thenReturn("Name");
        when(req.getParameter("city")).thenReturn(String.valueOf(1));
        new CandidateServlet().doPost(req, resp);
        List<Candidate> list = new ArrayList<>(store.findAllCandidates());
        assertThat(list.size(), is(1));
    }

    @Test
    public void whenDoPostUpdateCandidate() throws ServletException, IOException {
        Store store = MemStore.instOf();
        Candidate candidate = new Candidate(1, "name", "");
        store.saveCandidate(candidate);
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("id")).thenReturn(String.valueOf(candidate.getId()));
        when(req.getParameter("name")).thenReturn("update name");
        when(req.getParameter("city")).thenReturn(String.valueOf(candidate.getCityId()));
        new CandidateServlet().doPost(req, resp);
        List<Candidate> list = new ArrayList<>(store.findAllCandidates());
        assertThat(list.get(0).getName(), is("update name"));
    }
}
