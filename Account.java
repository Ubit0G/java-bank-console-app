public class Account {
    
    private String accountNumber;
    private double balance;
    private Customer owner;

    protected Account(String accountNumber, Customer owner){
        this.accountNumber = accountNumber;
        this.balance = 0;
        this.owner = owner;
    }

    public boolean deposit (double amount){
        if (amount > 0){
            this.balance += amount;
            return true;
        }
        return false;
    }

    public boolean withdraw (double amount){
        if (amount > 0){
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public boolean transfer (Account to, double amount){
        if (to == null){
            return false;
        }
        if (!this.withdraw(amount)){
            return false;
        }
        if (!to.deposit(amount)){
            this.deposit(amount);// Возвращаем на счет, если перевод не прошел
            return false;
        }

        return true;
    }

    public double getBalance(){
        return this.balance;
    }

    public String getAccountNumber(){
        return this.accountNumber;
    }

    public Customer getOwner(){
        return this.owner;
    }

}
