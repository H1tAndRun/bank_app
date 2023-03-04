package ru.academy.bank_app.service;

import org.springframework.stereotype.Service;
import ru.academy.bank_app.entity.Account;
import ru.academy.bank_app.entity.Currency;
import ru.academy.bank_app.exception.NoSuchAccountException;
import ru.academy.bank_app.exception.NotEnoughMoneyException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    private Integer countAccount = 0;

    public Account createAccount(String nameClient, String currencyName) {
        String sufNumber = "40817";
        String accountNumber = sufNumber + Currency.valueOf(currencyName).getCurrencyCode() + (++countAccount);
        Account account = Account
                .builder()
                .accountName(createNameClient(nameClient))
                .accountNumber(accountNumber)
                .balance(new BigDecimal(0))
                .currency(Currency.valueOf(currencyName))
                .isOpen(true)
                .build();
        if (accounts.containsKey(accountNumber)) {
            throw new NoSuchAccountException("Счет уже есть");
        }
        accounts.put(accountNumber, account);
        return account;
    }

    public boolean closeAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new NoSuchAccountException("Счет не найден");
        }
        Account account = accounts.get(accountNumber);
        account.setBalance(new BigDecimal(0));
        account.setOpen(false);
        return true;
    }

    public boolean reduceMoneyFromBalance(String accountNumber, BigDecimal sum, String currencyName) {
        if (isThereAmount(accountNumber, sum, currencyName)) {
            Account account = accounts.get(accountNumber);
            account.setBalance(account.getBalance()
                    .subtract(convertMoney(currencyName, sum, account.getCurrency().toString())));
        } else {
            throw new NotEnoughMoneyException("Сумма списания больше суммы на балансе");
        }
        return true;
    }

    public boolean addMoneyFromBalance(String accountNumber, BigDecimal sum, String currencyName) {
        if (sum.compareTo(new BigDecimal(0)) <= 0) {
            throw new NotEnoughMoneyException("Пополнение не может быть отрицательным");
        }
        Account account = accounts.get(accountNumber);
        account.setBalance(account.getBalance().add(convertMoney(currencyName,
                sum, account.getCurrency().toString())));
        return true;
    }

    public boolean transferMoneyFromAccountToAccount(String numberFrom, String numberTo, BigDecimal sum) {
        String currencyFrom = accounts.get(numberFrom).getCurrency().toString();
        String currencyTo = accounts.get(numberTo).getCurrency().toString();
        if (isThereAmount(numberFrom, sum, currencyFrom)) {
            reduceMoneyFromBalance(numberFrom, sum, currencyFrom);
            BigDecimal sumAdd = convertMoney(currencyFrom, sum, currencyTo);
            addMoneyFromBalance(numberTo, sumAdd, currencyTo);
            return true;
        } else {
            throw new NotEnoughMoneyException("Не хватает денег для перевода");
        }
    }

    public Account getInfoAccount(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            throw new NoSuchAccountException("Счет не найден");
        }
        return accounts.get(accountNumber);
    }

    private BigDecimal convertMoney(String currencyFrom, BigDecimal sum, String currencyTo) {
        BigDecimal from = sum.multiply(Currency.valueOf(currencyFrom).getNominal());
        BigDecimal to = from.divide(Currency.valueOf(currencyTo)
                .getNominal(), 2, RoundingMode.HALF_UP);
        return to;
    }

    private boolean isThereAmount(String accountNumber, BigDecimal sum, String currencyName) {
        Account account = accounts.get(accountNumber);
        BigDecimal moneyAccount = convertMoney(account.getCurrency().toString(),
                account.getBalance(), currencyName);
        return moneyAccount.compareTo(sum) >= 0;
    }

    private String createNameClient(String nameClient) {
        String[] clientArr = nameClient.split(" ");
        return clientArr[0] + " " + clientArr[1].charAt(0) + clientArr[2].charAt(0);
    }
}
