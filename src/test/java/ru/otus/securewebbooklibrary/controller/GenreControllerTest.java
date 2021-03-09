package ru.otus.securewebbooklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.securewebbooklibrary.domain.Genre;
import ru.otus.securewebbooklibrary.security.UserServiceImpl;
import ru.otus.securewebbooklibrary.service.GenreServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreServiceImpl genreService;
    @MockBean
    private UserServiceImpl userService;

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void testSaveByStatus() throws Exception {
        when(genreService.getAll()).thenReturn(List.of(new Genre("Modernist novel"), new Genre("Genre")));

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/add")
                .param("genre", "Genre"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestSaveByStatus() throws Exception {
        when(genreService.getAll()).thenReturn(List.of(new Genre("Modernist novel"), new Genre("Genre")));

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/add")
                .param("genre", "Genre"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testAuthenticatedOnUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectBecauseOfAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetGenreByNameByStatus() throws Exception {
        when(genreService.getGenreByName("Genre")).thenReturn(new Genre("Genre"));

        mockMvc.perform(MockMvcRequestBuilders.get("/genres/Genre"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetAllByStatus() throws Exception {
        when(genreService.getAll()).thenReturn(List.of(new Genre("Modernist novel"), new Genre("Genre")));

        mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void testUpdateByStatus() throws Exception {
        when(genreService.updateGenre("Modernist novel", "Genre")).thenReturn("Genre was updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/edit/Modernist novel")
                .param("genre", "Genre"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestUpdateByStatus() throws Exception {
        when(genreService.updateGenre("Modernist novel", "Genre")).thenReturn("Genre was updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/edit/Modernist novel")
                .param("genre", "Genre"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void testDeleteByNameByStatus() throws Exception {
        when(genreService.deleteGenreByName("Modernist novel")).thenReturn("Modernist novel was deleted");

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/Modernist novel"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestDeleteByNameByStatus() throws Exception {
        when(genreService.deleteGenreByName("Modernist novel")).thenReturn("Modernist novel was deleted");

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/Modernist novel"))
                .andExpect(status().is4xxClientError());
    }
}
