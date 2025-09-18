package services;

import interfaces.AccountInterface;
import modules.Account;
import modules.User;
import repositories.AccountRepository;
import utils.RandomStringGenerator;

import java.math.BigDecimal;
import java.util.Random;

import static enums.TransactionsType.*;

public class AccountService {
    private AccountInterface AccountRepo ;

    public AccountService(AccountRepository AccountRepo){
        this.AccountRepo = AccountRepo;
    }

    public void create(String amount){
        String FsChars = RandomStringGenerator.generateRandomString(2);
        String ScChars = RandomStringGenerator.generateRandomString(4);
        String numbers = RandomStringGenerator.generateRandomNumbers(4);
        String AccountID = FsChars +"-"+ScChars+"-"+numbers;
        Account account = new Account(AccountID,new BigDecimal(amount));
        boolean result = this.AccountRepo.save(account);
        if (result){
            this.AccountRepo.getAll();
        }
    }
    public void getAll(){
        this.AccountRepo.getAll();
    }

}
