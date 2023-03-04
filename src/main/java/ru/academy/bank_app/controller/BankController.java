package ru.academy.bank_app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.academy.bank_app.dto.AccountDtoRq;
import ru.academy.bank_app.dto.AccountTransferDtoRq;
import ru.academy.bank_app.service.AccountService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bank/api")
public class BankController {

    private final AccountService accountService;

    @PostMapping("/account/create")
    public ResponseEntity accountCreate(@RequestBody AccountDtoRq accountDtoRq) {
        return ResponseEntity.status(200)
                .body(accountService.createAccount(accountDtoRq.getClientName(), accountDtoRq.getCurrencyName()));
    }

    @PostMapping("/account/close")
    public ResponseEntity accountClose(@RequestBody AccountDtoRq accountDtoRq) {
        return ResponseEntity.status(200).body(accountService.closeAccount(accountDtoRq.getAccountNumber()));
    }

    @PutMapping("/account/payment")
    public ResponseEntity reduceMoneyFromAccount(@RequestBody AccountDtoRq accountDtoRq) {
        return ResponseEntity
                .status(200)
                .body(accountService.reduceMoneyFromBalance(accountDtoRq.getAccountNumber(),
                        accountDtoRq.getSum(),
                        accountDtoRq.getCurrencyName()));
    }

    @PutMapping("/account/income")
    public ResponseEntity incomeMoneyFromAccount(@RequestBody AccountDtoRq accountDtoRq) {
        return ResponseEntity
                .status(200)
                .body(accountService.addMoneyFromBalance(accountDtoRq.getAccountNumber(),
                        accountDtoRq.getSum(),
                        accountDtoRq.getCurrencyName()));
    }

    @PutMapping("/account/transfer")
    public ResponseEntity transferMoneyFromAccountToAccount(@RequestBody AccountTransferDtoRq accountTransferDtoRq) {
        return ResponseEntity
                .status(200)
                .body(accountService.transferMoneyFromAccountToAccount(accountTransferDtoRq.getNumberFrom(),
                        accountTransferDtoRq.getNumberTo(),
                        accountTransferDtoRq.getSum()));
    }

    @GetMapping("/account")
    public ResponseEntity getInfoByAccountNumber(@RequestParam String accountNumber) {
        return ResponseEntity.status(200).body(accountService.getInfoAccount(accountNumber));
    }
}
