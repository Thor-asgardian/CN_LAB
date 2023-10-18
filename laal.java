import java.util.Random;

class REDCongestionControl {
    private int minThreshold;
    private int maxThreshold;
    private double maxProbability;
    private Random random;

    public REDCongestionControl(int minThreshold, int maxThreshold, double maxProbability) {
        this.minThreshold = minThreshold;
        this.maxThreshold = maxThreshold;
        this.maxProbability = maxProbability;
        this.random = new Random();
    }

    public boolean shouldDropPacket(int currentQueueSize) {
        if (currentQueueSize < minThreshold) {
            return false;
        } else if (currentQueueSize > maxThreshold) {
            return true;
        } else {
            double dropProbability = (currentQueueSize - minThreshold) / (double) (maxThreshold - minThreshold);
            return random.nextDouble() < dropProbability;
        }
    }
}

public class REDDemo {
    public static void main(String[] args) {
        REDCongestionControl red = new REDCongestionControl(10, 50, 0.2);

        for (int i = 0; i < 100; i++) {
            int currentQueueSize = (int) (Math.random() * 100);
            boolean dropPacket = red.shouldDropPacket(currentQueueSize);

            if (dropPacket) {
                System.out.println("Dropping packet with queue size " + currentQueueSize);
            } else {
                System.out.println("Accepting packet with queue size " + currentQueueSize);
            }
        }
    }
}
