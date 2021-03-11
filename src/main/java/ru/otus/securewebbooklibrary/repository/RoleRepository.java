package ru.otus.securewebbooklibrary.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.securewebbooklibrary.domain.Role;

public interface RoleRepository extends MongoRepository<Role, Long> {

}
