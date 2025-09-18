package services;

import enums.TransactionsType;
import interfaces.TransactionRepositoryInt;
import modules.Transaction;
import repositories.AccountRepository;
import repositories.TransactionRepository;

import java.math.BigDecimal;

public class TransactionService {
    private TransactionRepositoryInt repository;

    public TransactionService(TransactionRepository transactionRepo){
        this.repository = transactionRepo;
    }

    public boolean deposit(String AccountID , BigDecimal amount, TransactionsType TransactionType, String Description){
        Transaction transaction = new Transaction(AccountID,amount,TransactionType,Description);
        if (this.repository.save(transaction)){
            return true;
        }
        return false;
    }

    public boolean withdraw(String AccountID , BigDecimal amount){
        Transaction transaction = new Transaction(AccountID,amount,TransactionsType.WITHDRAW,"THIS IS THE Withdraw");
        if (this.repository.save(transaction)){
            return true;
        }
        return false;
    }

    public boolean transferIN(String AccountID ,String counterpartyAccountId, BigDecimal amount){
        Transaction transaction = new Transaction(AccountID,amount,TransactionsType.TRANSFERIN,"THIS IS THE TransferIN",counterpartyAccountId);
        if (this.repository.save(transaction)){
            return true;
        }
        return false;
    }

    public boolean transferOUT(String AccountID ,String counterpartyAccountId, BigDecimal amount){
        Transaction transaction = new Transaction(AccountID,amount,TransactionsType.TRANSFEROUT,"THIS IS THE TransferOUT",counterpartyAccountId);
        if (this.repository.save(transaction)){
            return true;
        }
        return false;
    }
}
