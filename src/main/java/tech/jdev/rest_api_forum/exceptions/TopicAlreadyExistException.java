package tech.jdev.rest_api_forum.exceptions;

public class TopicAlreadyExistException extends RuntimeException {

    public TopicAlreadyExistException(String message) {
        super(message);
    }
}
