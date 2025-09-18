package interfaces;

import modules.User;

public interface UserRepository {
    boolean save(User user);
    void getAll();
    User checkEmailExists(String email);
    User findByEmail(String email);
}
