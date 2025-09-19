package Components;

import enums.TransactionsType;
import interfaces.UserRepository;
import modules.Account;
import modules.Transaction;
import modules.User;
import repositories.AccountRepository;
import repositories.InMemoryUserRepository;
import repositories.TransactionRepository;
import services.AccountService;
import services.AuthService;
import services.TransactionService;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Components.PasswordHasher.checkPassword;
import static java.lang.System.exit;
import static repositories.TransactionRepository.getAllTransactions;

public class Menu {
    private boolean isConnected;
    public static User CurrentUser;
    Scanner input = new Scanner(System.in);
    UserRepository repo = new InMemoryUserRepository();
    AuthService AuthService = new AuthService(repo);

    public Menu(boolean isConnected){
        this.isConnected = isConnected;
    }

    public void ShowMenu(User user){
        while (true){
            try{
                if (!isConnected){
                    System.out.println("===================================");
                    System.out.println("        🏠 Accueil (Non Connecté)       ");
                    System.out.println("===================================");
                    System.out.println("  1️⃣  Login");
                    System.out.println("  2️⃣  Register");
                    System.out.println("  0️⃣  Exit");
                    System.out.println("===================================");
                    System.out.print("➤ Choose an option: ");
                    int choice = input.nextInt();

                    switch (choice){
                        case 1 :
                            this.showLoginMenu();
                            break;
                        case 2 :
                            showRegisterMenu();
                            break;
                        case 0 :
                            System.out.println("Exiting the program ...");
                            exit(0);
                            break;
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                }else{
                    CurrentUser = user;
                    AccountRepository AccountRepository = new AccountRepository();
                    AccountService AccountService = new AccountService(AccountRepository);
                    System.out.println("===========================================");
                    System.out.println("  🔐 Logged in as: " + user.getFullName());
                    System.out.println("===========================================");
                    System.out.println("  1️⃣    Create account");
                    System.out.println("  2️⃣    List my accounts");
                    System.out.println("  3️⃣    Deposit");
                    System.out.println("  4️⃣    Withdraw");
                    System.out.println("  5️⃣    Transfer");
                    System.out.println("  6️⃣    Update profile");
                    System.out.println("  7️⃣    Change password");
                    System.out.println("  8️⃣    Close account");
                    System.out.println("  9️⃣    Logout");
                    System.out.println("  1️0️⃣  Transactions History");
                    System.out.println("  0️⃣    Exit");
                    System.out.println("===========================================");
                    System.out.print("➤ Choose an option: ");

                    int choice = input.nextInt();
                    switch (choice){
                        case 1 :
                            showTheCreateAccount();
                            break;
                        case 2 :
                            AccountService.getAll();
                            break;
                        case 3 :
                            ShowDeposit();
                            break;
                        case 4 :
                            ShowWithdraw();
                            break;
                        case 5 :
                            ShowTransfertDialog();
                            break;
                        case 6 :
                            ShowUpdateProfile();
                            break;
                        case 7 :
                            ShowUpdatePassword();
                            break;
                        case 8 :
                            ShowCloseAccount();
                            break;
                        case 9 :
                            LogOut();
                            break;
                        case 10:
                            ShowHistoryTransaction();
                            break;
                        case 0 :
                            System.out.println("Exiting the program ...");
                            exit(0);
                            break;
                        default:
                            System.out.println("Invalid option.");
                            break;
                    }
                }
            }catch (InputMismatchException e){
                Console.clearConsole();
                input.nextLine();
                System.out.println("Enter The number from the above menu .");
            }
        }
    }

    public void showLoginMenu(){
        System.out.println("===================================");
        System.out.println("         🔐 Login Menu              ");
        System.out.println("===================================");

        System.out.print("📧 Email    : ");
        String email = input.next();

        System.out.print("🔑 Password : ");
        String password = input.next();

        if(this.AuthService.login(email,password) != null){
            CurrentUser = this.AuthService.login(email,password);
            this.isConnected = true;
            this.ShowMenu(AuthService.emailExist(email));
        }
    }
    public void showRegisterMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println("===================================");
        System.out.println("         🔐 Register Menu              ");
        System.out.println("===================================");


        System.out.print("📧 FullName    : ");
        String fullname = input.nextLine();

        System.out.print("🔑 Address : ");
        String address = input.nextLine();

        String email = "";
        String password = "";
        boolean fullMatchEmail = false;
        boolean fullMatchPassword = false;
        while(!fullMatchEmail){
            System.out.print("📧 Email    : ");
            email = input.nextLine();
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            Matcher matcher = pattern.matcher(email);
            fullMatchEmail = matcher.matches();
            if(!fullMatchEmail){
                System.out.println("Email is not valid , try again (EX : 'example@gmail.com')");
            }
        }
        while(!fullMatchPassword){
            System.out.print("🔑 Password : ");
            password = input.nextLine();
            int min = 6;
            if(password.length() < min){
                System.out.println("Password is not valid , min length is 6 chars");
            }else{
                fullMatchPassword = true;
            }
        }



        User user = new User(UUID.randomUUID(),fullname,email,address,password);

        if(this.AuthService.register(user)){
            CurrentUser = this.AuthService.login(email,password);
            this.isConnected = true;
            this.ShowMenu(AuthService.emailExist(email));
        }else{
            System.out.println("╔═══════════════════════════════════════════════════════╗");
            System.out.println("   ✘ Login creation failed!           ");
            System.out.println("   🔄 No account associated with this informations.           ");
            System.out.println("╚═══════════════════════════════════════════════════════╝");
            showRegisterMenu();
        }
    }
    public void showTheCreateAccount(){
        try{
            AccountRepository AccountRepository = new AccountRepository();
            AccountService AccountService = new AccountService(AccountRepository);
            System.out.println("0 : Menu ");
            System.out.print("Account Type : ");
            String Type = input.next();
            backToMenu(Type);

            System.out.print("Amount : ");
            String Amount = input.next();
            backToMenu(Amount);

            AccountService.create(Amount);
        } catch (NumberFormatException e) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("   ✘ Amount Value is Not Valid!             ");
            System.out.println("   🔄 Please try again later.               ");
            System.out.println("╚══════════════════════════════════════════╝");
        }
    }

    public void backToMenu(String Choice){
        if(Objects.equals(Choice, "0")){
            ShowMenu(CurrentUser);
        }
    }

    public void ShowDeposit(){
        try{
            Scanner input = new Scanner(System.in);
            TransactionRepository TransactionRepo = new TransactionRepository();
            TransactionService TransactionService = new TransactionService(TransactionRepo);

            System.out.println("0 : Menu ");
            System.out.print("Enter Your Account Number : ");
            String AccountID = input.nextLine();
            backToMenu(AccountID);

            System.out.print("Amount That you want to deposit  : ");
            BigDecimal Amount = input.nextBigDecimal();
            backToMenu(String.valueOf(Amount));

            boolean result = TransactionService.deposit(AccountID,Amount,TransactionsType.DEPOSIT,"This is a deposit");
            if (result){
                System.out.println("==============================================");
                System.out.println(" 🎉 ✔ The Deposit Operation was Successful! 🎉 ");
                System.out.println("==============================================");
            }else{
                ShowDeposit();
            }
            ShowMenu(CurrentUser);
        } catch (NumberFormatException e) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("   ✘ Deposit Amount Value is Not Valid!     ");
            System.out.println("   🔄 Please try again later.               ");
            System.out.println("╚══════════════════════════════════════════╝");
        } catch (InputMismatchException e) {
            System.out.println("There is a problem , try again !");
            ShowDeposit();
        }
    }

    public void ShowWithdraw(){
        TransactionRepository TransactionRepo = new TransactionRepository();
        TransactionService TransactionService = new TransactionService(TransactionRepo);
        System.out.println("0 : Menu ");
        System.out.print("Enter Your Account Number : ");
        String AccountID = input.next();
        backToMenu(AccountID);

        System.out.print("Amount That you want to Withdraw : ");
        BigDecimal Amount = input.nextBigDecimal();
        backToMenu(String.valueOf(Amount));

        boolean result = TransactionService.withdraw(AccountID,Amount);
        if (result){
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("   💰 Withdraw Completed Successfully   ");
            System.out.println("   😉 See you again soon!               ");
            System.out.println("╚══════════════════════════════════════╝");
        }
        ShowMenu(CurrentUser);
    }
    public void ShowTransfertDialog(){
        System.out.println("0 : Menu ");
        System.out.print("What is the type of transaction you want (IN / OUT) : ");
        String type = input.next();
        backToMenu(type);

        switch (type){
            case "IN":
                ShowTransfert(type);
            case "OUT":
                ShowTransfert(type);
            default:
                System.out.println("Choose Correct type (IN / OUT)");
                ShowTransfertDialog();
        }
    }
    public void ShowTransfert(String Type){
        TransactionRepository TransactionRepo = new TransactionRepository();
        TransactionService TransactionService = new TransactionService(TransactionRepo);
        System.out.println("0 : Menu ");
        System.out.print("Enter Sender Account Number : ");
        String Sender = input.next();
        backToMenu(String.valueOf(Sender));

        System.out.print("Enter Receiver Account Number : ");
        String Receiver = input.next();
        backToMenu(String.valueOf(Receiver));
        System.out.print("Amount That you want to Transfer : ");
        BigDecimal Amount = input.nextBigDecimal();
        backToMenu(String.valueOf(Amount));
        boolean result = false;

        if (Objects.equals(Type, "IN")) {
            result = TransactionService.transferIN(Sender, Receiver, Amount);
        } else if (Objects.equals(Type, "OUT")) {
            result = TransactionService.transferOUT(Sender, Receiver, Amount);
        }
        if (result) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("   💰 Transfer Completed Successfully   ");
            System.out.println("   😉 See you again soon!               ");
            System.out.println("╚══════════════════════════════════════╝");
        } else {
            ShowTransfert(Type);
        }
        ShowMenu(CurrentUser);
    }
    public void ShowUpdateProfile(){
        System.out.println("1 . Name    : "+CurrentUser.getFullName());
        System.out.println("2 . Email   : "+CurrentUser.getEmail());
        System.out.println("3 . Address : "+CurrentUser.getAddress());
        System.out.println("0 . Exit ");
        System.out.print("What you want to update ? : ");
        int choice = input.nextInt();
        switch (choice){
            case 1:
                updateName();
                break;
            case 2:
                updateEmail();
                break;
            case 3:
                updateAddress();
                break;
            case 0:
                ShowMenu(CurrentUser);
                break;
            default:
                System.out.print("Enter the correct choice 1/2/3/0 : ");
                ShowUpdateProfile();
                break;

        }
    }
    public void updateName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("The Current Name : "+ CurrentUser.getFullName());
        System.out.print("Enter New Name : ");
        String newName = scanner.nextLine();
        CurrentUser.setFullName(newName);
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("   ✔ Your full name has been updated!    ");
        System.out.println("   From: " + CurrentUser.getFullName());
        System.out.println("   To  : " + newName);
        System.out.println("╚════════════════════════════════════════╝");
    }
    public void updateEmail(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("The Current Email : "+ CurrentUser.getEmail());
        System.out.print("Enter New Email : ");
        String newEmail = scanner.nextLine();
        CurrentUser.setEmail(newEmail);
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("   ✔ Your email has been updated!    ");
        System.out.println("   From: " + CurrentUser.getEmail());
        System.out.println("   To  : " + newEmail);
        System.out.println("╚════════════════════════════════════════╝");
    }
    public void updateAddress(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("The Current Address : "+ CurrentUser.getAddress());
        System.out.print("Enter New Address : ");
        String newAddress = scanner.nextLine();
        CurrentUser.setAddress(newAddress);
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("   ✔ Your address has been updated!    ");
        System.out.println("   From: " + CurrentUser.getAddress());
        System.out.println("   To  : " + newAddress);
        System.out.println("╚════════════════════════════════════════╝");
    }

    public void ShowCloseAccount(){
        try{
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the Account ID : ");
            String AccountID = scanner.next();

            for (int i = 0; i < AccountRepository.Accounts.size(); i++) {
                if (Objects.equals(AccountRepository.Accounts.get(i).getAccountId(), AccountID)) {
                    Account account = AccountRepository.Accounts.get(i);
                    if (account.getBalance().compareTo(BigDecimal.valueOf(0)) != 0) {
                        System.out.println("You have " + account.getBalance() + "dh in your account , you have to empty your account first , before closing the account !");
                        ShowCloseAccount();
                    } else {
                        System.out.println("Are you sure that you want to remove account number " + account.getAccountId() + " (Yes / No)");
                        String choice = scanner.next().toLowerCase(Locale.ROOT);
                        switch (choice) {
                            case "yes":
                                AccountRepository.Accounts.get(i).setActive(false);
                                System.out.println("The Account " + AccountRepository.Accounts.get(i).getAccountId() + " Has been removed successfully !");
                                break;
                            case "no":
                                backToMenu("0");
                                break;
                        }
                    }
                }
            }
        } catch (NullPointerException e) {
            System.out.println("The ID Account Doesnt exist on our system !");
        }

    }
    public void ShowUpdatePassword(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("0 : Menu ");
        System.out.print("Old password :");
        String OldPassword = scanner.nextLine();
        backToMenu(OldPassword);
        if(checkPassword(OldPassword,CurrentUser.getPassword())){
            boolean fullMatchPassword = false;
            String NewPassword = "";
            while(!fullMatchPassword){
                System.out.print("New password :");
                NewPassword = scanner.nextLine();
                int min = 6;
                if(NewPassword.length() < min){
                    System.out.println("Password is not valid , min length is 6 chars");
                }else{
                    fullMatchPassword = true;
                    backToMenu(NewPassword);
                    CurrentUser.setPassword(NewPassword);
                    System.out.println("Your password has been updated successfully !");
                    ShowMenu(CurrentUser);
                }
            }
        }else{
            System.out.println("The password is wrong try again !");
            ShowUpdatePassword();
        }
    }
    public void LogOut(){
        CurrentUser = null;
        isConnected = false;
        ShowMenu(CurrentUser);
    }

    public void ShowHistoryTransaction(){
        System.out.println("===== ALL TRANSACTIONS =====");
        for (int i = 0; i < getAllTransactions().size(); i++) {
            Transaction transaction = getAllTransactions().get(i);

            System.out.println("----------------------------");
            System.out.println("Transaction ID : " + transaction.getId());

            if (transaction.getType() == TransactionsType.TRANSFEROUT) {
                System.out.println("TRANSFER OPERATION");
                System.out.println("Receiver Account RIB : " + transaction.getCounterpartyAccountId());

                for (int j = 0; j < AccountRepository.Accounts.size(); j++) {
                    if (Objects.equals(AccountRepository.Accounts.get(i).getAccountId(), transaction.getCounterpartyAccountId())) {

                        for (int k = 0; k < InMemoryUserRepository.Users.size(); k++) {
                            if (InMemoryUserRepository.Users.get(k).getId() == AccountRepository.Accounts.get(i).getOwnerUserId()) {
                                System.out.println("Receiver Name  : " + InMemoryUserRepository.Users.get(k).getFullName());
                                System.out.println("Receiver Email : " + InMemoryUserRepository.Users.get(k).getEmail());
                            }
                        }
                    }
                }
            }else if (transaction.getType() == TransactionsType.DEPOSIT) {
                    System.out.println("DEPOSIT OPERATION");
                    for (int j = 0; j < AccountRepository.Accounts.size(); j++) {
                        if (Objects.equals(AccountRepository.Accounts.get(i).getAccountId(), transaction.getCounterpartyAccountId())) {

                            for (int k = 0; k < InMemoryUserRepository.Users.size(); k++) {
                                if (InMemoryUserRepository.Users.get(k).getId() == AccountRepository.Accounts.get(i).getOwnerUserId()) {
                                    System.out.println("Receiver Name  : " + InMemoryUserRepository.Users.get(k).getFullName());
                                    System.out.println("Receiver Email : " + InMemoryUserRepository.Users.get(k).getEmail());
                                }
                            }
                        }
                    }
            }  else if (transaction.getType() == TransactionsType.TRANSFERIN)  {
                System.out.println("INTERNAL TRANSACTION");
                System.out.println("From Account : " + transaction.getAccountId());
                System.out.println("To Account   : " + transaction.getCounterpartyAccountId());
            }

            System.out.println("Amount : " + transaction.getAmount() + " DH");
            System.out.println("Time   : " + transaction.getTimestamp());
        }
        System.out.println("============================");

    }
}
