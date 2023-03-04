package ru.academy.bank_app.entity;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class Account {

    @NonNull
    private String accountNumber;

    @NonNull
    private Currency currency;

    @NonNull
    private String accountName;

    @NonNull
    private BigDecimal balance;

    @NonNull
    private boolean isOpen;
}
