package br.com.banco.services;

import br.com.banco.entities.Account;
import br.com.banco.entities.Transaction;
import br.com.banco.enums.TransactionType;
import br.com.banco.repositories.TransactionInterface;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TransactionServiceTest {

    @Mock
    private TransactionInterface repository;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void findAll() {
        Account account1 = new Account(1L, "123456");
        Account account2 = new Account(2L, "654321");

        Transaction transaction1 = new Transaction(1L, LocalDateTime.parse("2023-01-01T10:00:00"), BigDecimal.valueOf(100), "SAQUE", "Operador A", account1);
        Transaction transaction2 = new Transaction(2L, LocalDateTime.parse("2023-01-02T15:30:00"), BigDecimal.valueOf(200), "TRANSFERENCIA", "Operador B", account2);

        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        // Configuração do mock
        when(repository.find(null, null, null, null)).thenReturn(expectedTransactions);

        // Chamada do método
        List<Transaction> actualTransactions = transactionService.find(null, null, null, null);

        // Verificação dos resultados
        verify(repository).find(null, null, null, null);
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void findById() {
        Long transactionId = 1L;
        Transaction expectedTransaction = new Transaction(transactionId, LocalDateTime.parse("2023-01-01T10:00:00"), BigDecimal.valueOf(100), "SAQUE", "Operador A", new Account());

        when(repository.find(transactionId, null, null, null)).thenReturn(Collections.singletonList(expectedTransaction));

        List<Transaction> actualTransactions = transactionService.find(transactionId, null, null, null);

        verify(repository).find(transactionId, null, null, null);
        assertEquals(Collections.singletonList(expectedTransaction), actualTransactions);
    }

    @Test
    public void findByName() {
        String name = "Operador A";

        Transaction transaction1 = new Transaction(1L, LocalDateTime.parse("2023-01-01T10:00:00"), BigDecimal.valueOf(100), "SAQUE", name, new Account());
        Transaction transaction2 = new Transaction(2L, LocalDateTime.parse("2023-02-01T15:30:00"), BigDecimal.valueOf(200), "TRANSFERENCIA", name, new Account());

        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(repository.find(null, name, null, null)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionService.find(null, name, null, null);

        verify(repository).find(null, name, null, null);
        assertEquals(expectedTransactions, actualTransactions);
    }

    @Test
    public void findByDateInterval() {
        String startDate = "2023-01-01";
        String endDate = "2023-02-28";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime initialDateTime = null;
        LocalDateTime finalDateTime = null;

        initialDateTime = LocalDateTime.parse(startDate + " 00:00:00", formatter);
        finalDateTime = LocalDateTime.parse(endDate + " 23:59:59", formatter);


        Transaction transaction1 = new Transaction(1L, LocalDateTime.parse("2023-01-15T08:00:00"), BigDecimal.valueOf(100), "SAQUE", "Operador A", new Account());
        Transaction transaction2 = new Transaction(2L, LocalDateTime.parse("2023-02-10T14:30:00"), BigDecimal.valueOf(200), "TRANSFERENCIA", "Operador B", new Account());

        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(repository.find(null, null, initialDateTime, finalDateTime)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionService.find(null, null, initialDateTime, finalDateTime);

        verify(repository).find(null, null, initialDateTime, finalDateTime);
        assertEquals(expectedTransactions, actualTransactions);
    }


    @Test
    public void testCalculateTotalExpensePeriodForSaque() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("100"));
        transaction1.setType(TransactionType.SAQUE.getType());
        transaction1.setTransferDate(LocalDateTime.of(2023, 7, 1, 10, 0));

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("50"));
        transaction2.setType(TransactionType.SAQUE.getType());
        transaction2.setTransferDate(LocalDateTime.of(2023, 7, 1, 10, 0));

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        String startDate = "2023-07-01";
        String endDate = "2023-07-02";

        BigDecimal result = transactionService.calculateTotalExpensePeriod(transactions, startDate, endDate);

        assertEquals(new BigDecimal("-150"), result);
    }

    @Test
    public void testCalculateTotalExpensePeriodForTransferencia() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("200"));
        transaction1.setType(TransactionType.TRANSFERENCIA.getType());
        transaction1.setTransferDate(LocalDateTime.of(2023, 7, 1, 10, 0));

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("50"));
        transaction2.setType(TransactionType.TRANSFERENCIA.getType());
        transaction2.setTransferDate(LocalDateTime.of(2023, 7, 1, 10, 0));

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);
        String startDate = "2023-07-01";
        String endDate = "2023-07-02";

        BigDecimal result = transactionService.calculateTotalExpensePeriod(transactions, startDate, endDate);

        assertEquals(new BigDecimal("250"), result);
    }


    @Test
    public void testCalculateTotalExpenseForSaque() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("100"));
        transaction1.setType(TransactionType.SAQUE.getType());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("50"));
        transaction2.setType(TransactionType.SAQUE.getType());

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        BigDecimal result = transactionService.calculateTotalExpense(transactions);

        assertEquals(new BigDecimal("-150"), result);
    }


    @Test
    public void testCalculateTotalExpenseForTransferencia() {
        Transaction transaction1 = new Transaction();
        transaction1.setAmount(new BigDecimal("200"));
        transaction1.setType(TransactionType.TRANSFERENCIA.getType());

        Transaction transaction2 = new Transaction();
        transaction2.setAmount(new BigDecimal("50"));
        transaction2.setType(TransactionType.TRANSFERENCIA.getType());

        List<Transaction> transactions = Arrays.asList(transaction1, transaction2);

        BigDecimal result = transactionService.calculateTotalExpense(transactions);

        assertEquals(new BigDecimal("250"), result);
    }
}
