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

        // Close the scanner
        scanner.close();
    }
}