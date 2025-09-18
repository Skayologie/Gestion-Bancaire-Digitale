package interfaces;

import modules.Account;
import modules.User;

public interface AccountInterface {
    boolean save(Account account);
    void getAll();
}
