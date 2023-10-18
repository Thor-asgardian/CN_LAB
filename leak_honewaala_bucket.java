import java.util.Timer;
import java.util.TimerTask;

class LeakyBucket {
    private int bucketSize;
    private int currentSize;
    private int rate;

    public LeakyBucket(int bucketSize, int rate) {
        this.bucketSize = bucketSize;
        this.rate = rate;
        this.currentSize = 0;
    }

    public void addPacket(int packetSize) {
        if (currentSize + packetSize <= bucketSize) {
            currentSize += packetSize;
            System.out.println("Packet of size " + packetSize + " bytes added. Bucket size = " + currentSize + " bytes");
        } else {
            System.out.println("Packet of size " + packetSize + " bytes dropped. Bucket full.");
        }
    }

    public void leak() {
        if (currentSize > 0) {
            currentSize -= rate;
            if (currentSize < 0) {
                currentSize = 0;
            }
            System.out.println("Leaked " + rate + " bytes. Bucket size = " + currentSize + " bytes");
        }
    }
}

class TokenBucket {
    private int bucketSize;
    private int tokens;
    private int rate;

    public TokenBucket(int bucketSize, int rate) {
        this.bucketSize = bucketSize;
        this.rate = rate;
        this.tokens = 0;
    }

    public void addPacket(int packetSize) {
        if (packetSize <= tokens) {
            tokens -= packetSize;
            System.out.println("Packet of size " + packetSize + " bytes sent. Tokens left = " + tokens);
        } else {
            System.out.println("Packet of size " + packetSize + " bytes dropped. Not enough tokens.");
        }
    }

    public void generateToken() {
        if (tokens < bucketSize) {
            tokens += rate;
            if (tokens > bucketSize) {
                tokens = bucketSize;
            }
            System.out.println("Generated " + rate + " tokens. Tokens = " + tokens);
        }
    }
}

public class CongestionControl {
    public static void main(String[] args) {
        LeakyBucket leakyBucket = new LeakyBucket(100, 10);
        TokenBucket tokenBucket = new TokenBucket(100, 10);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                leakyBucket.leak();
                tokenBucket.generateToken();
            }
        }, 1000, 1000);

        // Simulate packet arrival
        for (int i = 0; i < 150; i++) {
            int packetSize = (int) (Math.random() * 30);
            leakyBucket.addPacket(packetSize);
            tokenBucket.addPacket(packetSize);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
