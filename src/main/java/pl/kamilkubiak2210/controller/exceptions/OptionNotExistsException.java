package pl.kamilkubiak2210.controller.exceptions;

public class OptionNotExistsException extends RuntimeException {
    public OptionNotExistsException(String message) {
        super(message);
    }
}