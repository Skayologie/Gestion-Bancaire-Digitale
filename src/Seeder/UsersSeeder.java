package Seeder;

import interfaces.UserRepository;
import modules.User;
import repositories.InMemoryUserRepository;
import services.AuthService;

import java.util.UUID;

public class UsersSeeder {
    public void seed(){
        UserRepository repo = new InMemoryUserRepository();
        AuthService AuthService = new AuthService(repo);
        User jawad   = new User( UUID.randomUUID(),"Jawad Boulmal", "Jawadboulmal@gmail.com", "Safi","Skayologie");
        User youssef = new User( UUID.randomUUID(),"Youssef Demraoui", "YoussefDemraoui2442@gmail.com", "Casablanca","Skayologie");
        User leila   = new User( UUID.randomUUID(),"Leila Amrani", "Leila.Amrani22@gmail.com", "Rabat","Skayologie");
        User sara    = new User( UUID.randomUUID(),"Sara Benali", "Sara.Benali98@gmail.com", "Marrakech","Skayologie");
        User omar    = new User( UUID.randomUUID(),"Omar El Mansouri", "Omar.Mansouri@gmail.com", "Fes","Skayologie");
        User nada    = new User( UUID.randomUUID(),"Nada Laghzaoui", "Nada.Laghzaoui@gmail.com", "Tangier","Skayologie");
        User adil    = new User( UUID.randomUUID(),"Adil Tazi", "Adil.Tazi33@gmail.com", "Agadir","Skayologie");
        User amal    = new User( UUID.randomUUID(),"Amal Rachidi", "Amal.Rachidi11@gmail.com", "Kenitra","Skayologie");
        User rayan   = new User( UUID.randomUUID(),"Rayan Idrissi", "Rayan.Idrissi99@gmail.com", "Oujda","Skayologie");
        User imane   = new User( UUID.randomUUID(),"Imane Kabbaj", "Imane.Kabbaj88@gmail.com", "El Jadida","Skayologie");

        AuthService.register(jawad);
        AuthService.register(youssef);
        AuthService.register(leila);
        AuthService.register(sara);
        AuthService.register(omar);
        AuthService.register(nada);
        AuthService.register(adil);
        AuthService.register(amal);
        AuthService.register(rayan);
        AuthService.register(imane);
    }
}
