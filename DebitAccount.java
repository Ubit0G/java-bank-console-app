public class DebitAccount extends Account {
    
    public DebitAccount(String accountNumber, Customer owner){
        super(accountNumber, owner);
    }

    @Override
    public boolean withdraw (double amount){
        if (this.getBalance() >= amount && amount > 0){
            return super.withdraw(amount);
        }
        return false;
    }
}
