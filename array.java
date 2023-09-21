import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

class Packet {
    int SeqNum;
    String Data;

    public Packet(int SeqNum, String Data) {
        this.SeqNum = SeqNum;
        this.Data = Data;
    }
}

public class frame {
    private static final int FSize = 3;

    public static Packet[] divide(String msg) {
        int msglen = msg.length();
        int NoOfPacket = (int) Math.ceil((double) msglen / FSize);
        Packet[] readdata = new Packet[NoOfPacket];
        for (int i = 0, j = 0; i < NoOfPacket; i++) {
            int end = Math.min(j + FSize, msglen);
            readdata[i] = new Packet(i + 1, msg.substring(j, end));
            j += FSize;
        }
        return readdata;
    }

    public static Packet[] shuffle(Packet[] readdata) {
        Random rand = new Random();
        Packet[] transdata = Arrays.copyOf(readdata, readdata.length);
        for (int i = 0; i < transdata.length; i++) {
            int trans = rand.nextInt(transdata.length);
            Packet temp = transdata[i];
            transdata[i] = transdata[trans];
            transdata[trans] = temp;
        }
        return transdata;
    }

    public static void bubbleSort(Packet[] transdata) {
        int n = transdata.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) { // Start from index 1, not 0
                if (transdata[i - 1].SeqNum > transdata[i].SeqNum) {
                    // Swap transdata[i-1] and transdata[i]
                    Packet temp = transdata[i - 1];
                    transdata[i - 1] = transdata[i];
                    transdata[i] = temp;
                    swapped = true;
                }
            }
            n--;
        } while (swapped);
    }

    public static void receiver(Packet[] transdata) {
        System.out.println("\n Packets received in the following order:");
        for (Packet packet : transdata) {
            System.out.println(packet.SeqNum);
        }
        bubbleSort(transdata);
        System.out.println("\n\nPackets in order after sorting:\n");
        for (Packet packet : transdata) {
            System.out.print(packet.Data);
        }
    }

    public static void main(String[] args) {
        String msg;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter msg to transmit\n");
        msg = sc.nextLine();
        Packet[] readdata = divide(msg);
        Packet[] transdata = shuffle(readdata);
        receiver(transdata);

        sc.close();
    }
}
