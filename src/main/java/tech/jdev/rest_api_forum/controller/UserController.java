package tech.jdev.rest_api_forum.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import tech.jdev.rest_api_forum.controller.dto.RequestLoginDto;
import tech.jdev.rest_api_forum.controller.dto.ResponseLoginDto;
import tech.jdev.rest_api_forum.repository.AuthorRepository;
import tech.jdev.rest_api_forum.service.UserService;

import java.time.Instant;
import java.util.NoSuchElementException;

@RestController
public class UserController {

    private final JwtEncoder jwtEncoder;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthorRepository authorRepository;
    private final UserService userService;

    public UserController(JwtEncoder jwtEncoder, AuthorRepository authorRepository,
                          UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtEncoder = jwtEncoder;
        this.authorRepository = authorRepository;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDto> login(@RequestBody @Valid RequestLoginDto login) {
        var author = authorRepository.findByUsername(login.username())
                .orElseThrow(() -> new NoSuchElementException("Author username dont found"));

        if (userService.logisCorrect(login, bCryptPasswordEncoder, author)) {
            var expiresIn = 3000L;

            var claims = JwtClaimsSet.builder()
                    .issuer("rest-api-forum")
                    .subject(author.getId().toString())
                    .issuedAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(expiresIn))
                    .build();

            var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

            return ResponseEntity.ok(new ResponseLoginDto(jwtValue, expiresIn));
        }

        return ResponseEntity.notFound().build();
    }
}
