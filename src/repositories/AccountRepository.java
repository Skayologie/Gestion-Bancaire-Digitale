package repositories;


import Components.Menu;
import interfaces.AccountInterface;
import modules.Account;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Objects;

public class AccountRepository implements AccountInterface {
    public static HashMap<Integer, Account> Accounts = new HashMap<>();
    public static HashMap<Integer, Account> UserAccount = new HashMap<>();


    @Override
    public boolean save(Account account) {
        try{
            if(account.getAccountId() == null){
                return false;
            }else{
                Accounts.put(Accounts.size(),account);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void getAll() {
        try{
            getUserAccounts();
            int counter = 0;
            for (int i = 0; i < UserAccount.size(); i++) {
                if(Menu.CurrentUser.getId() == UserAccount.get(i).getOwnerUserId() && UserAccount.get(i).isActive()){
                    counter++;
                }
            }
            System.out.println("╔══════════════════════════════════╗");
            System.out.println("   📊 Your Total Accounts : " + counter);
            System.out.println("╚══════════════════════════════════╝");

            for (int i = 0; i < UserAccount.size(); i++) {
                if(Menu.CurrentUser.getId() == UserAccount.get(i).getOwnerUserId() && UserAccount.get(i).isActive()){
                    System.out.println("╔════════════════════════════════════════════╗");
                    System.out.println("  📌 Account ID    : " + UserAccount.get(i).getAccountId());
                    System.out.println("  👤 Owner User ID : " + UserAccount.get(i).getOwnerUserId());
                    System.out.println("  💰 Balance       : " + UserAccount.get(i).getBalance());
                    System.out.println("  📅 Created At    : " + UserAccount.get(i).getCreatedAt());
                    System.out.println("╚════════════════════════════════════════════╝");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("   ⚠️  Try Again Please   ");
            System.out.println("╚══════════════════════════════════════╝");
        }
    }

    public static void getUserAccounts(){
        for (int i = 0; i < Accounts.size(); i++) {
            if (Accounts.get(i) == null){
                continue;
            }
            if(Menu.CurrentUser.getId() == Accounts.get(i).getOwnerUserId() && Accounts.get(i).isActive()){
                UserAccount.put(i,Accounts.get(i));
            }
        }
    }

    public static Account increaseAccountBalance(String AccountID,BigDecimal Ammount){
        getUserAccounts();
        BigDecimal min = BigDecimal.valueOf(100);
        if(Ammount.compareTo(min) < 0){
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("   ⚠️  Enter an Amount Bigger than 100   ");
            System.out.println("╚══════════════════════════════════════╝");
            return null;
        }
        for (int i = 0; i < UserAccount.size(); i++) {
            if (Objects.equals(AccountID, UserAccount.get(i).getAccountId())){
                BigDecimal currentBalance = UserAccount.get(i).getBalance();
                BigDecimal newBalance = currentBalance.add(Ammount);
                UserAccount.get(i).setBalance(newBalance);
                return UserAccount.get(i);
            }
        }
        return null;
    }
    public static Account WithdrawFromAccountBalance(String AccountID,BigDecimal Ammount){
        getUserAccounts();

        if (Ammount.signum() <= 0) {
            System.out.println("╔════════════════════════════════╗");
            System.out.println("   ⚠️  Invalid amount !        ");
            System.out.println("╚════════════════════════════════╝");
            return null;
        }
        for (int i = 0; i < UserAccount.size(); i++) {
            if (Objects.equals(AccountID, UserAccount.get(i).getAccountId())){
                BigDecimal currentBalance = UserAccount.get(i).getBalance();
                if(Ammount.compareTo(currentBalance) <= 0){
                    BigDecimal newBalance = currentBalance.subtract(Ammount);
                    UserAccount.get(i).setBalance(newBalance);
                    return UserAccount.get(i);
                }else{
                    System.out.println("╔════════════════════════════════╗");
                    System.out.println("   ⚠️  Insufficient Funds!        ");
                    System.out.println("╚════════════════════════════════╝");
                    return null;
                }
            }
        }
        System.out.println("╔════════════════════════════════╗");
        System.out.println(" ⚠️  No Receiver Account Found! ");
        System.out.println("╚════════════════════════════════╝");
        return null;
    }
    public static boolean checkTheAccountID (String AccountID,boolean isTransfertIn){
        HashMap<Integer, Account> forThis ;
        if (isTransfertIn){
            forThis = UserAccount;
        }else{
            forThis = Accounts;
        }
        for (int i = 0; i < forThis.size(); i++) {
            if (Objects.equals(AccountID, forThis.get(i).getAccountId())){
                return false;
            }
        }
        return true;
    }
    public static boolean TransferIN(String AccountID,BigDecimal Ammount,String otherPartieUserID){
        getUserAccounts();
        if(Ammount.signum() == -1 || Ammount.signum() == 0){
            System.out.println("╔════════════════════════════════╗");
            System.out.println("   ⚠️  Invalid amount !        ");
            System.out.println("╚════════════════════════════════╝");
            return false;
        }
        if(Objects.equals(otherPartieUserID, AccountID)){
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("   ⚠️  You can't send to the same account , please choose an other account !        ");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
            return false;
        }
        if(checkTheAccountID(AccountID, true) && checkTheAccountID(otherPartieUserID, true)){
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("   ❌ The information is invalid. Please try again.        ");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            return false;
        }
        Account SenderAccount = null;
        Account ReceiverAccount = null;
        for (int i = 0; i < UserAccount.size(); i++) {
            if (Objects.equals(UserAccount.get(i).getAccountId(), AccountID)){
                SenderAccount = UserAccount.get(i);
            }
            if (Objects.equals(UserAccount.get(i).getAccountId(), otherPartieUserID)){
                ReceiverAccount = UserAccount.get(i);
            }
        }
        if(SenderAccount == null){
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("   ⚠️  Your Send Account id is invalid , please choose the correct  !        ");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
            return false;
        }
        if(ReceiverAccount == null){
            System.out.println("╔══════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("   ⚠️  The Receiver Account id is invalid , please enter the correct  !        ");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════╝");
            return false;
        }
        if(SenderAccount.getBalance().compareTo(Ammount) >= 0){
            BigDecimal currentSenderBalance = SenderAccount.getBalance();
            SenderAccount.setBalance(currentSenderBalance.subtract(Ammount));

            BigDecimal currentReceiverBalance = ReceiverAccount.getBalance();
            ReceiverAccount.setBalance(currentReceiverBalance.add(Ammount));

            return true;
        }else{
            System.out.println("╔════════════════════════════════╗");
            System.out.println("   ⚠️  Insufficient Funds!        ");
            System.out.println("╚════════════════════════════════╝");
            return false;
        }
    }
    public static boolean TransferOUT(String AccountID,BigDecimal Ammount,String otherPartieUserID){
        getUserAccounts();
        if(checkTheAccountID(AccountID, false) && checkTheAccountID(otherPartieUserID, false)){
            System.out.println("╔══════════════════════════════════════════════════════════╗");
            System.out.println("   ❌ The information is invalid. Please try again.        ");
            System.out.println("╚══════════════════════════════════════════════════════════╝");
            return false;
        }
        Account SenderAccount = null;
        Account ReceiverAccount = null;
        for (int i = 0; i < UserAccount.size(); i++) {
            if (Objects.equals(UserAccount.get(i).getAccountId(), AccountID)){
                SenderAccount = UserAccount.get(i);
            }
            if (Objects.equals(UserAccount.get(i).getAccountId(), otherPartieUserID)){
                ReceiverAccount = UserAccount.get(i);
            }
        }
        assert SenderAccount != null;
        assert ReceiverAccount != null;
        if(SenderAccount.getBalance().compareTo(Ammount) >= 0){
            BigDecimal currentSenderBalance = SenderAccount.getBalance();
            SenderAccount.setBalance(currentSenderBalance.subtract(Ammount));

            BigDecimal currentReceiverBalance = ReceiverAccount.getBalance();
            ReceiverAccount.setBalance(currentReceiverBalance.add(Ammount));

            return true;
        }else{
            System.out.println("╔════════════════════════════════╗");
            System.out.println("   ⚠️  Insufficient Funds!        ");
            System.out.println("╚════════════════════════════════╝");
            return false;
        }
    }
}
