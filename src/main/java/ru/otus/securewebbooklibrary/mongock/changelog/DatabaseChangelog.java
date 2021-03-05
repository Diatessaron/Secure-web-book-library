package ru.otus.securewebbooklibrary.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.otus.securewebbooklibrary.domain.*;
import ru.otus.securewebbooklibrary.repository.*;

@ChangeLog
public class DatabaseChangelog {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public DatabaseChangelog() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

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

    @ChangeSet(order = "006", id = "insertUsers", runAlways = true, author = "Diatessaron")
    public void insertUsers(UserRepository repository) {
        repository.save(new User("User1", bCryptPasswordEncoder.encode("Password1")));
        repository.save(new User("User2", bCryptPasswordEncoder.encode("Password2")));
    }
}
