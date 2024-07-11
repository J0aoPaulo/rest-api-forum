package tech.jdev.rest_api_forum.infra;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tech.jdev.rest_api_forum.exceptions.TopicAlreadyExistException;

import java.util.List;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> validationError(MethodArgumentNotValidException ex) {
        var validationErrors = ex.getFieldErrors();

        return ResponseEntity
                .badRequest()
                .body(validationErrors.stream()
                        .map(ValidationError::new)
                        .toList());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Void> elementNotFoundError(NoSuchElementException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(TopicAlreadyExistException.class)
    public ResponseEntity<ErrorMessageDto> topicAlreadyExistError(TopicAlreadyExistException ex) {
        return ResponseEntity.badRequest().body(new ErrorMessageDto(ex.getLocalizedMessage()));
    }

    private record ValidationError(String field, String message) {
        public ValidationError(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }

    private record ErrorMessageDto(String error) {}
}