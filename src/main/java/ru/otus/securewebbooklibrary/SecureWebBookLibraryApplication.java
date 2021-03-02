package ru.otus.securewebbooklibrary;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableMongock
public class SecureWebBookLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecureWebBookLibraryApplication.class, args);
    }

}
