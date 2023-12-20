import java.util.Scanner;

public class Main {
    public static int crc(char[] ip, char[] op, char[] poly, int mode) {
        System.arraycopy(ip, 0, op, 0, ip.length);
        if (mode != 0) {
            for (int i = 1; i < poly.length; i++) {
                op[ip.length + i - 1] = '0';
            }
        }
        
        for (int i = 0; i < ip.length; i++) {
            if (op[i] == '1') {
                for (int j = 0; j < poly.length; j++) {
                    if (op[i + j] == poly[j]) {
                        op[i + j] = '0';
                    } else {
                        op[i + j] = '1';
                    }
                }
            }
        }
        
        for (int i = 0; i < op.length; i++) {
            if (op[i] == '1') {
                return 0;
            }
        }
        return 1;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char[] ip = new char[50];
        char[] op = new char[50];
        char[] recv = new char[50];
        char[] poly = "10001000000100001".toCharArray();
        System.out.println("Enter the input message in binary");
        String input = scanner.nextLine();
        ip = input.toCharArray();
        crc(ip, op, poly, 1);
        System.out.println("The transmitted message is: " + input + new String(op, 0, ip.length));
        System.out.println("Enter the received message in binary");
        input = scanner.nextLine();
        recv = input.toCharArray();
        if (crc(recv, op, poly, 0) != 0) {
            System.out.println("No error in data");
        } else {
            System.out.println("Error in data transmission has occurred");
        }
    }
}
