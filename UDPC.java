import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UDPC {
    public static void main(String[] args) {
        final String SERVER_IP = "localhost";
        final int SERVER_PORT = 12345;

        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName(SERVER_IP);
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("Enter message for server (type 'exit' to quit): ");
                String message = scanner.nextLine();

                if (message.equalsIgnoreCase("exit")) {
                    break;
                }

                byte[] sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, SERVER_PORT);
                clientSocket.send(sendPacket);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
