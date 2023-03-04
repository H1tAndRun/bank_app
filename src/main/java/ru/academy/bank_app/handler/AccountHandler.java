package ru.academy.bank_app.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.academy.bank_app.exception.NoSuchAccountException;
import ru.academy.bank_app.exception.NotEnoughMoneyException;

@RestControllerAdvice
public class AccountHandler {

    @ExceptionHandler
    public ResponseEntity handle(NoSuchAccountException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity handle(NotEnoughMoneyException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
