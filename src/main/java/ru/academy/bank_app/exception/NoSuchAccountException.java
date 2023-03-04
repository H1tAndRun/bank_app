package ru.academy.bank_app.exception;

public class NoSuchAccountException extends RuntimeException {

    public NoSuchAccountException(String message) {
        super(message);
    }
}
