import java.io.*;
import java.net.*;
import java.util.*;

// this sets things for hashmap
class Item {
    String iD;
    String description;
    double price;
    int inventory;

    public Item(String iD, String description, double price, int inventory) {
        this.iD = iD;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
    }

    // this just changes the data types to String
    @Override
    public String toString() {
        return "Item ID: " + iD + ", Description: " + description + ", Price: $" + price + ", Inventory: " + inventory;
    }
}

public class ServerProgram {
    // PUT YOUR PORT NUMBER HERE ZACK
    private static final int port = 5320;
    private static final Map<String, Item> inventory = new HashMap<>();

    public static void main(String[] args) {
        initInventory();

        // making UDP Socket for the port
        try (DatagramSocket udpServerSocket = new DatagramSocket(port)) {
            //System.out.println(port);

            // this is an inf loop listening for client. Instead of the boolean morePackets just using true and then breaking.
            while (true) {
                try {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    udpServerSocket.receive(receivePacket);

                    String iD = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
                    System.out.println("Client is requesting for Item: " + iD);

                    String responseMessage = inventory.containsKey(iD)
                            ? inventory.get(iD).toString()
                            : "Item not found";

                    byte[] sendData = responseMessage.getBytes();
                    InetAddress clientAddress = receivePacket.getAddress();
                    int clientPort = receivePacket.getPort();

                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                    udpServerSocket.send(sendPacket);
                    System.out.println("Response sent: " + responseMessage);
                } catch (IOException e) {
                    System.err.println("Error: " + e.printStackTrace());
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Server failed: " + e.printStackTrace());
        }
    }

    private static void initInventory() {
        inventory.put("00001", new Item("00001", "New Inspiron 15", 379.99, 157));
        inventory.put("00002", new Item("00002", "New Inspiron 17", 449.99, 128));
        inventory.put("00003", new Item("00003", "New Inspiron 15R", 549.99, 202));
        inventory.put("00004", new Item("00004", "New Inspiron 15z Ultrabook", 749.99, 315));
        inventory.put("00005", new Item("00005", "XPS 14 Ultrabook", 999.99, 261));
        inventory.put("00006", new Item("00006", "New XPS 12 UltrabookXPS", 1199.99, 178));
    }
}