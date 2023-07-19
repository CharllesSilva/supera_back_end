package br.com.banco.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO {

    private String id;
    private String date;
    private String valence;
    private String type_of_transaction;
    private String operator_name;

}