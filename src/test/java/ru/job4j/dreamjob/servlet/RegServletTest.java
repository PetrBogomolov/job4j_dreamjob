package ru.job4j.dreamjob.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import ru.job4j.dreamjob.stores.Store;
import ru.job4j.dreamjob.stores.MemStore;
import ru.job4j.dreamjob.stores.PsqlStore;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class RegServletTest {

    @Test
    public void whenDoPostRegisteredNewUser() throws ServletException, IOException {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        when(PsqlStore.instOf()).thenReturn(store);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("nameUser")).thenReturn("name");
        when(req.getParameter("emailUser")).thenReturn("email@");
        when(req.getParameter("passwordUser")).thenReturn("password");
        new RegServlet().doPost(req, resp);
        assertThat(store.findUserByEmail("email@").getName(), is("name"));
    }
}
