import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class IpConfigParser {
    public static void main(String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("ipconfig");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Host Name")) {
                    System.out.println("Host Name: " + line.split(":")[1].trim());
                } else if (line.contains("IPv4 Address")) {
                    System.out.println("IPv4 Address: " + line.split(":")[1].trim());
                } else if (line.contains("Physical Address")) {
                    System.out.println("Physical Address (MAC): " + line.split(":")[1].trim());
                } else if (line.contains("Subnet Mask")) {
                    System.out.println("Subnet Mask: " + line.split(":")[1].trim());
                } else if (line.contains("Default Gateway")) {
                    System.out.println("Default Gateway: " + line.split(":")[1].trim());
                }
            }
            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
