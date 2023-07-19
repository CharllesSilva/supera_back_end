package br.com.banco.controllers;

import br.com.banco.controllers.dtos.ResultOfTransactionDTO;
import br.com.banco.entities.Transaction;
import br.com.banco.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping(value = "/transactions")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @GetMapping
    public ResponseEntity<ResultOfTransactionDTO> find(
            @RequestParam(name = "account.id", required = false) Long accountID,
            @RequestParam(required = false) String name,
            @RequestParam(name = "initial.date", required = false) String initialDate,
            @RequestParam(name = "final.date", required = false) String finalDate
    ) {
        ResultOfTransactionDTO resultOfTransactionDTO = new ResultOfTransactionDTO();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime initialDateTime = null;
        LocalDateTime finalDateTime = null;

        if (initialDate != null && finalDate != null && !initialDate.isEmpty() && !finalDate.isEmpty()) {
            initialDateTime = LocalDateTime.parse(initialDate + " 00:00:00", formatter);
            finalDateTime = LocalDateTime.parse(finalDate + " 23:59:59", formatter);
        }

        List<Transaction> list = service.find(accountID, name, initialDateTime, finalDateTime);
        BigDecimal periodTotal = service.calculateTotalExpensePeriod(list, initialDate, finalDate);
        BigDecimal valueTotal = service.calculateTotalExpense(list);


        ResultOfTransactionDTO responseBody = resultOfTransactionDTO.convertToResultOfTransaction(list, valueTotal, periodTotal);

        return ResponseEntity.ok().body(responseBody);
    }
}
