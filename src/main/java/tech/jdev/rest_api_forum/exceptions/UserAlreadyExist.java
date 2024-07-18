package tech.jdev.rest_api_forum.exceptions;

public class UserAlreadyExist extends RuntimeException {

    public UserAlreadyExist(String msg) {
        super(msg);
    }
}
