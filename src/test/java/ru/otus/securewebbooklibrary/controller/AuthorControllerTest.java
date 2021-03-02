package ru.otus.securewebbooklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.securewebbooklibrary.domain.Author;
import ru.otus.securewebbooklibrary.service.AuthorServiceImpl;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorServiceImpl authorService;

    @Test
    void shouldGetCorrectStatusAfterAuthorCreation() throws Exception {
        when(authorService.getAll()).thenReturn(List.of(new Author("James Joyce"), new Author("Author")));

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/add")
                .param("author", "Author"))
                .andExpect(status().isFound());
    }

    @Test
    void testGetAuthorByNameByCorrectStatus() throws Exception {
        when(authorService.getAuthorByName("Author")).thenReturn(new Author("Author"));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/Author"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllByCorrectStatus() throws Exception {
        when(authorService.getAll()).thenReturn(List.of(new Author("James Joyce"), new Author("Author")));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateByCorrectStatus() throws Exception {
        when(authorService.updateAuthor("James Joyce", "Author")).thenReturn("Author was updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/edit/James Joyce")
                .param("author", "Author"))
                .andExpect(status().isFound());
    }

    @Test
    void deleteByName() throws Exception {
        when(authorService.deleteAuthorByName("Author")).thenReturn("Author was deleted");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/Author"))
                .andExpect(status().isFound());
    }
}
