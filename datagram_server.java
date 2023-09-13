import java.net.*;
import java.io.*;

public class DatagramServer {
    public static void main(String[] args) {
        final int SERVER_PORT = 9876;

        try (DatagramSocket socket = new DatagramSocket(SERVER_PORT)) {
            System.out.println("Server is listening on port " + SERVER_PORT);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                
                System.out.println("Received from client: " + message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
