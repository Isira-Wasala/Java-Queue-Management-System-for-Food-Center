import java.util.Scanner;

public class Customer {
    public int getRequiredBurgers;
    private String firstName;
    private String secondName;
    private int requiredBurgers;

    public void enterFirstName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your First Name: ");
        this.firstName = scanner.nextLine();
    }

    public void enterSecondName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter your Second Name: ");
        this.secondName = scanner.nextLine();
    }

    public void enterRequiredBurgers() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("How many burgers do you need? : ");
        this.requiredBurgers = scanner.nextInt();
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public int getRequiredBurgers() {
        return requiredBurgers;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
}
