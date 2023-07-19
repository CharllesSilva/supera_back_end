package br.com.banco.services;


import br.com.banco.entities.Transaction;
import br.com.banco.enums.TransactionType;
import br.com.banco.repositories.TransactionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionInterface repository;

    @Transactional(readOnly = true)
    public List<Transaction> find(Long accountId, String name, LocalDateTime initialDateTime, LocalDateTime finalDateTime) {
        return repository.find(accountId, name, initialDateTime, finalDateTime);
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotalExpensePeriod(List<Transaction> transactions, String startDate, String endDate) {
        if (startDate == null || endDate == null) {
            return BigDecimal.ZERO;
        }

        LocalDateTime startDateTime = parseToLocalDateTime(startDate, false);
        LocalDateTime endDateTime = parseToLocalDateTime(endDate, true);

        BigDecimal totalExpensePeriod = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            LocalDateTime transactionDate = transaction.getTransferDate();
            if (transactionDate.isAfter(startDateTime) && transactionDate.isBefore(endDateTime.plusSeconds(1))) {
                BigDecimal amount = transaction.getAmount();

                if (transaction.getType().equals(TransactionType.SAQUE.getType())) {
                    totalExpensePeriod = totalExpensePeriod.subtract(amount);
                } else if (transaction.getType().equals(TransactionType.TRANSFERENCIA.getType())) {
                    totalExpensePeriod = totalExpensePeriod.add(amount);
                }
            }
        }

        return totalExpensePeriod;
    }

    @Transactional(readOnly = true)
    public BigDecimal calculateTotalExpense(List<Transaction> transactions) {
        BigDecimal totalExpense = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            BigDecimal amount = transaction.getAmount();

            if (transaction.getType().equals(TransactionType.SAQUE.getType())) {
                totalExpense = totalExpense.subtract(amount);
            } else if (transaction.getType().equals(TransactionType.TRANSFERENCIA.getType())) {
                totalExpense = totalExpense.add(amount);
            }
        }

        return totalExpense;
    }

    private LocalDateTime parseToLocalDateTime(String dateTimeString, boolean isEndOfDay) {
        LocalDate localDate = LocalDate.parse(dateTimeString, DateTimeFormatter.ISO_DATE);
        if (isEndOfDay) {
            return LocalDateTime.of(localDate, LocalTime.MAX);
        } else {
            return LocalDateTime.of(localDate, LocalTime.MIN);
        }
    }

}
