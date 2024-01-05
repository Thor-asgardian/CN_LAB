public class TokenBucketAlgorithm {
    private int bucketCapacity;
    private int tokens;
    private int tokenRate;
    private long lastTokenTime;

    public TokenBucketAlgorithm(int bucketCapacity, int tokenRate) {
        this.bucketCapacity = bucketCapacity;
        this.tokens = 0;
        this.tokenRate = tokenRate;
        this.lastTokenTime = System.currentTimeMillis();
    }

    public boolean allowAction() {
        long currentTime = System.currentTimeMillis();
        long timePassed = currentTime - lastTokenTime;

        tokens += timePassed * tokenRate;

        if (tokens > bucketCapacity) {
            tokens = bucketCapacity;
        }

        lastTokenTime = currentTime;

        if (tokens >= 1) {
            tokens -= 1;
            return true; 
        } else {
            return false; 
        }
    }

    public static void main(String[] args) {
      
        TokenBucketAlgorithm tokenBucket = new TokenBucketAlgorithm(10, 2);

        for (int i = 0; i < 15; i++) {
            if (tokenBucket.allowAction()) {
                System.out.println("Action " + (i + 1) + " allowed.");
            } else {
                System.out.println("Action " + (i + 1) + " denied.");
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
