import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.time.Instant;
import java.time.Duration;

public class ClientProgram {
    public static void main(String[] args) {
        // Create a Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for the DNS or IP address
        System.out.print("Enter the DNS or IP address of the server: ");
        String serverAddress = scanner.nextLine();
        System.out.println("You entered: " + serverAddress);

        boolean continueQuery = true;

        while (continueQuery) {
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

            // Record time before sending request
            Instant sendTime = Instant.now();

            // Send the request and receive the response
            try (Socket socket = new Socket(serverAddress, 8080); // Assume server listens on port 8080
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                // Send the Item ID as a request message
                out.println(itemID);
                System.out.println("Request sent to server: " + itemID);

                // Read the server response
                String serverResponse = in.readLine(); // Assume response is a single line
                if (serverResponse == null) {
                    throw new Exception("No response from server.");
                }

                // Record time after receiving response
                Instant receiveTime = Instant.now();

                // Calculate Round-Trip Time (RTT) in milliseconds
                long rtt = Duration.between(sendTime, receiveTime).toMillis();

                // Split response into components (format: "Item Description, Unit Price, Inventory")
                String[] responseParts = serverResponse.split(",");
                if (responseParts.length != 3) {
                    throw new Exception("Invalid response format from server.");
                }

                String itemDescription = responseParts[0].trim();
                String unitPrice = responseParts[1].trim();
                String inventory = responseParts[2].trim();

                // Display the received information in tabular form
                System.out.println("\nItem ID\t\tItem Description\t\tUnit Price\tInventory\tRTT of Query");
                System.out.printf("%s\t%s\t$%s\t\t%s\t\t%d ms%n", itemID, itemDescription, unitPrice, inventory, rtt);

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Ask user if they want to continue
            System.out.print("\nDo you want to perform another query? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();
            if (!response.equals("yes")) {
                continueQuery = false;
            }
        }

        // Close the scanner and exit the program
        System.out.println("Client program terminated.");
        scanner.close();
    }
}

