package services;

import interfaces.UserRepository;
import modules.Account;
import modules.User;
import repositories.AccountRepository;

import java.util.Objects;

import static Components.PasswordHasher.checkPassword;

public class AuthService {

    private UserRepository repository ;

    public AuthService (UserRepository repository){
        this.repository = repository;
    }

    public boolean register(User user){
        boolean result = this.repository.save(user);
        if (result){
//            System.out.println("Welcome "+user.getFullName()+" to our system bank !");
            return true;
        }else{
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("   ✘ Account creation failed!           ");
            System.out.println("   🔄 Please try again later.           ");
            System.out.println("╚══════════════════════════════════════╝");
            return false;
        }
    }
    public User login(String email , String password){
        if(emailExist(email) != null){
            User user = findByEmail(email);
            if(checkPassword(password , user.getPassword())){
                return user;
            }
            return null;
        }else{
            return null;
        }
    }
    public void getAll(){
        this.repository.getAll();
    }
    public User findByEmail(String email){
        return this.repository.findByEmail(email);
    }

    public User emailExist(String email){
        return this.repository.checkEmailExists(email);
    }
}
