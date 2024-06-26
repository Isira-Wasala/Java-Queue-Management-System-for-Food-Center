import java.util.Scanner;

public class FoodiesFaveFoodCenter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FoodCenter foodCenter = new FoodCenter();
        foodCenter.viewCustomersSortedAlphabetically();

        String option;
        do {
            displayMenu();
            option = scanner.nextLine().toLowerCase();

            switch (option) {
                case "100":
                case "vfq":
                    foodCenter.viewAllQueues();
                    break;
                case "101":
                case "veq":
                    foodCenter.viewEmptyQueues();
                    break;
                case "102":
                case "acq":
                    Customer customer = new Customer();
                    customer.enterFirstName();
                    customer.enterSecondName();
                    customer.enterRequiredBurgers();
                    foodCenter.addCustomerToQueue(customer);
                    break;
                case "103":
                case "rcq":
                    System.out.print("Enter cashier number: ");
                    int removeCashierNumber = scanner.nextInt();
                    System.out.print("Enter customer's cashier position: ");
                    int position = scanner.nextInt();
                    scanner.nextLine(); // Consume newline character
                    foodCenter.removeCustomerFromQueue(removeCashierNumber, position);
                    break;
                case "104":
                case "pcq":
                    foodCenter.removeServedCustomer();
                    break;
                case "105":
                case "vcs":
                    foodCenter.viewCustomersSortedAlphabetically();
                    break;
                case "106":
                case "spd":
                    foodCenter.storeProgramDataIntoTextFile();
                    break;
                case "107":
                case "lpd":
                    foodCenter.loadProgramDataToConsole();
                    break;
                case "108":
                case "stk":
                    foodCenter.viewRemainingBurgerStock();
                    break;
                case "109":
                case "afs":
                    System.out.print("Enter new burgers to add stock: ");
                    int newBurgers = scanner.nextInt();
                    scanner.nextLine(); // newline character
                    foodCenter.addBurgersToStock(newBurgers);
                    break;
                case "110":
                case "IFQ":
                    foodCenter.incomeEachQueue();
                    break;
                case "999":
                case "ext":
                    System.out.println("Exit from the program. \nThank you for using!\n");
                    break;
                default:
                    System.out.println("Invalid input. Please try again carefully!");
                    break;
            }
        } while (!option.equals("999") && !option.equals("ext"));

        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("********** Foodies Fave Food Center **********\n");
        System.out.println("Options are here:\n");
        System.out.println("100 or VFQ: View all Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burger Stock");
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("110 or IFQ : View Income Each Queue");
        System.out.println("999 or EXT: Exit the Program");
        System.out.println("**********************************************");
        System.out.print("Enter your option: ");
    }

}
