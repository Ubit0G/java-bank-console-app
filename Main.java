import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Bank bank = new Bank();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running){

            printMenu();

            System.out.print("Выберите пункт меню: ");
            int chose = getIntInput(scanner);

            switch (chose) {
                case 1 ->{
                    System.out.print("Введите ФИО клиента: ");
                    String fullName = scanner.nextLine().trim();
                    Customer customer = bank.createCustomer(fullName);
                    if (customer == null){
                        System.out.println("Клиент не создан");
                    }
                    else{
                        System.out.println("Клиент создан. ID: " + customer.getId());
                    }
                }
                case 2 -> {
                    System.out.print("Введите ID клиента: ");
                    int customerId = getIntInput(scanner);
                    Customer customer = bank.findCustomer(customerId);
                    if (customer != null){
                        Account account = bank.openDebitAccount(customer);
                        System.out.println("Дебетовый счёт открыт: " + account.getAccountNumber());
                    }
                    else {
                        System.out.println("ID не найден");
                    }  
                }
                case 3 -> {
                    System.out.print("Введите ID клиента: ");
                    int customerId = getIntInput(scanner);
                    Customer customer = bank.findCustomer(customerId);
                    if (customer == null){
                        System.out.println("ID не найден");
                    }
                    else{
                        System.out.print("Введите кредитный лимит: ");
                        double limit = getDoubleInput(scanner);
                        Account account = bank.openCreditAccount(customer, limit);
                        System.out.println("Кредитный счёт открыт: " + account.getAccountNumber());
                    } 
                }
                case 4 -> {
                    System.out.print("Номер счёта: ");
                    String accountNumber = scanner.nextLine().trim();
                    System.out.print("Сумма для пополнения: ");
                    double amount = getDoubleInput(scanner);
                    boolean success = bank.deposit(accountNumber, amount);
                    System.out.println("Пополнение счета " + accountNumber + " на сумму: " + amount + (success ? " успешно" : " не удалось"));
                }
                case 5 -> {
                    System.out.print("Номер счёта: ");
                    String accountNumber = scanner.nextLine().trim();
                    System.out.print("Сумма для снятия: ");
                    double amount = getDoubleInput(scanner);
                    boolean success = bank.withdraw(accountNumber, amount);
                    System.out.println("Списание со счета " + accountNumber + " на сумму: " + amount + (success ? " успешно" : " не удалось"));
                }
                case 6 -> {
                    System.out.print("Счет отправителя: ");
                    String from = scanner.nextLine().trim();
                    System.out.print("Счет получателя: ");
                    String to = scanner.nextLine().trim();
                    System.out.print("Сумма перевода: ");
                    double amount = getDoubleInput(scanner);
                    boolean success = bank.transfer(from, to, amount);
                    System.out.println("Перевод со счета " + from + " на счет " + to + " на сумму: "+ amount + (success ? " успешно" : " не удалось"));
                }
                case 7 -> {
                    System.out.print("Введите ID клиента: ");
                    int customerId = getIntInput(scanner);
                    bank.printCustomerAccounts(customerId);
                }
                case 8 -> {
                    bank.printTransactions();
                }
                case 9 -> {
                    bank.printReport();
                }
                case 10 -> {
                    running = false;
                }
                default -> {
                    System.out.println("Неверный номер, повторите выбор");
                }
            }


        }
    }

    private static void printMenu(){
        System.out.println("\n");
        System.out.println("=== МЕНЮ ===");
        System.out.println("1. Создать клиента");
        System.out.println("2. Открыть дебетовый счёт");
        System.out.println("3. Открыть кредитный счёт");
        System.out.println("4. Пополнить счёт");
        System.out.println("5. Снять деньги");
        System.out.println("6. Перевести деньги между счетами");
        System.out.println("7. Показать счета клиента");
        System.out.println("8. Показать все транзакции");
        System.out.println("9. Показать отчёт");
        System.out.println("10. Выход");
        System.out.println("============");
        System.out.println("\n");
    }

    private static int getIntInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);  
        }
    }

    private static double getDoubleInput(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            return Double.parseDouble(input);
        }
    }
}
