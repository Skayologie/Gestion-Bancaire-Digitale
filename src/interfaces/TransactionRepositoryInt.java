package interfaces;

import modules.Transaction;
import modules.User;

import java.util.HashMap;

public interface TransactionRepositoryInt {
    boolean save(Transaction transaction);
    HashMap<Integer, Transaction> getAllTransactions();
}
