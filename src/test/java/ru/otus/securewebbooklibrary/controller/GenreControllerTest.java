package ru.otus.securewebbooklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.securewebbooklibrary.domain.Genre;
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

    @Test
    void testSaveByStatus() throws Exception {
        when(genreService.getAll()).thenReturn(List.of(new Genre("Modernist novel"), new Genre("Genre")));

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/add")
                .param("genre", "Genre"))
                .andExpect(status().isFound());
    }

    @Test
    void testGetGenreByNameByStatus() throws Exception {
        when(genreService.getGenreByName("Genre")).thenReturn(new Genre("Genre"));

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/Genre"))
                .andExpect(status().isFound());
    }

    @Test
    void testGetAllByStatus() throws Exception {
        when(genreService.getAll()).thenReturn(List.of(new Genre("Modernist novel"), new Genre("Genre")));

        mockMvc.perform(MockMvcRequestBuilders.get("/genres"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateByStatus() throws Exception {
        when(genreService.updateGenre("Modernist novel", "Genre")).thenReturn("Genre was updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/edit/Modernist novel")
                .param("genre", "Genre"))
                .andExpect(status().isFound());
    }

    @Test
    void testDeleteByNameByStatus() throws Exception {
        when(genreService.deleteGenreByName("Modernist novel")).thenReturn("Modernist novel was deleted");

        mockMvc.perform(MockMvcRequestBuilders.post("/genres/Modernist novel"))
                .andExpect(status().isFound());
    }
}
