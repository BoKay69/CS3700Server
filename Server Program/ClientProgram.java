import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.time.LocalTime;

public class ClientProgram {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for the DNS or IP address
        System.out.print("Enter the DNS or IP address of the server: ");
        String serverAddress = scanner.nextLine();

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

        // Set of valid item IDs
        Set<String> validItemIDs = new HashSet<>();
        validItemIDs.add("00001");
        validItemIDs.add("00002");
        validItemIDs.add("00003");
        validItemIDs.add("00004");
        validItemIDs.add("00005");
        validItemIDs.add("00006");

        // Prompt the user for an Item ID and validate
        String itemID;
        while (true) {
            System.out.print("\nEnter an Item ID: ");
            itemID = scanner.nextLine();

            if (validItemIDs.contains(itemID)) {
                System.out.println("Valid Item ID Entered: " + itemID);
                break;
            } else {
                System.out.println("Invalid Item ID! Please enter a valid one from the list above.");
            }
        }

        // Record local time before sending request
        LocalTime requestTime = LocalTime.now();
        System.out.println("Request time recorded: " + requestTime);

        // Send the request to the server
        try (Socket socket = new Socket(serverAddress, 8080); // Assume server listens on port 8080
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            
            // Send the Item ID as a request message
            out.println(itemID);
            System.out.println("Request sent to server: " + itemID);

        } catch (Exception e) {
            System.out.println("Error connecting to server: " + e.getMessage());
        }

        // Close the scanner
        scanner.close();
    }
}
