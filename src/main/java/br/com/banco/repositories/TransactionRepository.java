package br.com.banco.repositories;

import br.com.banco.entities.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TransactionRepository implements TransactionInterface {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Transaction> find(Long accountId, String name, LocalDateTime initialDateTime, LocalDateTime finalDateTime) {

        StringBuilder queryBuilder = new StringBuilder("SELECT t FROM Transaction t WHERE 1 = 1");
        Map<String, Object> params = new HashMap<>();

        if (accountId != null) {
            queryBuilder.append(" AND t.account.accountId = :accountID");
            params.put("accountID", accountId);
        }

        if (name != null) {
            queryBuilder.append(" AND t.transactionOperatorName = :name");
            params.put("name", name);
        }

        if (initialDateTime != null && finalDateTime != null) {
            queryBuilder.append(" AND t.transferDate BETWEEN :initialDate AND :finalDate");
            params.put("initialDate", initialDateTime);
            params.put("finalDate", finalDateTime);

        }

        TypedQuery<Transaction> query = entityManager.createQuery(queryBuilder.toString(), Transaction.class);

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        return query.getResultList();
    }
}
