import Components.Menu;
import Seeder.UsersSeeder;
import interfaces.UserRepository;
import modules.User;
import repositories.AccountRepository;
import repositories.InMemoryUserRepository;
import services.AuthService;

import java.util.*;

public class App {
    public static void main(String[] args) {
        UsersSeeder Seeder = new UsersSeeder();
        Seeder.seed();
        Menu menu = new Menu(false);
        menu.ShowMenu(null);
    }
}
