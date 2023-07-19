package br.com.banco.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transferencia")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_transferencia", columnDefinition = "TIMESTAMP")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ssXXX")
    private LocalDateTime transferDate;

    @Column(name = "valor")
    private BigDecimal amount;

    @Column(name = "tipo")
    private String type;

    @Column(name = "nome_operador_transacao")
    private String transactionOperatorName;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Account account;


    public Transaction() {
    }

    public Transaction(Long id, LocalDateTime transferDate, BigDecimal amount, String type, String transactionOperatorName, Account account) {
        this.id = id;
        this.transferDate = transferDate;
        this.amount = amount;
        this.type = type;
        this.transactionOperatorName = transactionOperatorName;
        this.account = account;
    }
}
