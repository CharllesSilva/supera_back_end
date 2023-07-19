package br.com.banco.controllers.dtos;

import br.com.banco.entities.Transaction;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class ResultOfTransactionDTO {
    private BigDecimal total;
    private BigDecimal period_total;
    private List<TransactionDTO> transactions;

    public ResultOfTransactionDTO convertToResultOfTransaction(List<Transaction> transactions, BigDecimal total, BigDecimal periodTotal) {
        ResultOfTransactionDTO result = new ResultOfTransactionDTO();
        result.setTotal(total);
        result.setPeriod_total(periodTotal);
        result.setTransactions(convertToDTOList(transactions));
        return result;
    }

    public List<TransactionDTO> convertToDTOList(List<Transaction> transactions) {
        return transactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TransactionDTO convertToDTO(Transaction transaction) {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId().toString());
        dto.setDate(transaction.getTransferDate().toString());
        dto.setValence(getValence(transaction.getAmount()));
        dto.setType_of_transaction(transaction.getType());
        dto.setOperator_name(transaction.getTransactionOperatorName());
        return dto;
    }

    private String getValence(BigDecimal amount) {
        return (amount.compareTo(BigDecimal.ZERO) >= 0) ? "Positive" : "Negative";
    }
}
