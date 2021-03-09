package ru.otus.securewebbooklibrary.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.securewebbooklibrary.domain.Author;
import ru.otus.securewebbooklibrary.domain.Book;
import ru.otus.securewebbooklibrary.domain.Genre;
import ru.otus.securewebbooklibrary.security.UserServiceImpl;
import ru.otus.securewebbooklibrary.service.AuthorServiceImpl;
import ru.otus.securewebbooklibrary.service.BookServiceImpl;
import ru.otus.securewebbooklibrary.service.GenreServiceImpl;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookServiceImpl bookService;
    @MockBean
    private AuthorServiceImpl authorService;
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
        when(bookService.getAll()).thenReturn(List.of
                (new Book("Modernist novel", new Author("James Joyce"), new Genre("Modernist novel")),
                        new Book("Book", new Author("Author"), new Genre("Genre"))));

        mockMvc.perform(post("/books/add")
                .param("book", "Book"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestSaveByStatus() throws Exception {
        when(bookService.getAll()).thenReturn(List.of
                (new Book("Modernist novel", new Author("James Joyce"), new Genre("Modernist novel")),
                        new Book("Book", new Author("Author"), new Genre("Genre"))));

        mockMvc.perform(post("/books/add")
                .param("book", "Book"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testAuthenticatedOnUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRedirectBecauseOfAnonymousUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/books"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetBookByTitleByStatus() throws Exception {
        when(bookService.getBookByTitle("Book")).thenReturn(new Book("Book", new Author("Author"),
                new Genre("Genre")));

        mockMvc.perform(get("/books/title/Book"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetBookByAuthorByStatus() throws Exception {
        when(bookService.getBookByAuthor("Author")).thenReturn(new Book("Book", new Author("Author"),
                new Genre("Genre")));

        mockMvc.perform(get("/books/author/Author"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetBookByGenreByStatus() throws Exception {
        when(bookService.getBookByGenre("Genre")).thenReturn(new Book("Book", new Author("Author"),
                new Genre("Genre")));

        mockMvc.perform(get("/books/genre/Genre"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetBookByCommentByStatus() throws Exception {
        when(bookService.getBookByComment("Comment")).thenReturn(new Book("Book", new Author("Author"),
                new Genre("Genre")));

        mockMvc.perform(get("/books/comment")
                .param("comment", "Comment"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void testGetAllByStatusByStatus() throws Exception {
        when(bookService.getAll()).thenReturn(List.of
                (new Book("Modernist novel", new Author("James Joyce"), new Genre("Modernist novel")),
                        new Book("Book", new Author("Author"), new Genre("Genre"))));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk());
    }

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void testUpdateByStatus() throws Exception {
        doNothing().when(bookService).updateBook
                ("Ulysses", "Book", "Author", "Genre");

        mockMvc.perform(post("/books/edit/Ulysses")
                .param("title", "Book")
                .param("authorName", "Author")
                .param("genreName", "Genre"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestUpdateByStatus() throws Exception {
        doNothing().when(bookService).updateBook
                ("Ulysses", "Book", "Author", "Genre");

        mockMvc.perform(post("/books/edit/Ulysses")
                .param("title", "Book")
                .param("authorName", "Author")
                .param("genreName", "Genre"))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(
            username = "User2",
            password = "Password2",
            authorities = {"ROLE_ADMIN"}
    )
    @Test
    void testDeleteByTitle() throws Exception {
        doNothing().when(bookService).deleteBookByTitle("Ulysses");

        mockMvc.perform(post("/books/edit/Ulysses"))
                .andExpect(status().isFound());
    }

    @WithMockUser(
            username = "User1",
            password = "Password1",
            authorities = {"ROLE_USER"}
    )
    @Test
    void negativeTestDeleteByTitle() throws Exception {
        doNothing().when(bookService).deleteBookByTitle("Ulysses");

        mockMvc.perform(post("/books/edit/Ulysses"))
                .andExpect(status().is4xxClientError());
    }
}
