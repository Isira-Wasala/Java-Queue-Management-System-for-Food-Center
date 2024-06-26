import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FoodCenter {
    private static final int MAX_IN_CASHIER_1 = 2;
    private static final int MAX_IN_CASHIER_2 = 3;
    private static final int MAX_IN_CASHIER_3 = 5;

    private Object[][] cashierQueues;
    private int[] cashierQueuesSizes;
    private int[][] cashierBurgers;
    private ArrayList<Customer> customerWaitingListQueue;
    private int burgerStock;
    private int burgerPrice;

    public FoodCenter() {
        cashierQueues = new Object[][]{{}, {}, {}};
        cashierQueuesSizes = new int[]{0, 0, 0};
        cashierBurgers = new int[3][5];
        customerWaitingListQueue = new ArrayList<>();
        burgerStock = 50;
        burgerPrice = 650;
    }

    public void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        int[] maxQueueSizes = {2, 3, 5};

        // Print cashier numbers horizontally
        for (int i = 1; i <= cashierQueues.length; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        // Print occupied or not occupied status vertically under the relevant cashier number
        for (int i = 0; i < getMaxQueueSize(); i++) {
            for (int j = 0; j < cashierQueues.length; j++) {
                if (i < cashierQueuesSizes[j]) {
                    System.out.print("O ");
                } else if (i < maxQueueSizes[j]) {
                    System.out.print("X ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }

    public void viewEmptyQueues() {
        System.out.println("********* Empty Queues Are Here *********\n");
        for (int i = 0; i < cashierQueues.length; i++) {
            if (cashierQueuesSizes[i] == 0) {
                System.out.println("Cashier " + (i + 1) + " queue is empty.\n");
            }
        }
    }

    public void addCustomerToQueue(Customer customer) {
        if (isFoodQueuesFull()) {
            addToCustomerWaitingList(customer);
            return;
        }

        int indexOfQueue;

        if (cashierQueuesSizes[0] < MAX_IN_CASHIER_1) {
            indexOfQueue = 0;  // Add customer to Cashier 01 if there is space
        } else if (cashierQueuesSizes[1] < MAX_IN_CASHIER_2) {
            indexOfQueue = 1;  // Add customer to Cashier 02 if there is space
        } else if (cashierQueuesSizes[2] < MAX_IN_CASHIER_3) {
            indexOfQueue = 2;  // Add customer to Cashier 03 if there is space
        } else {
            System.out.println("We're sorry. Can't add more customers. All queues are full.");
            return;
        }

        addToFoodQueue(indexOfQueue, customer);
    }

    public void removeCustomerFromQueue(int queueNumber, int position) {
        if (queueNumber < 1 || queueNumber > cashierQueues.length) {
            System.out.println("Invalid Input. Please check the Cashier Queue Number and try again!");
            return;
        }

        int indexOfQueue = queueNumber - 1;
        int existingQueueSize = cashierQueuesSizes[indexOfQueue];

        if (existingQueueSize == 0) {
            System.out.println("Cashier " + queueNumber + " queue is empty. Can't remove more customers.");
            return;
        }

        if (position < 1 || position > existingQueueSize) {
            System.out.println("Invalid Input. Please check Customer Position and try again!");
            return;
        }

        String removedCustomer = (String) cashierQueues[indexOfQueue][position - 1];

        for (int i = position - 1; i < existingQueueSize - 1; i++) {
            cashierQueues[indexOfQueue][i] = cashierQueues[indexOfQueue][i + 1];
        }

        cashierQueues[indexOfQueue] = Arrays.copyOf(cashierQueues[indexOfQueue], existingQueueSize - 1);
        cashierQueuesSizes[indexOfQueue]--;
        System.out.println("Done! Customer '" + removedCustomer + "' removed from Queue " + queueNumber);
    }
    public void removeServedCustomer() {
        for (int i = 0; i < cashierQueues.length; i++) {
            if (cashierQueuesSizes[i] > 0) {
                String customerName = (String) cashierQueues[i][0];
                int requiredBurgers = getRequiredBurgersForCustomer(customerName);

                for (int j = 0; j < cashierQueuesSizes[i] - 1; j++) {
                    cashierQueues[i][j] = cashierQueues[i][j + 1];
                }
                cashierQueues[i][cashierQueuesSizes[i] - 1] = "X";
                cashierQueuesSizes[i]--;

                updateRemainingBurgerStock(requiredBurgers);

                System.out.println("Done! " + customerName + " removed from Queue " + (i + 1));

                if (!isWaitingListEmpty()) {
                    Customer nextCustomer = removeFromWaitingList();
                    addToFoodQueue(i, nextCustomer);
                }

                return;
            }
        }
        System.out.println("All cashiers are empty. There is no customer to remove.");
    }

    public void viewRemainingBurgerStock() {
        // Update the remaining burger stock
        for (int i = 0; i < cashierQueues.length; i++) {
            for (int j = 0; j < cashierQueuesSizes[i]; j++) {
                String customerName = (String) cashierQueues[i][j];
                int requiredBurgers = getRequiredBurgersForCustomer(customerName);
                updateRemainingBurgerStock(requiredBurgers);
            }
        }
        System.out.println("Remaining burger stock: " + burgerStock);
    }

    private int getRequiredBurgersForCustomer(String customerName) {
        for (int i = 0; i < cashierQueues.length; i++) {
            for (int j = 0; j < cashierQueuesSizes[i]; j++) {
                if (cashierQueues[i][j].equals(customerName)) {
                    return cashierBurgers[i][j];
                }
            }
        }
        return 0;
    }
    private void updateRemainingBurgerStock(int requiredBurgers) {
        burgerStock -= requiredBurgers;
    }

    public void viewCustomersSortedAlphabetically() {
        String[] allCustomers = new String[0];
        for (Object[] queue : cashierQueues) {
            allCustomers = Arrays.copyOf(allCustomers, allCustomers.length + queue.length);
            System.arraycopy(queue, 0, allCustomers, allCustomers.length - queue.length, queue.length);
        }

        Arrays.sort(allCustomers);

        System.out.println("\n******** Customer Names Sorted in Alphabetical Order ********");
        for (String customer : allCustomers) {
            System.out.println(customer);
        }
        System.out.println();
    }
    public void storeProgramDataIntoTextFile() {
        try {
            File file = new File("FoodCenterprogram_data.txt");
            FileWriter writer = new FileWriter(file);

            // Write the remaining burger stock to the file
            writer.write("Remaining Burger Stock: " + burgerStock + "\n");

            // Write the cashier queues to the file
            for (int i = 0; i < cashierQueues.length; i++) {
                StringBuilder queueData = new StringBuilder();
                queueData.append("Queue ").append(i + 1).append(": ");

                if (cashierQueues[i].length > 0) {
                    for (int j = 0; j < cashierQueues[i].length; j++) {
                        String customerFullName = (String) cashierQueues[i][j];
                        queueData.append(customerFullName);
                        if (j < cashierQueues[i].length - 1) {
                            queueData.append(", ");
                        }
                    }
                } else {
                    queueData.append("Empty");
                }

                writer.write(queueData.toString() + "\n");
            }

            writer.close();
            System.out.println("Program data saved in a TXT file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public void loadProgramDataToConsole() {
        try {
            File file = new File("FoodCenterprogram_data.txt");
            Scanner fileScanner = new Scanner(file);

            // Read the remaining burger stock from the file
            String line = fileScanner.nextLine();
            int startIndex = line.indexOf(":") + 2;
            String stockValue = line.substring(startIndex);
            burgerStock = Integer.parseInt(stockValue);

            // Read the cashier queues from the file
            while (fileScanner.hasNextLine()) {
                line = fileScanner.nextLine();

                if (line.startsWith("Queue ")) {
                    int queueNumber = Character.getNumericValue(line.charAt(6));
                    int queueIndex = queueNumber - 1;
                    startIndex = line.indexOf(":") + 2;
                    String customers = line.substring(startIndex);

                    if (!customers.equals("Empty")) {
                        String[] customerNames = customers.split(", ");
                        cashierQueues[queueIndex] = new Object[customerNames.length];
                        for (int i = 0; i < customerNames.length; i++) {
                            String[] nameParts = customerNames[i].split(" ");
                            Customer customer = new Customer();
                            customer.setFirstName(nameParts[0]);
                            customer.setSecondName(nameParts[1]);
                            cashierQueues[queueIndex][i] = customer;
                        }
                        cashierQueuesSizes[queueIndex] = customerNames.length;
                    } else {
                        cashierQueues[queueIndex] = new Object[0];
                        cashierQueuesSizes[queueIndex] = 0;
                    }
                }
            }

            fileScanner.close();
            System.out.println("Done! Program data loaded successfully.\n");
            System.out.println("Burger Stock: " + burgerStock);

            // Print the cashier queues
            for (int i = 0; i < cashierQueues.length; i++) {
                System.out.print("Queue " + (i + 1) + ": ");
                if (cashierQueues[i].length > 0) {
                    for (int j = 0; j < cashierQueues[i].length; j++) {
                        Customer customer = (Customer) cashierQueues[i][j];
                        String customerFullName = customer.getFirstName() + " " + customer.getSecondName();
                        System.out.print(customerFullName);
                        if (j < cashierQueues[i].length - 1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                } else {
                    System.out.println("Empty");
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred!");
            e.printStackTrace();
        }
    }
    public void incomeEachQueue() {
        for (int i = 0; i < cashierQueues.length; i++) {
            int income = 0;
            for (int j = 0; j < cashierQueuesSizes[i]; j++) {
                int requiredBurgers = cashierBurgers[i][j];
                income += requiredBurgers * burgerPrice;
            }
            System.out.println("Cashier " + (i + 1) + " income: " + income);
        }
    }
    public void addBurgersToStock(int newBurgers) {
        burgerStock += newBurgers;
        System.out.println(newBurgers + " new burgers added to stock. Total stock: " + burgerStock);
    }

    private int getMaxQueueSize() {
        return Math.max(Math.max(MAX_IN_CASHIER_1, MAX_IN_CASHIER_2), MAX_IN_CASHIER_3);
    }

    private boolean isFoodQueuesFull() {
        return cashierQueuesSizes[0] == MAX_IN_CASHIER_1 &&
                cashierQueuesSizes[1] == MAX_IN_CASHIER_2 &&
                cashierQueuesSizes[2] == MAX_IN_CASHIER_3;
    }

    private void addToCustomerWaitingList(Customer customer) {
        customerWaitingListQueue.add(customer);

        String customerFullName = customer.getFirstName() + " " + customer.getSecondName();
        System.out.println("Done! '" + customerFullName + "' customer added to Waiting List");
    }
    private boolean isWaitingListEmpty() {
        return customerWaitingListQueue.isEmpty();
    }

    private Customer removeFromWaitingList() {
        if (isWaitingListEmpty()) return null;

        Customer customer = customerWaitingListQueue.remove(0);
        return customer;
    }

    private void addToFoodQueue(int queueIndex, Customer customer) {
        int existingQueueSize = cashierQueuesSizes[queueIndex];
        cashierQueues[queueIndex] = Arrays.copyOf(cashierQueues[queueIndex], existingQueueSize + 1);

        // Prepare the customer's full name before adding it to the queue
        String customerFullName = customer.getFirstName() + " " + customer.getSecondName();
        int burgerAmount = customer.getRequiredBurgers();
        cashierBurgers[queueIndex][existingQueueSize] = burgerAmount;
        cashierQueues[queueIndex][existingQueueSize] = customerFullName;
        cashierQueuesSizes[queueIndex]++;
        System.out.println("Done! '" + customerFullName + "' customer added to Queue " + (queueIndex + 1));
    }
}

