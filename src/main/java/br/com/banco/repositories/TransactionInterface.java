package br.com.banco.repositories;

import br.com.banco.entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionInterface {

    List<Transaction> find(Long accountId, String name, LocalDateTime initialDateTime, LocalDateTime finalDateTime);

}
