package tech.jdev.rest_api_forum.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tech.jdev.rest_api_forum.controller.dto.RequestLoginDto;
import tech.jdev.rest_api_forum.entity.Author;
import tech.jdev.rest_api_forum.repository.AuthorRepository;

@Service
public class UserService {

    private final AuthorRepository authorRepository;

    public UserService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public boolean logisCorrect(RequestLoginDto loginDto, PasswordEncoder passwordEncoder, Author author) {
        return passwordEncoder.matches(loginDto.password(), author.getPassword());
    }
}
