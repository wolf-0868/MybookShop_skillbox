package com.example.bookshop.data.dto.page;

import com.example.bookshop.data.dto.TransactionDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TransactionsPageDTO {

    @JsonProperty(value = "count")
    private Integer count;

    @JsonProperty(value = "transactions")
    private List<TransactionDTO> transactions;

    public TransactionsPageDTO(List<TransactionDTO> aTransactions) {
        transactions = aTransactions;
        count = aTransactions.size();
    }

}
