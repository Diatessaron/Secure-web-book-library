package ru.otus.securewebbooklibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.securewebbooklibrary.domain.Genre;
import ru.otus.securewebbooklibrary.service.GenreService;

import java.util.List;

@Controller
public class GenreController {
    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres/add")
    public String createPage() {
        return "genreSave";
    }

    @PostMapping("/genres/add")
    public String create(@RequestParam(name = "genre") String genreName) {
        genreService.saveGenre(genreName);
        return "redirect:/genres";
    }

    @GetMapping("/genres/{genre}")
    public String getGenreByName(@PathVariable String genre, Model model) {
        model.addAttribute("genre", genreService.getGenreByName(genre));
        return "genre";
    }

    @GetMapping("/genres")
    public String getAll(Model model) {
        final List<Genre> genres = genreService.getAll();
        model.addAttribute("genres", genres);
        return "genreList";
    }

    @GetMapping("/genres/edit/{oldGenre}")
    public String editPage(@PathVariable String oldGenre, Model model) {
        model.addAttribute("genre", genreService.getGenreByName(oldGenre));
        return "genreEdit";
    }

    @PostMapping("/genres/edit/{oldGenre}")
    public String update(@PathVariable String oldGenre, @RequestParam(name = "genre") String genreName) {
        genreService.updateGenre(oldGenre, genreName);
        return "redirect:/genres";
    }

    @PostMapping("/genres/{genre}")
    public String deleteByName(@PathVariable String genre) {
        genreService.deleteGenreByName(genre);
        return "redirect:/genres";
    }
}
