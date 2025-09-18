package repositories;

import interfaces.UserRepository;
import modules.User;

import java.util.*;

public class InMemoryUserRepository implements UserRepository {
    private static HashMap<Integer, User> Users = new HashMap<>();

    @Override
    public boolean save(User user) {
        try{
            if(user.getId() == null){
                return false;
            } else if (Users.containsKey(user.getEmail())) {
                return false;
            }else{
                Users.put(Users.size(),user);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAll() {
        System.out.println("Total Users : "+Users.size());
        for (int i = 0; i < Users.size(); i++) {
            System.out.println("+ User #"+i+"\n\tid : "+ Users.get(i).getId() +"\n\tName : "+ Users.get(i).getFullName()+"\n\tAddress : "+ Users.get(i).getAddress());
        }
    }
    @Override
    public User checkEmailExists(String emailGiven){
        for (int i = 0; i < Users.size(); i++) {
            String email = Users.get(i).getEmail();
            if (Objects.equals(email.toLowerCase(), emailGiven.toLowerCase())){
                return Users.get(i);
            }
        }
        return null;
    }
    @Override
    public User findByEmail(String emailGiven){
        for (int i = 0; i < Users.size(); i++) {
            String email = Users.get(i).getEmail();
            if (Objects.equals(email.toLowerCase(), emailGiven.toLowerCase())){
                return Users.get(i);
            }
        }
        return null;
    }
}
