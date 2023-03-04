package ru.academy.bank_app.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class AccountDtoRq {

    private String accountNumber;

    private String currencyName;

    private String clientName;

    private String accountName;

    private BigDecimal sum;
}
