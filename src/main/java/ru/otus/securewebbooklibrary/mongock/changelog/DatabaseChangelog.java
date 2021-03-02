package ru.otus.securewebbooklibrary.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.securewebbooklibrary.domain.Author;
import ru.otus.securewebbooklibrary.domain.Book;
import ru.otus.securewebbooklibrary.domain.Comment;
import ru.otus.securewebbooklibrary.domain.Genre;
import ru.otus.securewebbooklibrary.repository.AuthorRepository;
import ru.otus.securewebbooklibrary.repository.BookRepository;
import ru.otus.securewebbooklibrary.repository.CommentRepository;
import ru.otus.securewebbooklibrary.repository.GenreRepository;

@ChangeLog
public class DatabaseChangelog {
    @ChangeSet(order = "001", id = "dropDb", runAlways = true, author = "Diatessaron")
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "002", id = "insertAuthor", runAlways = true, author = "Diatessaron")
    public void insertAuthor(AuthorRepository repository) {
        repository.save(new Author("James Joyce"));
    }

    @ChangeSet(order = "003", id = "insertGenre", runAlways = true, author = "Diatessaron")
    public void insertGenre(GenreRepository repository) {
        repository.save(new Genre("Modernist novel"));
    }

    @ChangeSet(order = "004", id = "insertBook", runAlways = true, author = "Diatessaron")
    public void insertBook(BookRepository repository) {
        repository.save(new Book("Ulysses", new Author("James Joyce"),
                new Genre("Modernist novel")));
    }

    @ChangeSet(order = "005", id = "insertComment", runAlways = true, author = "Diatessaron")
    public void insertComment(CommentRepository repository) {
        repository.save(new Comment("Published in 1922", "Ulysses"));
    }
}
