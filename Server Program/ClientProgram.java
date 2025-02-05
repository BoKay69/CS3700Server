import java.util.Scanner;

public class ClientProgram {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for the DNS or IP address
        System.out.print("Enter the DNS or IP address of the server: ");

        // Read user input as a string
        String serverAddress = scanner.nextline();

        // Display the entered address
        System.out.println("You entered: " + serverAddress);

        // Display the table header
        System.out.println("\nItem ID\t\tItem Description");
        System.out.println("00001\t\tNew Inspiron 15");
        System.out.println("00002\t\tNew Inspiron 17");
        System.out.println("00003\t\tNew Inspiron 15R");
        System.out.println("00004\t\tNew Inspiron 15z Ultrabook");
        System.out.println("00005\t\tXPS 14 Ultrabook");
        System.out.println("00006\t\tNew XPS 12 UltrabookXPS");

        // Close the scanner
        scanner.close();
    }
}