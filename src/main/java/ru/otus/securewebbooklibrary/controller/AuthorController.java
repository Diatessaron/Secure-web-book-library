package ru.otus.securewebbooklibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.securewebbooklibrary.domain.Author;
import ru.otus.securewebbooklibrary.service.AuthorService;

import java.util.List;

@Controller
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/authors/add")
    public String createPage() {
        return "authorSave";
    }

    @PostMapping("/authors/add")
    public String create(@RequestParam(name = "author") String authorName) {
        authorService.saveAuthor(authorName);
        return "redirect:/authors";
    }

    @GetMapping("/authors/{name}")
    public String getAuthorByName(@PathVariable String name, Model model) {
        model.addAttribute("author", authorService.getAuthorByName(name));
        return "author";
    }

    @GetMapping("/authors")
    public String getAll(Model model) {
        final List<Author> authors = authorService.getAll();
        model.addAttribute("authors", authors);
        return "authorList";
    }

    @GetMapping("/authors/edit/{oldAuthor}")
    public String editPage(@PathVariable String oldAuthor, Model model) {
        model.addAttribute("author", authorService.getAuthorByName(oldAuthor));
        return "authorEdit";
    }

    @PostMapping("/authors/edit/{oldAuthor}")
    public String edit(@PathVariable String oldAuthor, @RequestParam(name = "author") String authorName) {
        authorService.updateAuthor(oldAuthor, authorName);
        return "redirect:/authors";
    }

    @PostMapping("/authors/{author}")
    public String deleteByName(@PathVariable String author) {
        authorService.deleteAuthorByName(author);
        return "redirect:/authors";
    }
}
