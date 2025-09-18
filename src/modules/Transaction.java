package modules;
//id: UUID, timestamp: Instant, accountId: String,


import enums.TransactionsType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public class Transaction {
    private UUID id = UUID.randomUUID();
    private Instant timestamp = Instant.now();
    private String accountId;
    private TransactionsType type;
    private BigDecimal amount;
    private String counterpartyAccountId = null ;
    private String description = null ;

    public Transaction(String accountId, BigDecimal amount, TransactionsType type , String description){
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.description = description;
    }

    public Transaction(String accountId, BigDecimal amount, TransactionsType type , String description , String counterpartyAccountId ){
        this.accountId = accountId;
        this.amount = amount;
        this.type = type;
        this.description = description;
        this.counterpartyAccountId = counterpartyAccountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getCounterpartyAccountId() {
        return counterpartyAccountId;
    }

    public void setCounterpartyAccountId(String counterpartyAccountId) {
        this.counterpartyAccountId = counterpartyAccountId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionsType getType() {
        return type;
    }

    public void setType(TransactionsType type) {
        this.type = type;
    }
}
