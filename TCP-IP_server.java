import java.io.*;
import java.net.*;

public class FileServer {
    public static void main(String[] args) {
        final int PORT = 12345;
        
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);
            
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());
                
                // Create a new thread to handle the client request
                Thread clientThread = new Thread(new ClientHandler(clientSocket));
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            String fileName = reader.readLine();
            System.out.println("Client requested file: " + fileName);
            
            // Check if the file exists
            File file = new File(fileName);
            if (file.exists()) {
                BufferedReader fileReader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = fileReader.readLine()) != null) {
                    writer.println(line);
                }
                fileReader.close();
            } else {
                writer.println("File not found");
            }
            
            System.out.println("File transfer complete.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
