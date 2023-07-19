package br.com.banco.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "conta")
public class Account {

    @Id
    @Column(name = "id_conta")
    private Long accountId;

    @Column(name = "nome_responsavel")
    private String responsibleName;

    public Account() {
    }

    public Account(Long accountId, String responsibleName) {
        this.accountId = accountId;
        this.responsibleName = responsibleName;
    }

}
