import java.net.*;
import java.util.Scanner;

public class DatagramClient {
    public static void main(String[] args) {
        try {
            DatagramSocket clientSocket = new DatagramSocket();

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter a message to send to the server: ");
                String message = scanner.nextLine();
                byte[] sendData = message.getBytes();

                InetAddress serverAddress = InetAddress.getByName("localhost");
                int serverPort = 9876;

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
                clientSocket.send(sendPacket);

                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Client is exiting.");
                    break;
                }
            }

            scanner.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
