package ru.otus.securewebbooklibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.securewebbooklibrary.domain.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
