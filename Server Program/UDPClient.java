import java.io.*;
import java.net.*;
import java.util.*;
import java.time.Instant;
import java.time.Duration;

public class UDPClient {
    public static void main(String[] args) throws IOException {
        // Validate command-line arguments
        if (args.length != 1) {
            System.out.println("Usage: java UDPClient <hostname>");
            return;
        }

        // Create a UDP socket
        DatagramSocket udpSocket = new DatagramSocket();
        BufferedReader sysIn = new BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(System.in);
        String fromServer;
        String fromUser;

        // Get server address from command-line argument
        InetAddress serverAddress = InetAddress.getByName(args[0]);

        // Set of valid item IDs
        Set<String> validItemIDs = new HashSet<>(Arrays.asList("00001", "00002", "00003", "00004", "00005", "00006"));

        while (true) {
            // Display item list
            System.out.println("\nItem ID\t\tItem Description");
            System.out.println("00001\t\tNew Inspiron 15");
            System.out.println("00002\t\tNew Inspiron 17");
            System.out.println("00003\t\tNew Inspiron 15R");
            System.out.println("00004\t\tNew Inspiron 15z Ultrabook");
            System.out.println("00005\t\tXPS 14 Ultrabook");
            System.out.println("00006\t\tNew XPS 12 UltrabookXPS");

            // Prompt user for a valid Item ID
            String itemID;
            while (true) {
                System.out.print("\nEnter an Item ID (or type 'Bye.' to exit): ");
                itemID = scanner.nextLine().trim();

                if (itemID.equalsIgnoreCase("Bye.")) {
                    udpSocket.close();
                    System.out.println("Client program terminated.");
                    return;
                }

                if (validItemIDs.contains(itemID)) {
                    break;
                } else {
                    System.out.println("Invalid Item ID! Please enter a valid one.");
                }
            }

            // Record time before sending request
            Instant sendTime = Instant.now();

            // Send request to server
            byte[] sendBuffer = itemID.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, 5678);
            udpSocket.send(sendPacket);
            System.out.println("Request sent to server: " + itemID);

            // Receive response from server
            byte[] receiveBuffer = new byte[256];
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);
            udpSocket.receive(receivePacket);

            // Record time after receiving response
            Instant receiveTime = Instant.now();

            // Calculate RTT
            long rtt = Duration.between(sendTime, receiveTime).toMillis();

            // Process server response
            fromServer = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String[] responseParts = fromServer.split(",");

            if (responseParts.length != 3) {
                System.out.println("Invalid response format from server.");
                continue;
            }

            String itemDescription = responseParts[0].trim();
            String unitPrice = responseParts[1].trim();
            String inventory = responseParts[2].trim();

            // Display formatted result
            System.out.println("\nItem ID\t\tItem Description\t\tUnit Price\tInventory\tRTT of Query");
            System.out.printf("%s\t%s\t$%s\t\t%s\t\t%d ms%n", itemID, itemDescription, unitPrice, inventory, rtt);
        }
    }
}


