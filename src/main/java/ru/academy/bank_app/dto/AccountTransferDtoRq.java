package ru.academy.bank_app.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Builder
@Data
public class AccountTransferDtoRq {

    private String numberFrom;

    private String numberTo;

    private BigDecimal sum;
}
