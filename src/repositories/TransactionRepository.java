package repositories;

import Components.Menu;
import enums.TransactionsType;
import interfaces.TransactionRepositoryInt;
import modules.Account;
import modules.Transaction;
import modules.User;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.UUID;

public class TransactionRepository implements TransactionRepositoryInt {
    private static HashMap<Integer, Transaction> Transactions = new HashMap<>();

    @Override
    public boolean save(Transaction transaction) {
        if(transaction.getType() == TransactionsType.DEPOSIT){
            Account result = AccountRepository.increaseAccountBalance(transaction.getAccountId(),transaction.getAmount());
            if (result != null){
                Transactions.put(Transactions.size(),transaction);
                return true;
            }else{
                return false;
            }
        } else if (transaction.getType() == TransactionsType.WITHDRAW) {
            Account result = AccountRepository.WithdrawFromAccountBalance(transaction.getAccountId(),transaction.getAmount());
            if (result != null){
                Transactions.put(Transactions.size(),transaction);
                return true;
            }else{
                return false;
            }
        }   else if (transaction.getType() == TransactionsType.TRANSFERIN) {
            boolean result = AccountRepository.TransferIN(transaction.getAccountId(),transaction.getAmount(),transaction.getCounterpartyAccountId());
            if (result){
                Transactions.put(Transactions.size(),transaction);
                return true;
            }else{
                return false;
            }
        }else if (transaction.getType() == TransactionsType.TRANSFEROUT) {
            boolean result = AccountRepository.TransferOUT(transaction.getAccountId(),transaction.getAmount(),transaction.getCounterpartyAccountId());
            if (result){
                Transactions.put(Transactions.size(),transaction);
                return true;
            }else{
                return false;
            }
        }
        return false;
    }



    @Override
    public HashMap<Integer, Transaction> getAllTransactions() {
        return Transactions;
    }
}
