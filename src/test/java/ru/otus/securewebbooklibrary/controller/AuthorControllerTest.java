package ru.otus.securewebbooklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.securewebbooklibrary.domain.Author;
import ru.otus.securewebbooklibrary.security.UserServiceImpl;
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
    @MockBean
    private UserServiceImpl userService;

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void shouldGetCorrectStatusAfterAuthorCreation() throws Exception {
        when(authorService.getAll()).thenReturn(List.of(new Author("James Joyce"), new Author("Author")));

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/add")
                .param("author", "Author"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeGetCorrectStatusAfterAuthorCreation() throws Exception {
        when(authorService.getAll()).thenReturn(List.of(new Author("James Joyce"), new Author("Author")));

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/add")
                .param("author", "Author"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testAuthenticatedOnUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectBecauseOfAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetAuthorByNameByCorrectStatus() throws Exception {
        when(authorService.getAuthorByName("Author")).thenReturn(new Author("Author"));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors/Author"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetAllByCorrectStatus() throws Exception {
        when(authorService.getAll()).thenReturn(List.of(new Author("James Joyce"), new Author("Author")));

        mockMvc.perform(MockMvcRequestBuilders.get("/authors"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void testUpdateByCorrectStatus() throws Exception {
        when(authorService.updateAuthor("James Joyce", "Author")).thenReturn("Author was updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/edit/James Joyce")
                .param("author", "Author"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestUpdateByCorrectStatus() throws Exception {
        when(authorService.updateAuthor("James Joyce", "Author")).thenReturn("Author was updated");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/edit/James Joyce")
                .param("author", "Author"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void testDeleteByName() throws Exception {
        when(authorService.deleteAuthorByName("Author")).thenReturn("Author was deleted");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/Author"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestDeleteByName() throws Exception {
        when(authorService.deleteAuthorByName("Author")).thenReturn("Author was deleted");

        mockMvc.perform(MockMvcRequestBuilders.post("/authors/Author"))
                .andExpect(status().is4xxClientError());
    }
}
