import java.util.ArrayList;

public class Bank {

    private ArrayList<Customer> customers;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> transactions;
    private int nextCustomerId;
    private int nextAccountNumber;

    public Bank(){
        this.nextCustomerId = 1;
        this.nextAccountNumber = 1;

        customers = new ArrayList<>();
        accounts = new ArrayList<>();
        transactions = new ArrayList<>();
    }

    public Customer createCustomer(String fullName){
        if (fullName != null && !fullName.isEmpty()){
            int customerId = generateCustomerId();
            Customer customer = new Customer(customerId, fullName);
            this.customers.add(customer);

            return customer;
        }

        return null;
    }

    public Account openDebitAccount(Customer owner){
        if (owner != null){
            String accountNumber = generateAccountNumber();
            Account account = new DebitAccount(accountNumber, owner);
            this.accounts.add(account);

            return account;
        }

        return null;
    }

    public Account openCreditAccount(Customer owner, double creditLimit){
        if (owner != null || creditLimit > 0){
            String accountNumber = generateAccountNumber();
            Account account = new CreditAccount(accountNumber, owner, creditLimit);
            this.accounts.add(account);

            return account;
        }

        return null;
    }

    public Account findAccount(String accountNumber){

        if (accountNumber == null || accountNumber.isEmpty()){
            return null;
        } 

        for (Account account : this.accounts){
            if (accountNumber.equals(account.getAccountNumber())){
                return account;
            }
        }

        return null;
    }

    public Customer findCustomer (int id){
        for (Customer customer : customers){
            if (customer.getId() == id){
                return customer;
            }
        }

        return null;
    }

    public boolean deposit(String accountNumber, double amount){

        Account account = this.findAccount(accountNumber);
        String message = "OK";
        boolean success = false;

        if (account == null ){
            message = "Счет не найден";
        }
        else{
            success = account.deposit(amount);
            if (!success){
                message = "Ошибка в сумме";
            }
        }

        this.transactions.add(new Transaction(
                            TransactionType.DEPOSIT,
                            amount,
                            null, 
                            accountNumber, 
                            success, 
                            message));
        
        return success;
    }

    public boolean withdraw(String accountNumber, double amount){

        Account account = this.findAccount(accountNumber);
        String message = "OK";
        boolean success = false;

        if (account == null ){
            message = "Счет не найден";
        }
        else{
            success = account.withdraw(amount);
            if (!success){
                if (amount <= 0){
                    message = "Сумма должна быть положительна";
                }
                else{
                    message = "Недостаточно средств или превышен лимит";
                }
            }
        }

        this.transactions.add(new Transaction(
                            TransactionType.WITHDRAW,
                            amount,
                            accountNumber, 
                            null, 
                            success, 
                            message));
        
        return success;
    }

    public boolean transfer(String from, String to, double amount){

        Account fromAccount = this.findAccount(from);
        Account toAccount = this.findAccount(to);
        String message = "OK";
        boolean success = false;

        if (fromAccount == null){
            message = "Счет отправителя не найден";
        }
        else if (toAccount == null){
            message = "Счет получателя не найден";
        }
        else {
            success = fromAccount.transfer(toAccount, amount);
            if (!success){
                if (amount <= 0){
                    message = "Сумма должна быть положительна";
                }
                else{
                    message = "Недостаточно средств или ошибка при переводе";
                }
            }
        }

        this.transactions.add(new Transaction(
                            TransactionType.TRANSFER,
                            amount,
                            from, 
                            to, 
                            success, 
                            message));
        
        return success;
    }

    public void printCustomerAccounts(int customerId){

        boolean isFound = false;
        
        System.out.println("Счета клиента с ID " + customerId + ":");

        for (Account account : accounts){
            if (account.getOwner().getId() == customerId){
                System.out.println("Номер: " + account.getAccountNumber() + " Баланс: " + account.getBalance());
                isFound = true;
            }
        }

        if (!isFound){
            System.out.println("Не найдено счетов");
        }
    }

    public void printTransactions(){

        if (this.transactions.isEmpty()){
            System.out.println("Транзакций не было");
        }
        else{
            System.out.println("История операций:");

            for (Transaction transaction : this.transactions){
                System.out.println(transaction.getTimestamp() + 
                                    " Сумма: " + transaction.getAmount() + 
                                    " Тип: " + transaction.getType() +
                                    " Отправитель: " + transaction.getFromAccountNumber() +
                                    " Получатель: " + transaction.getToAccountNumber() + 
                                    " Статус: " + transaction.isSuccess() + 
                                    " Сообщение: " + transaction.getMessage());
            }
        }
    }

    public void printReport(){

        int debitCount = 0, creditCount = 0;
        double totalDebitBalance = 0.0, totalCreditBalance = 0.0;
        int successCount = 0, failedCount = 0;

        for (Account account : accounts){
            if (account instanceof DebitAccount) {
                ++debitCount;
                totalDebitBalance += account.getBalance();
            } else if (account instanceof CreditAccount) {
                ++creditCount;
                totalCreditBalance += account.getBalance();
            }
        }

        for (Transaction transaction : transactions){
            if (transaction.isSuccess()){
                ++successCount;
            }
            else{
                ++failedCount;
            }
        }

        System.out.println("ОТЧЁТ БАНКА");
        System.out.println("Дебетовые счета: " + debitCount + " Общий баланс: " +  totalDebitBalance);
        System.out.println("Кредитные счета: " + creditCount + " Общий баланс: " + totalCreditBalance);
        System.out.println("Всего операций: " + transactions.size());
        System.out.println("Успешных: " + successCount + " Неуспешных: " + failedCount);
    }

    private int generateCustomerId(){
        return nextCustomerId++;
    }

    private String generateAccountNumber(){
        return "N"+ nextAccountNumber++;
    }
}
