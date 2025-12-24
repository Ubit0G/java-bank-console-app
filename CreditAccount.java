public class CreditAccount extends Account{
    
    private double creditLimit;

    public CreditAccount(String accountNumber, Customer owner, double creditLimit){
        super(accountNumber, owner);
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean withdraw(double amount){
        if (this.getBalance() - amount >= - this.creditLimit && amount > 0){
            return super.withdraw(amount);
        }
        return false;
    }
}
