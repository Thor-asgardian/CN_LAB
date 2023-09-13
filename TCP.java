import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetstatParser {
    public static void main(String[] args) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("netstat", "-ano");
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            System.out.println("TCP Connections:");
            while ((line = reader.readLine()) != null) {
                if (line.contains("TCP")) {
                    System.out.println(line);
                }
            }

            process.waitFor();
            process.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
