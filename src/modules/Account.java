package modules;

//accountId: String, ownerUserId: UUID, balance: BigDecimal(2), createdAt: Instant, active: boolean


import Components.Menu;
import enums.TransactionsType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

public class Account {
    private String accountId ;
    private UUID ownerUserId = Menu.CurrentUser.getId();
    private BigDecimal balance;
    private Instant createdAt = Instant.now();
    private Boolean active = true;
    private TransactionsType type;
    public static UUID AccountID ;
    private List<String> transactions = new ArrayList<>();

    public Account(String accountId , BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public void addSomething(Account other) {
        this.transactions.addAll(other.transactions);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public UUID getOwnerUserId() {
        return ownerUserId;
    }

    public void setOwnerUserId(UUID ownerUserId) {
        this.ownerUserId = ownerUserId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
